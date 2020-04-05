package cn.zull.lpc.common.mq.core.constants;

/**
 * 消费者订阅主题-标签
 *
 * @author zurun
 * @date 2018/9/17 21:56:44
 */
public interface ConsumerTag {

    /**
     * 获取订阅主题
     *
     * @return
     */
    ConsumerTopic getConsumerTopic();

    String getTopic();

    /**
     * 获取订阅的主题下的标签
     *
     * @return
     */
    String getTag();
}
