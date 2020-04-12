package cn.zull.lpc.common.mq.rocketmq.apache.test;

import cn.zull.lpc.common.mq.rocketmq.AbstractRocketMqConsumer;
import cn.zull.lpc.common.mq.rocketmq.apache.ApacheRocketMqTestApplication;
import cn.zull.lpc.common.mq.rocketmq.apache.MqConsumer1;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZuRun
 * @date 2020/4/5 22:45:38
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApacheRocketMqTestApplication.class)
//@Component
public class ConsumerTest {
    @Bean
    public AbstractRocketMqConsumer consumer() {
        return new MqConsumer1();
    }


    @Test
    public void consumer1() throws InterruptedException {
        Thread.sleep(2000L);
    }
}
