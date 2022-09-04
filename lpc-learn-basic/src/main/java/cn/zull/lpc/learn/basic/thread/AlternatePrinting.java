package cn.zull.lpc.learn.basic.thread;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zurun
 * @date 2022/8/1 22:03:06
 */
public class AlternatePrinting {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        AtomicBoolean bl = new AtomicBoolean(true);

        Runnable runnable1 = () -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lock.lock();
                try {
                    if (bl.get()) {
                        System.out.println("1");
                        bl.set(false);
                        condition2.signal();
                    } else {
                        condition1.await();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }

        };
        Runnable runnable2 = () -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                lock.lock();
                try {
                    if (!bl.get()) {
                        System.out.println("2");
                        bl.set(true);
                        condition1.signal();
                    } else {
                        condition2.await();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        };

        new Thread(runnable1).start();
        new Thread(runnable2).start();
        Thread.sleep(10000);
    }
}
