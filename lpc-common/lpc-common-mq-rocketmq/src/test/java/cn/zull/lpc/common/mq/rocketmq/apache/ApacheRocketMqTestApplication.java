package cn.zull.lpc.common.mq.rocketmq.apache;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author ZuRun
 * @date 2020/4/5 22:02:34
 */
@SpringBootApplication
public class ApacheRocketMqTestApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ApacheRocketMqTestApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
