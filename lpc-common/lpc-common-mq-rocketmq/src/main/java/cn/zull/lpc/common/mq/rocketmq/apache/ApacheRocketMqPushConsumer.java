package cn.zull.lpc.common.mq.rocketmq.apache;


import cn.zull.lpc.common.mq.core.IMessageExt;
import cn.zull.lpc.common.mq.core.IMessageListener;
import cn.zull.lpc.common.mq.core.IMqPushConsumer;
import cn.zull.lpc.common.mq.core.constants.ErrorCode;
import cn.zull.lpc.common.mq.core.constants.MqException;
import cn.zull.lpc.common.basis.utils.Assert;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author zurun
 * @date 2019/1/27 20:36:30
 */
@Slf4j
@Data
public class ApacheRocketMqPushConsumer implements IMqPushConsumer {
    private DefaultMQPushConsumer mqPushConsumer;

    private IMessageListener messageListener;
    /**
     * 消息重试策略
     * Message consume retry strategy<br>
     * -1, no retry,put into DLQ directly<br>
     * 0, broker control retry frequency<br>
     * >0, client control retry frequency
     */
    private int delayLevelWhenNextConsume = 0;

    /**
     * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
     * 如果非第一次启动，那么按照上次消费的位置继续消费
     */
    private ConsumeFromWhere consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET;

    public ApacheRocketMqPushConsumer() {
        this.mqPushConsumer = new DefaultMQPushConsumer();
    }

    @Override
    public void setConsumerGroup(String group) {
        this.mqPushConsumer.setConsumerGroup(group);
    }

    @Override
    public void subscribe(String topic, String subExpression, IMessageListener messageListener) {
        this.messageListener = messageListener;

        try {
            this.mqPushConsumer.subscribe(topic, subExpression);
            log.info("[subscribe success], topic:{}, tag:{}", topic, subExpression);
        } catch (MQClientException e) {
            e.printStackTrace();
            log.warn("[subscribe failed], topic:{}, tag:{}", topic, subExpression);

            throw new MqException(e.getMessage());
        }
        MessageListenerConcurrently listener = new DefaultMessageListenerConcurrently();

        this.mqPushConsumer.setMessageListener(listener);
    }

    @Override
    public void setInstanceName(String instanceName) {
        this.mqPushConsumer.setInstanceName(instanceName);
    }

    @Override
    public void setNamesrvAddr(String namesrvAddr) {
        this.mqPushConsumer.setNamesrvAddr(namesrvAddr);

    }

    @Override
    public void start() {
        //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        this.mqPushConsumer.setConsumeFromWhere(consumeFromWhere);
        // 检查
        Assert.notEmpty(this.mqPushConsumer.getNamesrvAddr(), "未指定NameServer地址!");
        try {
            this.mqPushConsumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
            log.error("[mq启动失败] e:{}", e.getMessage());
            throw new MqException(ErrorCode.mq.MQ_START_FAILD);
        }
    }

    @Override
    public void shutdown() {
        this.mqPushConsumer.shutdown();
    }

    /**
     * 默认的并行的方式处理消息,不追求时间顺序
     */
    public class DefaultMessageListenerConcurrently implements MessageListenerConcurrently {

        @Override
        @SuppressWarnings("unchecked")
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            for (MessageExt messageExt : msgs) {
                IMessageExt iMessageExt = new ApacheMessageExt(messageExt);
                boolean isRetry = messageListener.consumer(iMessageExt);
                if (isRetry) {
                    return retry(context);
                }
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        /**
         * 重试
         *
         * @param context
         * @return
         */
        private ConsumeConcurrentlyStatus retry(ConsumeConcurrentlyContext context) {
            context.setDelayLevelWhenNextConsume(delayLevelWhenNextConsume);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

}
