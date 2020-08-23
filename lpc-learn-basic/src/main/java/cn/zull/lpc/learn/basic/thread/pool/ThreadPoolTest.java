package cn.zull.lpc.learn.basic.thread.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zurun
 * @date 2020/7/1 17:08:53
 */
//@Slf4j
public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        ThreadFactory factory = new ThreadFactory() {

            AtomicInteger threadNumber = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "zt_" + threadNumber.incrementAndGet());
                return thread;
            }
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 111, 20, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100), factory, new ThreadPoolExecutor.CallerRunsPolicy());

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println("[]" + threadPoolExecutor.getActiveCount() + " | " +
                    threadPoolExecutor.getCompletedTaskCount() + " | " +
                    threadPoolExecutor.getQueue().size() + " | "
            );
        }, 0, 1, TimeUnit.SECONDS);
        for (int i = 0; i < 22; i++) {
            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    threadPoolExecutor.execute(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });
            t.start();
        }
        Thread.sleep(15000L);
        threadPoolExecutor.setMaximumPoolSize(13);

        countDownLatch.await();
    }
}
