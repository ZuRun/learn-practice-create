package cn.zull.lpc.common.mq.rocketmq.apache;

import cn.zull.lpc.common.basis.utils.StringUtils;
import cn.zull.lpc.common.mq.core.IMessageExt;
import cn.zull.lpc.common.mq.core.constants.ConsumerTag;
import cn.zull.lpc.common.mq.rocketmq.AbstractRocketMqConsumer;
import cn.zull.lpc.common.mq.rocketmq.biz.TagTest;
import cn.zull.lpc.common.mq.rocketmq.biz.group.ConsumerGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.function.BiFunction;

/**
 * @author ZuRun
 * @date 2020/4/5 22:42:24
 */
@Slf4j
public class MqConsumer1 extends AbstractRocketMqConsumer {
    @Override
    public void subscribeTopicTags(Set<ConsumerTag> set) {
        set.add(TagTest.TEST_1);
    }

    @Override
    public RocketMqResult consumeMsg(IMessageExt messageExt, BiFunction<Boolean, Boolean, RocketMqResult> biFunction) {
        String msgId = messageExt.getMsgId();
        String keys = messageExt.getKeys();
        String body = StringUtils.newStringUtf8(messageExt.getBody());
        log.info("[consumer] keys:{} msgId:{} body:{}", keys, msgId, body);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return biFunction.apply(true, true);
    }

    @Override
    public String getConsumerGroup() {
        return ConsumerGroup.GROUP_TEST;
    }
}
