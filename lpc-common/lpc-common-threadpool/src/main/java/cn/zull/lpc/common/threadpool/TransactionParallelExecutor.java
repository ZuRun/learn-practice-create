package cn.zull.lpc.common.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Jared.Zu
 * @date 2021/6/10 17:40:47
 */
@Slf4j
public class TransactionParallelExecutor implements ParallelExecutor {
    private final TransactionThreadPoolExecutor executor;

    public TransactionParallelExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        executor = new TransactionThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public TransactionParallelExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        executor = new TransactionThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public Future batchExecute(List<Runnable> runnableList) {
        if (CollectionUtils.isEmpty(runnableList)) {
            return emptyFuture;
        }
        int size = runnableList.size();
        CountDownLatch countDownLatch = new CountDownLatch(size);
        AtomicBoolean rollbackMark = new AtomicBoolean(false);

        runnableList.forEach(runnable -> {
            executor.execute(runnable, rollbackMark, countDownLatch);
        });

        return new Future() {
            @Override
            public void get() throws InterruptedException {
                countDownLatch.await();
                if (rollbackMark.get()) {
                    log.warn("failed---------------");
                    throw new RuntimeException("failed");

                }
            }

            @Override
            public boolean get(long timeout, TimeUnit unit) throws InterruptedException {
                boolean await = countDownLatch.await(timeout, unit);
                if (rollbackMark.get()) {
                    log.warn("failed---------------");
                    throw new RuntimeException("failed");

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
            if (rollbackMark.get()) {
                return;
            }
            Map<Object, Object> resourceMap = TransactionSynchronizationManager.getResourceMap();
            if (CollectionUtils.isEmpty(resourceMap)) {
                // 主线程中没有连接信息
                log.warn("resourceMap is empty");
                command.run();
                countDownLatch.countDown();
                return;
            }
            Thread thread = Thread.currentThread();
            super.execute(() -> {
                Thread asyncThread = Thread.currentThread();
                if (asyncThread.equals(thread)) {
                    // 如果队列满了,走主线程,则不处理事务
                    log.warn("TransactionThreadPoolExecutor queue capacities are reached ");
                    command.run();
                    countDownLatch.countDown();
                    return;
                }
                try {

                    // copy resource
                    resourceMap.forEach(TransactionSynchronizationManager::bindResource);
                    command.run();
                } catch (Exception e) {
                    // 异常标识回滚
                    rollbackMark.set(true);
                } finally {
                    countDownLatch.countDown();
                    TransactionSynchronizationManager.clear();
                    resourceMap.forEach((k, v) -> TransactionSynchronizationManager.unbindResourceIfPossible(k));
                }
            });
        }
    }
}
