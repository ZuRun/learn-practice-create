package cn.zull.lpc.practice.log2kafka.log;

import cn.zull.lpc.common.basis.utils.JsonUtils;
import cn.zull.lpc.practice.log2kafka.bean.LogBean;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zurun
 * @date 2020/8/11 19:15:23
 */
@Component
public class LogConsumer2Kafka {
    private volatile boolean start = false;

    @Value("${log2kafka.consumer.batchSize:50}")
    private int batchSize;
    @Autowired
    private KafkaProducer<String, String> kafkaProducer;
    AtomicInteger sum = new AtomicInteger(0);

    {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            int s = sum.getAndSet(0);
            System.out.println(s);
        }, 0, 1, TimeUnit.SECONDS);
    }

    @PostConstruct
    public synchronized void start() {
        if (start) {
            return;
        }
        new Thread(runnable, "log2kafka-consumer-1").start();
//        new Thread(runnable, "log2kafka-consumer-2").start();
        start = true;
    }

    Runnable runnable = () -> {
        while (!Thread.currentThread().isInterrupted()) {
            List<LogBean> list = new ArrayList<>(20);
            try {
                for (int i = 0; i < batchSize; i++) {
                    LogBean logbean = Log2kafkaQueue.take(10, TimeUnit.MILLISECONDS);

                    if (logbean != null) {
                        list.add(logbean);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            if (list.size() > 0) {
                write2Kafka(list);
            }
        }
    };

    private void write2Kafka(List<LogBean> list) {
        final String topic = "lpc_log";
        ProducerRecord producerRecord = new ProducerRecord(topic, JsonUtils.toJSONString(list));
        Future<RecordMetadata> future = kafkaProducer.send(producerRecord);
        sum.getAndIncrement();

    }
}
