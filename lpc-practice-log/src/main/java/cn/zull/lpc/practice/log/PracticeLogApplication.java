package cn.zull.lpc.practice.log;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author ZuRun
 * @date 2020/9/10 16:13:02
 */
@SpringBootApplication
public class PracticeLogApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PracticeLogApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
