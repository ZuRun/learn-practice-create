package cn.zull.lpc.practice.log2kafka.log;

import cn.zull.lpc.practice.log2kafka.bean.LogBean;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zurun
 * @date 2020/8/11 19:06:25
 */
public class Log2kafkaQueue {
    // 队列长度,1<<18为26万
    private static final BlockingQueue<LogBean> queue = new ArrayBlockingQueue(1 << 18);

    public static boolean add(LogBean logBean) {
        boolean bl = queue.offer(logBean);
        return bl;
    }

    public static LogBean take(long time, TimeUnit timeUnit) throws InterruptedException {
        return queue.poll(time, timeUnit);
    }


    public static void main(String[] args) {
        System.out.println(1 << 18);
    }
}
