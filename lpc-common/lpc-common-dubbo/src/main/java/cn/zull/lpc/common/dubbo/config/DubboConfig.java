package cn.zull.lpc.common.dubbo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zurun
 * @date 2020/7/22 10:49:13
 */
@Configuration
@ComponentScan("cn.zull.lpc.common.dubbo")
@PropertySource(value = "classpath:/dubbo-config.properties")
public class DubboConfig {
}
