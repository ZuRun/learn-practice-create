package cn.zull.lpc.practice.kafka.provider;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zurun
 * @date 2020/8/10 11:23:59
 */
@SpringBootApplication
public class KafkaProviderApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(KafkaProviderApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
