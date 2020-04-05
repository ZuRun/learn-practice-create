package cn.zull.lpc.common.mq.core;

/**
 * @author zurun
 * @date 2019/1/27 20:33:29
 */
public interface IMqPushConsumer {
    void setConsumerGroup(String group);

    void subscribe(final String topic, final String subExpression, IMessageListener messageListener);

    /**
     * 必须调用,不然如果一个服务启动了多个mq消费者,会报错
     *
     * @param instanceName
     */
    void setInstanceName(String instanceName);

    /**
     * 地址
     *
     * @param namesrvAddr
     */
    void setNamesrvAddr(String namesrvAddr);

    /**
     * 外部不要调用此方法了,apache与aliyun的rocketmq启动顺序不一样,apache的要先执行subscribe方法(listener)才能执行start(),
     * aliyun的刚好相反
     */
    void start();

    void shutdown();
}
