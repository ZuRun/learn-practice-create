package cn.zull.lpc.learn.leetcode.interview.apply;

import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zurun
 * @date 2022/9/4 09:24:26
 */
public class DispatchPool {
    // 最大堆
    private static final PriorityQueue<Task> priorityQueue = new PriorityQueue<>(Task::compareTo);
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1), new ThreadPoolExecutor.CallerRunsPolicy());

    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    /**
     * 可用线程
     */
    private static final AtomicInteger usable = new AtomicInteger(5);

    static {
        new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    // 调度池容纳量为5，池中不满时从队列获取补充到池中，至补满或队列为空
                    while (priorityQueue.size() < 5 && TaskQueue.size() > 0) {
                        priorityQueue.offer(TaskQueue.poll());
                    }
                    while (priorityQueue.peek() == null || priorityQueue.peek().getResourceNum() > ResourcePool.size()) { // 无task 或者资源不足
                        try {
                            // TaskQueue有新task时唤醒
                            condition.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    usable.decrementAndGet();
                    // 资源足够时,任务-1，资源-task.getResourceNum();
                    Task task = priorityQueue.poll();
                    for (int i = 0; i < task.getResourceNum(); i++) ResourcePool.poll();
                    // 异步执行任务
                    executor.execute(() -> {
                        long start = System.currentTimeMillis();
                        task.execute();
                        long stop = System.currentTimeMillis();
                        usable.incrementAndGet(); // 可用线程+1
                        System.out.println("任务属性：" + task.toString() + "  耗时：" + (stop - start));   // 任务执行完成打印完成时间、任务属性和任务执行耗时；
                    });
                } finally {
                    lock.unlock();
                }
            }

        }).start();


        // 周期打印任务队列长度、资源池数量、当前执行任务数、调度器空闲率
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    System.out.println("任务队列长度:" + TaskQueue.size() + " 资源池数量:" + ResourcePool.size() + " 当前执行任务数: " + (5 - usable.get()));//任务队列长度
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    /**
     * @return 1运行  0空闲
     */
    public static Integer getStatus() {
        return usable.get() == 5 ? 0 : 1;
    }

    public static void signal() {
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

}
