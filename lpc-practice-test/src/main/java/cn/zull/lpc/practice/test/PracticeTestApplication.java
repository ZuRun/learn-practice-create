package cn.zull.lpc.practice.test;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author jared.zu
 * @date 2020/8/22 22:27:05
 */
@SpringBootApplication
public class PracticeTestApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PracticeTestApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
