package cn.zull.lpc.practice.kafka2es.consumer;

import cn.zull.lpc.common.basis.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
    KafkaConsumer<String, String> kafkaConsumer;
    @Value("${lpc.biz.w2es.thread.size:50}")
    private int threadSize;

    private final AtomicInteger sum = new AtomicInteger(0);

    {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println(System.currentTimeMillis() + " 消费条数:" + sum.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);

    }

    @Override
    public void run(String... args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(threadSize);

        new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(5000));
                    if (records.isEmpty()) {
                        return;
                    }
                    CountDownLatch countDownLatch = new CountDownLatch(records.count());
                    System.out.println(records.count());
                    Thread.sleep(1000);
                    records.iterator().forEachRemaining(record -> {
                        executorService.execute(() -> {
                            try {
                                String msg = record.value();

                                List<Map<String, Object>> list = JsonUtils.json2List(msg);
                                sum.getAndAdd(list.size());
                                write2es.batchInsertEs("lpc_log", list);
//                        System.out.println(record.topic() + "_" + record.key() + "_" + list);
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
        }, "kafka-consumer-thread").start();
    }
}
