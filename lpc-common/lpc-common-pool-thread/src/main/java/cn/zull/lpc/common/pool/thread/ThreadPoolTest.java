package cn.zull.lpc.common.pool.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zurun
 * @date 2020/7/1 17:08:53
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadFactory factory = new ThreadFactory() {

            AtomicInteger threadNumber = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "zt_" + threadNumber.incrementAndGet());
                return thread;
            }
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 11, 20, TimeUnit.SECONDS, new LinkedBlockingQueue<>(17), factory, new ThreadPoolExecutor.CallerRunsPolicy());

        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            System.out.println("[]" + threadPoolExecutor.getPoolSize());
        });
    }
}
