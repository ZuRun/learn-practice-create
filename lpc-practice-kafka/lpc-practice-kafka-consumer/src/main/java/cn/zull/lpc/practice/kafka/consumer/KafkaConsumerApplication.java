package cn.zull.lpc.practice.kafka.consumer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zurun
 * @date 2020/8/10 16:22:56
 */
@SpringBootApplication
public class KafkaConsumerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(KafkaConsumerApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
