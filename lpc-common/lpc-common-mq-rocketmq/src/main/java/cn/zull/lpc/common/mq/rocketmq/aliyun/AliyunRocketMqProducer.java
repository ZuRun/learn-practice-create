package cn.zull.lpc.common.mq.rocketmq.aliyun;


import cn.zull.lpc.common.mq.core.IMessageExt;
import cn.zull.lpc.common.mq.core.IMqProducer;
import cn.zull.lpc.common.mq.core.IMqSendResult;
import cn.zull.lpc.common.mq.core.constants.ConsumerTag;
import cn.zull.lpc.common.basis.utils.StringUtils;
import com.aliyun.openservices.ons.api.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;
import java.util.function.Consumer;

/**
 * @author zurun
 * @date 2019/1/28 13:48:11
 */
@Slf4j
public class AliyunRocketMqProducer<T extends Message> implements IMqProducer<T> {
    private Producer producer;
    private Properties properties;

    public AliyunRocketMqProducer(String accessKey, String secretKey) {
//        this.producer = new ProducerBean();
        this.properties = new Properties();
        this.properties.setProperty(PropertyKeyConst.AccessKey, accessKey);
        this.properties.setProperty(PropertyKeyConst.SecretKey, secretKey);

    }

    @Override
    public void setNamesrvAddr(String namesrvAddr) {
        this.properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, namesrvAddr);
    }

    @Override
    public void setProducerGroup(String producerGroup) {
        this.properties.setProperty(PropertyKeyConst.GROUP_ID, producerGroup);

    }

    @Override
    public IMqSendResult send(ConsumerTag tag, String keys, String body, Consumer<IMessageExt> consumer) {
        Message message = new Message(tag.getTopic(), tag.getTag(), keys, StringUtils.getBytesUtf8(body));
        IMessageExt messageExt = new AliyunMessageExt(message);
        consumer.accept(messageExt);
        SendResult sendResult = this.producer.send(message);
        return new AliyunSendResult(sendResult);
    }

    @Override
    public IMqSendResult send(ConsumerTag tag, String keys, String body) {
        Message message = new Message(tag.getTopic(), tag.getTag(), keys, StringUtils.getBytesUtf8(body));
        SendResult sendResult = this.producer.send(message);
        return new AliyunSendResult(sendResult);
    }

    @Override
    public void sendOneway(IMessageExt<T> messageExt) {
        this.producer.sendOneway(messageExt.get());
    }

    @Override
    public void start() {
        this.producer = ONSFactory.createProducer(this.properties);
        this.producer.start();
    }

    @Override
    public void shutdown() {
        this.producer.shutdown();
    }
}
