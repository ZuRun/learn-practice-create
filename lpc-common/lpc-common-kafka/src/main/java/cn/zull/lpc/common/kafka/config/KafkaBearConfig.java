package cn.zull.lpc.common.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zurun
 * @date 2020/8/12 09:37:26
 */
@Slf4j
@Configuration
public class KafkaBearConfig {
    @Value("${lpc.kafka.serverAddr}")
    private String serverAddr;
    //    private String serverAddr = "172.31.129.144:9092";
    @Value("${lpc.kafka.consumer.topic:,}")
    private String topic;

    @Bean
    @ConditionalOnExpression(value = "${lpc.kafka.producer.enable:false}")
    public KafkaProducer<String, String> kafkaProducer() {

        Serializer<String> serializer = new StringSerializer();
        Map<String, String> properties = new HashMap<>(16);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddr);
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer(properties, serializer, serializer);
        return kafkaProducer;
    }

    @Bean
    @ConditionalOnExpression(value = "${lpc.kafka.consumer.enable:false}")
    public KafkaConsumer kafkaConsumer() {
        String[] split = topic.split(",");
        List<String> topics = new ArrayList<>(split.length);
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (StringUtils.isEmpty(s)) {
                continue;
            }
            topics.add(s);
        }
        if (topics.size() == 0) {
            log.warn("[topic长度为0] topic:{}", topic);
            throw new IllegalArgumentException("topic异常:" + topic);
        }
        Deserializer<String> deserializer = new StringDeserializer();
        Map<String, String> properties = new HashMap<>(16);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddr);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test1");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer(properties, deserializer, deserializer);
        kafkaConsumer.subscribe(topics);

        return kafkaConsumer;
    }

}
