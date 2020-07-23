package cn.zull.lpc.common.monitor;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zurun
 * @date 2020/7/22 10:28:13
 */
@ComponentScan("cn.zull.lpc.common.monitor")
@SpringBootApplication
public class MonitorTestApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(MonitorTestApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
