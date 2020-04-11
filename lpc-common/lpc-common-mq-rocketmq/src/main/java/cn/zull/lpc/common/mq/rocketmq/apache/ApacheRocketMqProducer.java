package cn.zull.lpc.common.mq.rocketmq.apache;


import cn.zull.lpc.common.mq.core.IMessageExt;
import cn.zull.lpc.common.mq.core.IMqProducer;
import cn.zull.lpc.common.mq.core.IMqSendResult;
import cn.zull.lpc.common.mq.core.constants.ConsumerTag;
import cn.zull.lpc.common.mq.core.constants.ErrorCode;
import cn.zull.lpc.common.mq.core.constants.MqException;
import cn.zull.lpc.common.basis.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.function.Consumer;

/**
 * @author zurun
 * @date 2019/1/28 11:55:53
 */
@Slf4j
public class ApacheRocketMqProducer<T extends Message> implements IMqProducer<T> {
    private DefaultMQProducer producer;

    public ApacheRocketMqProducer() {
        producer = new DefaultMQProducer();
    }

    @Override
    public void setNamesrvAddr(String namesrvAddr) {
        this.producer.setNamesrvAddr(namesrvAddr);
    }

    @Override
    public void setProducerGroup(String producerGroup) {
        this.producer.setProducerGroup(producerGroup);
    }

    @Override
    public IMqSendResult send(ConsumerTag tag, String keys, String body, Consumer<IMessageExt> consumer) {
        Message message = new Message(tag.getTopic(), tag.getTag(), keys, StringUtils.getBytesUtf8(body));
        IMessageExt messageExt = new ApacheMessageExt(message);
        consumer.accept(messageExt);
        try {
            SendResult sendResult = this.producer.send(message);
            return new ApacheSendResult(sendResult);
        } catch (Exception e) {
            log.error("[send msg error] : eName:{} msg:{}", e.getClass().getSimpleName(), e.getMessage());
            throw new MqException(ErrorCode.mq.DEFAULT_MQ_PROVIDER_ERROR);
        }
    }

    @Override
    public IMqSendResult send(ConsumerTag tag, String keys, String body) {
        try {
            Message message = new Message(tag.getTopic(), tag.getTag(), keys, StringUtils.getBytesUtf8(body));
            SendResult sendResult = this.producer.send(message);
            return new ApacheSendResult(sendResult);
        } catch (Exception e) {
            log.error("[send msg error] : eName:{} msg:{}", e.getClass().getSimpleName(), e.getMessage());
            throw new MqException(e, ErrorCode.mq.MQ_SEND_FAILD);
        }
    }

    @Override
    public void sendOneway(IMessageExt<T> messageExt) {
        try {
            this.producer.sendOneway(new MessageExt());
            this.producer.sendOneway(messageExt.get());
        } catch (Exception e) {
            log.error("[send oneway msg error] : {}", e.getMessage());
            throw new MqException(ErrorCode.mq.MQ_SEND_FAILD);
        }
    }

    @Override
    public void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
            log.error("[mq启动失败] e:{}", e.getMessage());
            throw new MqException(ErrorCode.mq.MQ_START_FAILD);
        }
    }

    @Override
    public void shutdown() {
        this.producer.shutdown();
    }


}
