package cn.zull.lpc.common.mq.rocketmq.biz;


import cn.zull.lpc.common.mq.core.constants.ConsumerTag;
import cn.zull.lpc.common.mq.core.constants.ConsumerTopic;

/**
 * @author zurun
 * @date 2019/5/27 20:22:51
 */
public enum TagTest implements ConsumerTag {
    /**
     * 测试tag1
     */
    TEST_1("test_1"),
    /**
     * 测试tag2
     */
    TEST_2("test_2"),
    ;
    private ConsumerTopic consumerTopic = Topic.TOPIC_TEST;
    private String tag;

    TagTest(String tag) {

        this.tag = tag;
    }

    @Override
    public ConsumerTopic getConsumerTopic() {
        return this.consumerTopic;
    }

    @Override
    public String getTopic() {
        return this.consumerTopic.getTopic();
    }

    @Override
    public String getTag() {
        return this.tag;
    }
}
