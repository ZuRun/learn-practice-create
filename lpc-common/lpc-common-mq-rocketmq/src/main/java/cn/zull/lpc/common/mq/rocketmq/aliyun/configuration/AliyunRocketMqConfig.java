package cn.zull.lpc.common.mq.rocketmq.aliyun.configuration;


import cn.zull.lpc.common.mq.core.IMqProducer;
import cn.zull.lpc.common.mq.core.IMqPushConsumer;
import cn.zull.lpc.common.mq.rocketmq.RocketMqClient;
import cn.zull.lpc.common.mq.rocketmq.aliyun.AliyunRocketMqProducer;
import cn.zull.lpc.common.mq.rocketmq.aliyun.AliyunRocketMqPushConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2019/1/27 19:55:34
 */
@Configuration
@ConditionalOnExpression("${lpc.mq.rocketmq.client:apache}==" + RocketMqClient.ALIYUN)
public class AliyunRocketMqConfig {
    @Value("${lpc.mq.rocketmq.aliyun.accessKey}")
    private String accessKey;
    @Value("${lpc.mq.rocketmq.aliyun.secretKey}")
    private String secretKey;


    @Bean
    public IMqPushConsumer iMqPushConsumer() {
        return new AliyunRocketMqPushConsumer(accessKey, secretKey);
    }

    @Bean
    public IMqProducer iMqProducer() {
        return new AliyunRocketMqProducer(accessKey, secretKey);
    }
}
