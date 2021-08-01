package cn.zull.lpc.learn.basic.thread.threadlocal;

import cn.zull.lpc.common.basis.utils.UUIDUtils;
import cn.zull.lpc.common.basis.utils.function.NoArgsFunction;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jared.Zu
 * @date 2021/5/24 18:14:33
 */
public class ThreadLocalMemoryLeakTest {

    public static class LocalVariable {
        // 5MB
        private byte[] b = new byte[1024 * 1024 * 1];
    }


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 100, TimeUnit.HOURS, new LinkedBlockingQueue<>());
        Runnable runnable = () -> {
            ThreadLocal<LocalVariable> threadLocal = new ThreadLocal<>();

//            threadLocal.set(UUIDUtils.simpleUUID());
            threadLocal.set(new LocalVariable());
            threadLocal.remove();
            int i = 1;
        };

        for (int i = 0; i < 200000; i++) {
            executor.execute(runnable);
            System.out.println("---" + i);
            try {
                Thread.sleep(10L);
//                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

    }
}
