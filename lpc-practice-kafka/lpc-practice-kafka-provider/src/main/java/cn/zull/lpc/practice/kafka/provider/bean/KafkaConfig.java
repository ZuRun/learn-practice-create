package cn.zull.lpc.practice.kafka.provider.bean;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zurun
 * @date 2020/8/10 11:42:24
 */
@Slf4j
@Configuration
public class KafkaConfig {

    @Bean
    public KafkaProducer kafkaProducer() {

        Serializer<String> serializer = new StringSerializer();
        Map<String, String> properties = new HashMap<>(16);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.31.129.144:9092");
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer(properties, serializer, serializer);


        return kafkaProducer;
    }
}
