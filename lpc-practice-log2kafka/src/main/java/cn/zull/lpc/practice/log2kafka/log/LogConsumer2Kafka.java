package cn.zull.lpc.practice.log2kafka.log;

import cn.zull.lpc.common.basis.utils.JsonUtils;
import cn.zull.lpc.practice.log2kafka.model.LogModel;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
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

    @Value("${lpc.biz.log.consumer.batchSize:50}")
    private int batchSize;

    @Value("${lpc.biz.log.consumer.thread:1}")
    private int consumerThread;

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    public static AtomicInteger sum = new AtomicInteger(0);


    @PostConstruct
    public synchronized void start() {
        if (start) {
            return;
        }
        for (int i = 0; i < consumerThread; i++) {
            new Thread(runnable, "log2kafka-consumer-" + consumerThread).start();
        }
        start = true;
    }

    Runnable runnable = () -> {
        while (!Thread.currentThread().isInterrupted()) {
            List<LogModel> list = new ArrayList<>(20);
            try {
                for (int i = 0; i < batchSize; i++) {
                    LogModel logbean = Log2kafkaQueue.take(10, TimeUnit.MILLISECONDS);

                    if (logbean != null) {
                        list.add(logbean);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("线程中断 : " + Thread.currentThread().getName()+"  剩余未消费长度:"+Log2kafkaQueue.queueSize());
                Thread.currentThread().interrupt();
            }
            if (list.size() > 0) {
                write2Kafka(list);
            }
        }
    };

    private void write2Kafka(List<LogModel> list) {
        final String topic = "lpc_log";
        ProducerRecord producerRecord = new ProducerRecord(topic, JsonUtils.toJSONString(list));
        Future<RecordMetadata> future = kafkaProducer.send(producerRecord);
        sum.getAndIncrement();

    }
}
