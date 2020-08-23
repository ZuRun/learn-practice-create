package cn.zull.lpc.learn.basic.thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author jared.zu
 * @date 2020/8/23 11:05:17
 */
public class ExecutorsTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        executor.execute(null);
    }
}
