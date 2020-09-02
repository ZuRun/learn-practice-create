package cn.zull.lpc.practice.log2kafka.log;

import cn.zull.lpc.common.basis.utils.JsonUtils;
import cn.zull.lpc.common.basis.utils.StringUtils;
import cn.zull.lpc.practice.log2kafka.model.LogModel;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
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
    private final List<Header> headers = Collections.singletonList(
            new RecordHeader("_v", StringUtils.getBytesUtf8("1.1")));


    public static AtomicInteger sum = new AtomicInteger(0);

    public LogConsumer2Kafka() {
        System.out.println("===");
    }

    @PostConstruct
    public synchronized void start() {
        System.out.println("--------------s--");
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
                    LogModel logModel = Log2kafkaQueue.take(10, TimeUnit.MILLISECONDS);

                    if (logModel != null) {
                        list.add(logModel);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("线程中断 : " + Thread.currentThread().getName() + "  剩余未消费长度:" + Log2kafkaQueue.queueSize());
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (list.size() > 0) {
                try {
                    write2Kafka(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void write2Kafka(List<LogModel> list) {
        final String topic = "lpc_log_2";
//        ProducerRecord producerRecord = new ProducerRecord(topic, JsonUtils.toJSONString(list));
        ProducerRecord producerRecord = new ProducerRecord(topic, null, null, null, JsonUtils.toJSONString(list), headers);
        Future<RecordMetadata> future = kafkaProducer.send(producerRecord);
//        ListenableFuture<SendResult<String, String>> send = kafkaProducer.send(topic, JsonUtils.toJSONString(list));
        sum.getAndIncrement();

    }
}
