package cn.zull.lpc.common.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZuRun
 * @date 2020/9/21 14:44:41
 */
@SpringBootApplication
@EnableApolloConfig
public class ApolloTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApolloTestApplication.class, args);
    }
}
