package cn.zull.lpc.practice.kafka2es;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zurun
 * @date 2020/8/17 10:03:17
 */
@SpringBootApplication
public class Kafka2EsApplication {
    public static void main(String[] args) {
//        SpringApplication.run(Kafka2EsApplication.class, args);
        new SpringApplicationBuilder(Kafka2EsApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
