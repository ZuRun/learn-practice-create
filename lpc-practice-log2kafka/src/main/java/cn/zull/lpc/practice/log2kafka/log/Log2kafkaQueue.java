package cn.zull.lpc.practice.log2kafka.log;

import cn.zull.lpc.practice.log2kafka.model.LogModel;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zurun
 * @date 2020/8/11 19:06:25
 */
public class Log2kafkaQueue {
    private static final AtomicLong addedFailed = new AtomicLong(0L);
    private static final AtomicInteger addCount = new AtomicInteger(0);
    private static final AtomicInteger takeCount = new AtomicInteger(0);
    // 队列长度,1<<18为26万
    private static final BlockingQueue<LogModel> queue = new ArrayBlockingQueue(1 << 18);

    static {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            int add = addCount.getAndSet(0);
            int take = takeCount.getAndSet(0);
            System.out.println(System.currentTimeMillis() + " queue:" + queue.size() + " ; add:" + add + " ; take:" + take + " ; w2k:" + LogConsumer2Kafka.sum.getAndSet(0) + " f:" + addedFailed.get());
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static boolean add(LogModel logBean) {
        boolean bl = queue.offer(logBean);
        addCount.getAndIncrement();
        if (!bl) {
            addedFailed.getAndIncrement();
        }
        return bl;
    }

    public static LogModel take(long time, TimeUnit timeUnit) throws InterruptedException {
        LogModel logBean = queue.poll(time, timeUnit);
        if (logBean != null) {
            takeCount.getAndIncrement();
        }
        return logBean;
    }

    public static int queueSize() {
        return queue.size();
    }

    public static void main(String[] args) {
        System.out.println(1 << 18);
    }

}
