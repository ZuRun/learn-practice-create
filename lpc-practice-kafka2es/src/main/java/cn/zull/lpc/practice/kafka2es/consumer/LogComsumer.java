package cn.zull.lpc.practice.kafka2es.consumer;

import cn.zull.lpc.common.basis.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author zurun
 * @date 2020/8/17 10:06:04
 */
@Slf4j
@Component
public class LogComsumer implements CommandLineRunner {
    @Autowired
    KafkaConsumer<String, String> kafkaConsumer;

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(5000));
                    records.iterator().forEachRemaining(record -> {

                        System.out.println(record.topic() + "_" + record.key() + "_" + JsonUtils.toJSONString(record));
                    });
                }
            } finally {
                kafkaConsumer.close();
            }
        }, "kafka-consumer-thread").start();
    }
}
