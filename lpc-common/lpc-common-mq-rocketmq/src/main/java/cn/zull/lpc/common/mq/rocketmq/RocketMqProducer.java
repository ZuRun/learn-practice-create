package cn.zull.lpc.common.mq.rocketmq;


import cn.zull.lpc.common.mq.core.IMessageExt;
import cn.zull.lpc.common.mq.core.IMqSendResult;
import cn.zull.lpc.common.mq.core.constants.ConsumerTag;

import java.util.function.Consumer;

/**
 * @author zurun
 * @date 2019/2/21 22:38:10
 */
public interface RocketMqProducer {
    IMqSendResult send(ConsumerTag tag, String keys, String body);

    IMqSendResult send(ConsumerTag tag, String keys, String body, Consumer<IMessageExt> consumer);

    void sendOneWayMsg(IMessageExt msg);
}
