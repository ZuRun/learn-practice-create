package cn.zull.lpc.common.mq.rocketmq.apache.configuration;


import cn.zull.lpc.common.mq.core.IMqProducer;
import cn.zull.lpc.common.mq.core.IMqPushConsumer;
import cn.zull.lpc.common.mq.rocketmq.apache.ApacheRocketMqProducer;
import cn.zull.lpc.common.mq.rocketmq.apache.ApacheRocketMqPushConsumer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2019/1/29 09:52:52
 */
@Configuration
@ConditionalOnExpression("'${lpc.mq.rocketmq.client:apache}'.equals('apache')")
public class ApacheRocketMqConfig {

    @Bean
    public IMqPushConsumer iMqPushConsumer() {
        IMqPushConsumer mqPushConsumer = new ApacheRocketMqPushConsumer();
        return mqPushConsumer;
    }

    @Bean
    public IMqProducer iMqProducer() {
        return new ApacheRocketMqProducer();
    }
}
