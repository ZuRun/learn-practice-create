package cn.zull.lpc.common.kafka.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.List;

/**
 * @author zurun
 * @date 2020/8/21 15:51:01
 */
@Getter
@Setter
@EqualsAndHashCode
public class KafkaConsumerCluster {
    private List<KafkaConsumer<String, String>> consumerList;

    public void close() {
        consumerList.forEach(KafkaConsumer::close);
    }
}
