package cn.zull.lpc.practice.kafka.consumer;

import cn.zull.lpc.common.basis.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @author zurun
 * @date 2020/8/10 16:23:37
 */
@Slf4j
@Service
public class KafkaConsumerTest implements CommandLineRunner {
    private final String topic = "TEST_ELK";
    @Autowired
    KafkaConsumer<String, String> kafkaConsumer;

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
//            Deserializer<String> deserializer = new StringDeserializer();
//            Map<String, String> properties = new HashMap<>(16);
//            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.31.129.144:9092");
//            properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test1");
//            KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer(properties, deserializer, deserializer);
            try {


//                kafkaConsumer.subscribe(Collections.singleton(topic));
                while (true) {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(5000));
                    records.iterator().forEachRemaining(record -> {
                        System.out.println(  record.key() + "_" + JsonUtils.toJSONString(record));
                    });
                }
            } finally {
                kafkaConsumer.close();
            }
        },"kafka-consumer-thread").start();
    }
}
