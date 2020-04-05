package cn.zull.lpc.common.mq.rocketmq.apache.test;

import cn.zull.lpc.common.basis.utils.UUIDUtils;
import cn.zull.lpc.common.mq.core.IMqSendResult;
import cn.zull.lpc.common.mq.rocketmq.RocketMqProducer;
import cn.zull.lpc.common.mq.rocketmq.apache.ApacheRocketMqTestApplication;
import cn.zull.lpc.common.mq.rocketmq.biz.TagTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZuRun
 * @date 2020/4/5 22:03:04
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApacheRocketMqTestApplication.class)
public class MqTest1 {
    @Autowired
    private RocketMqProducer producer;

    @Test
    public void producer() {
        String key = UUIDUtils.simpleUUID();
        IMqSendResult send = producer.send(TagTest.TEST_1, key, key + "_body");
        log.info("key:{} msgId:{} status:{}", key, send.getMsgId(), send.getSendStatus());
    }
}
