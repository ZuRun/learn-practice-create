package cn.zull.lpc.learn.basic.lock;

import cn.zull.lpc.common.basis.utils.RandomUtils;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionTest {
    private static Lock lock = new ReentrantLock();
    private static Condition addCondition = lock.newCondition();
    private static Condition removeCondition = lock.newCondition();
    private static Deque<Integer> pool = new LinkedList<>();
    private static AtomicInteger num = new AtomicInteger(0);

    public static Runnable add = new Runnable() {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    Thread.sleep(RandomUtils.randomNumber(2000));
                    int a = num.getAndIncrement();
                    pool.offerLast(a);
                    addCondition.signal();
                    System.out.println("输入 " + a);
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }

            }
        }
    };

    public static Runnable remove = new Runnable() {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    if (pool.size() > 0) {
                        int a = pool.pollFirst();
                        System.out.println("输出 " + a);
                    } else {
                        addCondition.await(); // 此时会释放锁，并等待signal通知
                    }
                    System.out.println("--- ");
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }
            }
        }
    };

    public static void main(String[] args) {
        new Thread(add).start();
        new Thread(remove).start();
    }
}
