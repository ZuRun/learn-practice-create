package cn.zull.lpc.common.mq.rocketmq;


import cn.zull.lpc.common.mq.core.IMessageExt;
import cn.zull.lpc.common.mq.core.IMqProducer;
import cn.zull.lpc.common.mq.core.IMqSendResult;
import cn.zull.lpc.common.mq.core.constants.ConsumerTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author zurun
 * @date 2018/3/2 00:20:52
 */
public abstract class AbstractRocketMqProducer implements RocketMqProducer {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IMqProducer producer;
    @Value("${lpc.mq.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    protected AbstractRocketMqProducer() {
    }

    /**
     * producerGroup
     *
     * @return
     */
    protected abstract String producerGroup();

    /**
     * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
     * 注意：ProducerGroupName需要由应用来保证唯一<br>
     * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
     * 因为服务器会回查这个Group下的任意一个Producer
     */
    @PostConstruct
    protected void init() {
        this.producer.setNamesrvAddr(namesrvAddr);
        this.producer.setProducerGroup(producerGroup());
        //TODO-zurun 其他参数

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        this.producer.start();
    }

    @Override
    public IMqSendResult send(ConsumerTag tag, String keys, String body) {
        return this.producer.send(tag, keys, body);
    }

    @Override
    public IMqSendResult send(ConsumerTag tag, String keys, String body, Consumer<IMessageExt> consumer) {
        return this.producer.send(tag, keys, body, consumer);
    }

    /**
     * 单向（oneway）发送特点为只负责发送消息，
     * 不等待服务器回应且没有回调函数触发，即只发送请求不等待应答。
     * 此方式发送消息的过程耗时非常短，一般在微秒级别。
     *
     * @param msg msg
     */
    @Override
    public void sendOneWayMsg(IMessageExt msg) {
        try {
            producer.sendOneway(msg);
        } catch (Exception e) {
            logger.error("send msg error", e);
        }
    }


    /**
     * 发送延迟消息.
     *
     * @return send result
     * <p>
     * public boolean sendDelayMsg(IMessageExt msg, int delayLevel) {
     * msg.setDelayTimeLevel(delayLevel);
     * SendResult sendResult = null;
     * try {
     * sendResult = producer.send(msg);
     * } catch (Exception e) {
     * logger.error("send msg error", e);
     * }
     * return sendResult != null && sendResult.getSendStatus() == SendStatus.SEND_OK;
     * }
     */

    /**
     * 销毁
     */
    @PreDestroy
    private void destroy() {
        if (Objects.nonNull(producer)) {
            logger.info("producer shutdown");
            producer.shutdown();
            logger.info("producer has shutdown");
        }
    }
}
