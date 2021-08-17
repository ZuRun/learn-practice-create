package cn.zull.lpc.common.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jared.Zu
 * @date 2021/6/10 17:40:47
 */
@Slf4j
public class TransactionParallelExecutor implements ParallelExecutor {
    private final TransactionThreadPoolExecutor executor;

    public TransactionParallelExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        executor = new TransactionThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                new TransactionParallelExecutor.TransactionThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    public TransactionParallelExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        executor = new TransactionThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public Future batchExecute(List<Runnable> runnableList) {
        if (CollectionUtils.isEmpty(runnableList)) {
            log.warn("empty_runnable_list");
            return emptyFuture;
        }
        int size = runnableList.size();
        CountDownLatch countDownLatch = new CountDownLatch(size);
        AtomicBoolean rollbackMark = new AtomicBoolean(false);

        runnableList.forEach(runnable -> {
            if (!rollbackMark.get()) {
                executor.execute(runnable, rollbackMark, countDownLatch);
            } else {
                countDownLatch.countDown();
            }
        });

        return new Future() {
            @Override
            public void get() throws InterruptedException {
                countDownLatch.await();
                if (rollbackMark.get()) {
                    log.warn("batch_execute_failed---------------");
                    throw new RuntimeException("batch_execute_failed");
                }
            }

            @Override
            public boolean get(long timeout, TimeUnit unit) throws InterruptedException {
                boolean await = countDownLatch.await(timeout, unit);
                if (rollbackMark.get()) {
                    log.warn("batch_execute_failed2---------------");
                    throw new RuntimeException("batch_execute_failed2");
                }
                return await;
            }
        };
    }


    private final Future emptyFuture = new Future() {
        @Override
        public void get() throws InterruptedException {

        }

        @Override
        public boolean get(long timeout, TimeUnit unit) throws InterruptedException {
            return true;
        }
    };


    static class TransactionThreadPoolExecutor extends ThreadPoolExecutor {
        public TransactionThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        public TransactionThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        public TransactionThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        public TransactionThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }


        public void execute(Runnable command, AtomicBoolean rollbackMark, CountDownLatch countDownLatch) {
            try {
                if (rollbackMark.get()) {
                    return;
                }
                // 主业务线程的resourceMap
                Map<Object, Object> resourceMap = TransactionSynchronizationManager.getResourceMap();
                if (CollectionUtils.isEmpty(resourceMap)) {
                    // 主业务线程中没有连接信息
                    log.warn("resourceMap_is_empty");
                    command.run();
                    countDownLatch.countDown();
                    return;
                }

                Thread thread = Thread.currentThread();
                super.execute(() -> {
                    Thread asyncThread = Thread.currentThread();
                    if (asyncThread.equals(thread)) {
                        // 如果队列满了,走主线程,则不处理事务
                        log.warn("TransactionThreadPoolExecutor_queue_capacities_are_reached");
                        try {
                            command.run();
                        } catch (Exception e) {
                            // 异常标识回滚
                            log.warn("sync_task_execute_failed,{}", e.getMessage(), e);
                            rollbackMark.set(true);
                        }
                        countDownLatch.countDown();
                        return;
                    }

                    // 真正执行异步任务
                    try {
                        // copy resource
                        resourceMap.forEach(TransactionSynchronizationManager::bindResource);
                        command.run();
                    } catch (Exception e) {
                        log.warn("async_task_execute_failed,{}", e.getMessage(), e);
                        // 异常标识回滚
                        rollbackMark.set(true);
                    } finally {
                        countDownLatch.countDown();
                        TransactionSynchronizationManager.clear();
                        resourceMap.forEach((k, v) -> TransactionSynchronizationManager.unbindResourceIfPossible(k));
                    }
                });
            } catch (Exception e) {
                log.warn("sync_task_execute_failed,{}", e.getMessage(), e);
                // 异常标识回滚
                rollbackMark.set(true);
                countDownLatch.countDown();
            }
        }
    }

    class TransactionThreadFactory implements ThreadFactory {
        private final String namePrefix = "biz-ttp-";
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r,
                    namePrefix + threadNumber.getAndIncrement());
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
