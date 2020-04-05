package cn.zull.lpc.common.mq.core;


import cn.zull.lpc.common.mq.core.constants.ConsumerTag;

import java.util.function.Consumer;

/**
 * @param <T> message
 * @author zurun
 * @date 2019/1/28 11:36:35
 */
public interface IMqProducer<T> {
    void setNamesrvAddr(String namesrvAddr);

    void setProducerGroup(String producerGroup);

    IMqSendResult send(ConsumerTag tag, String keys, String body, Consumer<IMessageExt> consumer);

    IMqSendResult send(ConsumerTag tag, String keys, String body);

    void sendOneway(IMessageExt<T> messageExt);

    void start();

    void shutdown();
}
