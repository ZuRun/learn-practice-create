package cn.zull.lpc.common.mq.rocketmq.aliyun;

import cn.zull.lpc.common.mq.core.IMessageExt;
import cn.zull.lpc.common.mq.core.IMessageListener;
import cn.zull.lpc.common.mq.core.IMqPushConsumer;
import com.aliyun.openservices.ons.api.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * @author zurun
 * @date 2019/1/27 20:46:40
 */
@Slf4j
public class AliyunRocketMqPushConsumer implements IMqPushConsumer {

    private volatile Consumer consumer;
    private final Properties properties;
    private IMessageListener messageListener;


    public AliyunRocketMqPushConsumer(String accessKey, String secretKey) {
        this.properties = new Properties();
        this.properties.setProperty(PropertyKeyConst.AccessKey, accessKey);
        this.properties.setProperty(PropertyKeyConst.SecretKey, secretKey);
    }

    @Override
    public void setConsumerGroup(String group) {
        this.properties.setProperty(PropertyKeyConst.GROUP_ID, group);
    }

    @Override
    public void subscribe(String topic, String subExpression, IMessageListener messageListener) {
        consumerCheckNull();
        this.messageListener = messageListener;
        log.info("[subscribe success], topic:{}, tag:{}", topic, subExpression);
        MessageListener listener = new MessageListenerImpl();
        this.consumer.subscribe(topic, subExpression, listener);
    }

    private void consumerCheckNull() {
        if (this.consumer == null) {
            synchronized (this) {
                if (this.consumer == null) {
                    this.consumer = ONSFactory.createConsumer(properties);
                }
            }
        }
    }

    @Override
    public void setInstanceName(String instanceName) {
        this.properties.setProperty(PropertyKeyConst.InstanceName, instanceName);
    }

    @Override
    public void setNamesrvAddr(String namesrvAddr) {
        this.properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, namesrvAddr);
    }

    @Override
    public void start() {
        this.consumer.start();
    }

    @Override
    public void shutdown() {
        this.consumer.shutdown();
    }

    class MessageListenerImpl implements MessageListener {

        @Override
        public Action consume(Message message, ConsumeContext consumeContext) {
            IMessageExt iMessageExt = new AliyunMessageExt(message);

            boolean isRetry = messageListener.consumer(iMessageExt);
            if (isRetry) {
                // 如果需要重试
                int delayLevelWhenNextConsume = 0;
                message.setStartDeliverTime(delayLevelWhenNextConsume);
                return Action.ReconsumeLater;
            }
            return Action.CommitMessage;
        }
    }

}
