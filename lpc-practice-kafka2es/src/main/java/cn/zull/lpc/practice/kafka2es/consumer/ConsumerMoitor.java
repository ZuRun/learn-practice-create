//package cn.zull.lpc.practice.kafka2es.consumer;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @author zurun
// * @date 2020/8/21 10:29:27
// */
//public class ConsumerMoitor {
//
//    public static final AtomicInteger sum = new AtomicInteger(0);
//    public static ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
//
//
//
//    static {
//        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
////            new ThreadPoolExecutor(100,100,1, TimeUnit.MINUTES,
////                    new LinkedBlockingQueue(1000));
//            System.out.println(System.currentTimeMillis() +" active: "+executorService.getActiveCount()+ " 消费条数:" + sum.getAndSet(0));
//        }, 0, 1, TimeUnit.SECONDS);
//
//    }
//}
