package cn.zull.lpc.common.mq.rocketmq.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author zurun
 * @date 2019/1/28 10:56:37
 */
@Order(2)
@Slf4j
@Configuration
@ComponentScan("cn.zull.lpc.common.mq.rocketmq")
public class RocketMqConfig implements CommandLineRunner {

    @Value("${lpc.mq.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Override
    public void run(String... args) throws Exception {
        log.info("[初始化mq] namesrvAddr:{} ", namesrvAddr);
    }
}
