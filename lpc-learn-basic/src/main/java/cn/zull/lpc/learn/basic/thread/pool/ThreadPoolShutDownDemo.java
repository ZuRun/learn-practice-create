package cn.zull.lpc.learn.basic.thread.pool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolShutDownDemo {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable1 = () -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(" - ");
        };

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 2, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100));

        for (int i = 0; i < 90; i++) executor.submit(runnable1);

        
        executor.shutdown();

        for (int i = 0; i < 1000000; i++) {
            System.out.println(executor.isTerminated() + " " + executor.getActiveCount());
//            System.out.println(executor.getActiveCount());
            Thread.sleep(100);
        }
        Thread.sleep(10000000);
    }
}
