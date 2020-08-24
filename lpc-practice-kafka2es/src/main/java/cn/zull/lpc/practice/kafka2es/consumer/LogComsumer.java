package cn.zull.lpc.practice.kafka2es.consumer;

import cn.zull.lpc.common.basis.utils.JsonUtils;
import cn.zull.lpc.common.kafka.config.KafkaConsumerCluster;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zurun
 * @date 2020/8/17 10:06:04
 */
@Slf4j
@Component
public class LogComsumer implements CommandLineRunner {
    @Autowired
    Write2es write2es;
    @Autowired
//    KafkaConsumer<String, String> kafkaConsumer;
    KafkaConsumerCluster cluster;
    @Value("${lpc.biz.w2es.thread.size:50}")
    private int threadSize;

    private final AtomicInteger sum = new AtomicInteger(0);
    ThreadPoolExecutor executorService;

    @PostConstruct
    public void pre() {
        executorService = new ThreadPoolExecutor(threadSize, threadSize, 1, TimeUnit.MINUTES,
                new LinkedBlockingQueue(1000));

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println(System.currentTimeMillis() + " active: " + executorService.getActiveCount() + " 消费条数:" + sum.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);

    }

    @Override
    public void run(String... args) throws Exception {
        List<KafkaConsumer<String, String>> consumerList = cluster.getConsumerList();
        for (int i = 0; i < consumerList.size(); i++) {
            KafkaConsumer kafkaConsumer = consumerList.get(i);
            new Thread(() -> {
                try {
                    while (true) {
                        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(5000));
                        if (records.isEmpty()) {
                            return;
                        }
                        CountDownLatch countDownLatch = new CountDownLatch(records.count());
                        System.out.println(records.count());
//                    Thread.sleep(1000);
                        records.iterator().forEachRemaining(record -> {
                            executorService.execute(() -> {
                                try {
                                    String msg = record.value();
                                    List<Map<String, String>> list = JsonUtils.json2List(msg);
                                    sum.getAndAdd(list.size());
//                                    write2es.batchInsertEs("lpc_log", list);
                                } finally {
                                    countDownLatch.countDown();
                                }
                            });
                        });
                        countDownLatch.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                } finally {
                    kafkaConsumer.close();
                }
            }, "kafka-consumer-thread-" + i).start();
        }
    }

}