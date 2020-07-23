package cn.zull.lpc.common.monitor.config;

import cn.zull.lpc.common.monitor.MonitorDataHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2020/7/22 09:59:15
 */
@Configuration
@ComponentScan("cn.zull.lpc.common.monitor")
public class MonitorConfig {
    @Bean
    public MonitorDataHandler monitorDataHandler() {
        return MonitorDataHandler.getInstance();
    }
}
