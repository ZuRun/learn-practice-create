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
    @Value("${spring.kafka.bootstrap-servers}")
    private String serverAddr;

    @Value("${lpc.kafka.consumer.topic:,}")
    private String topic;

    @Value("${lpc.kafka.consumer.size:10}")
    private int consumerSize;

    @Value("${spring.kafka.consumer.max-poll-records:300}")
    private int maxPollRecords;

    @Value("${spring.kafka.consumer.fetch-min-size:100}")
    private int fetchMinSize;

    @Value("${spring.kafka.consumer.group-id:}")
    private String groupId;

    @Bean(destroyMethod = "close")
    @ConditionalOnExpression(value = "${lpc.kafka.producer.enable:false}")
    public KafkaProducer<String, String> kafkaProducer() {

        Serializer<String> serializer = new StringSerializer();
        Map<String, String> properties = new HashMap<>(16);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddr);
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer(properties, serializer, serializer);
        kafkaProducer.close();
        return kafkaProducer;
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnExpression(value = "${lpc.kafka.consumer.enable:false}")
    public KafkaConsumerCluster kafkaConsumer() {
        List<KafkaConsumer<String, String>> list = new ArrayList<>();
        for (int j = 0; j < consumerSize; j++) {
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
            if (StringUtils.isEmpty(groupId)) {
                log.warn("[group-id为空]");
            }
            Deserializer<String> deserializer = new StringDeserializer();
            Map<String, String> properties = new HashMap<>(16);
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddr);
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, String.valueOf(maxPollRecords));
            //todo 待确认
//            properties.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, String.valueOf(fetchMinSize));
            KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer(properties, deserializer, deserializer);
            kafkaConsumer.subscribe(topics);
            list.add(kafkaConsumer);
        }
        KafkaConsumerCluster cluster = new KafkaConsumerCluster();
        cluster.setConsumerList(list);
        return cluster;
    }

}
