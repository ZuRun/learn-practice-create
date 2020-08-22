package cn.zull.lpc.practice.es;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author jared.zu
 * @date 2020/8/22 21:21:41
 */
@SpringBootApplication
public class PracticeEsApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PracticeEsApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
