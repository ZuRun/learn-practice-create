package cn.zull.lpc.common.mq.rocketmq.biz;


import cn.zull.lpc.common.mq.core.constants.ConsumerTopic;

/**
 * @author zurun
 * @date 2018/10/16 18:17:48
 */
public enum Topic implements ConsumerTopic {

    /**
     * 测试topic
     */
    TOPIC_TEST("TOPIC_TEST"),

    ;

    private String topic;

    Topic(String topic) {
        this.topic = topic;
    }

    @Override
    public String getTopic() {
        return this.topic;
    }
}
