package cn.zull.lpc.common.redis.jedis;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zurun
 * @date 2020/4/9 10:54:07
 */
@SpringBootApplication
public class JedisTestApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(JedisTestApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
