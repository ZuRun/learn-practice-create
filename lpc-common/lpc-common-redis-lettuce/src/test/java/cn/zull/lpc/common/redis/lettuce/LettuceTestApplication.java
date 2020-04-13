package cn.zull.lpc.common.redis.lettuce;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zurun
 * @date 2020/4/3 11:14:11
 */
@SpringBootApplication
public class LettuceTestApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(LettuceTestApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
