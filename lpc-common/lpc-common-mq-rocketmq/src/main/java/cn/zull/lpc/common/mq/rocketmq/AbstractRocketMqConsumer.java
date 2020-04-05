package cn.zull.lpc.common.mq.rocketmq;


import cn.zull.lpc.common.basis.exception.BusinessException;
import cn.zull.lpc.common.basis.exception.LpcRuntimeException;
import cn.zull.lpc.common.mq.core.IMessageExt;
import cn.zull.lpc.common.mq.core.IMqPushConsumer;
import cn.zull.lpc.common.mq.core.constants.ConsumerTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.function.BiFunction;

/**
 * mq消费者抽象类,只需继承此类即可使用
 * <p>
 * 注意:需要设置namesrvAddr,consumerGroup
 *
 * @author zurun
 * @date 2018/3/2 15:42:40
 */
@Slf4j
public abstract class AbstractRocketMqConsumer {
    @Value("${lpc.mq.rocketmq.namesrvAddr}")
    private String namesrvAddr;

//    @Autowired
//    private RocketmqTraceContext traceContext;

    @Autowired
    private IMqPushConsumer mqPushConsumer;

    /**
     * 是否启动,只能启动一次
     */
    private boolean isStarted;
    /**
     * 消息重试策略
     * Message consume retry strategy<br>
     * -1, no retry,put into DLQ directly<br>
     * 0, broker control retry frequency<br>
     * >0, client control retry frequency
     */
    private int delayLevelWhenNextConsume = 0;


    /**
     * 暂不通过构造方法
     */
    protected AbstractRocketMqConsumer() {
    }

    /**
     * 需要订阅的 标签
     * <p>
     *
     * @param set Set<Tags>  PushTopic下指定Tag的消息
     * @return
     */
    public abstract void subscribeTopicTags(Set<ConsumerTag> set);

    /**
     * 收到的信息进行处理(消费)
     *
     * @param messageExt
     * @param biFunction {@link #function} 是否消费成功,异常或者消费失败是否重试, 返回`是否需要重试`
     * @return true表示为不需要重复消费, false表示需要重复消费
     */
    public abstract RocketMqResult consumeMsg(IMessageExt messageExt, BiFunction<Boolean, Boolean, RocketMqResult> biFunction);

    /**
     * isSuccess: 是否消费成功
     * isRetry: 异常或者消费失败是否重试
     * return: 返回`是否需要重试`
     */
    private BiFunction<Boolean, Boolean, RocketMqResult> function = (isSuccess, isRetry) -> {
        if (isSuccess) {
            return RocketMqResult.SUCCESS;
        }
        if (isRetry) {
            return RocketMqResult.FAIL_AND_RETRY;
        }
        return RocketMqResult.FAIL_NOT_RETRY;
    };

    private boolean consumeWithTracing(IMessageExt messageExt) {
        //TODO-zurun 链路
//        return TracingLogPostProcessingUtils.collectionLog(traceContext.consumer(traceDTO -> {
//        }, messageExt), traceLog -> {
//            traceLog.setTraceType("rocketmq-consumer");
//            RocketMqResult result = consumeMsg(messageExt, function);
//            if (RocketMqResult.SUCCESS.equals(result)) {
//                return true;
//            }
//            traceLog.setStatus(TraceStatusEnum.FAIL);
//            return true;
//        });

        RocketMqResult result = consumeMsg(messageExt, function);
        if (RocketMqResult.SUCCESS.equals(result)) {
            return true;
        }
        return true;
    }

    /**
     * 消费失败是否需要重试,此方法建议重写
     *
     * @return
     */
    public Boolean isRetry() {
        return true;
    }

    /**
     * 消费者的组名
     *
     * @return
     */
    public abstract String getConsumerGroup();


    @PostConstruct
    protected void init() {
        Assert.notNull(getConsumerGroup(), "未指定ConsumerGroup!");
        mqPushConsumer.setConsumerGroup(getConsumerGroup());
        // 不加的话,如果一个服务启动了多个mq消费者,会报错
        String instanceName = getClass().getSimpleName();
        mqPushConsumer.setInstanceName(instanceName);
        mqPushConsumer.setNamesrvAddr(namesrvAddr);

        Assert.isTrue(!isStarted, "container already started.");

        /**
         * 解析需要订阅的主题标签
         * 直接用set是为了实现方法中写起来简单,虽然解析起来比较麻烦
         */
        Set<ConsumerTag> set = new HashSet<>();
        subscribeTopicTags(set);
        /**
         * mq订阅需要的格式为 tag1 || tag2 || tag3
         * 先遍历set 将有相同的rocketMqTopic的rocketMqTag放在同一个set中
         * Map<rocketMqTopic,Set<rocketMqTag>>
         */
        Map<String, Set<String>> map = new HashMap<>(16);
        set.forEach(consumerTag -> {
            String rocketMqTopic = consumerTag.getTopic();
            String rocketMqTag = consumerTag.getTag();
            Assert.notNull(rocketMqTag, "标签不为空,如需全部订阅,请使用*");
            if (map.containsKey(rocketMqTopic)) {
                map.get(rocketMqTopic).add(rocketMqTag);
            } else {
                HashSet<String> tags = new HashSet<>();
                tags.add(rocketMqTag);
                map.put(rocketMqTopic, tags);
            }
        });
        /**
         * 格式化为需要的subscription expression
         */
        map.forEach((rocketMqTopic, stringSet) -> {
            StringBuffer sb = new StringBuffer();
            stringSet.forEach(s -> sb.append(s).append(" || "));
            String rocketMqTags = sb.toString();
            mqPushConsumer.subscribe(rocketMqTopic, "*", this::consumer);
//            mqPushConsumer.subscribe(rocketMqTopic, rocketMqTags, this::consumer);
        });

        this.isStarted = true;
        mqPushConsumer.start();
        log.info("[mq消费方启动] instanceName:{} addr:{}", instanceName, namesrvAddr);
    }


    /**
     * 销毁
     */
    @PreDestroy
    private void destroy() {
        if (Objects.nonNull(mqPushConsumer)) {
            log.info("consumer shutdown");
            mqPushConsumer.shutdown();
        }
    }

    public boolean consumer(IMessageExt messageExt) {
        try {
            long now = System.currentTimeMillis();
            // 消费失败,则重试!
            if (!consumeWithTracing(messageExt)) {
                return true;
            }
            long costTime = System.currentTimeMillis() - now;
            log.info("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
        } catch (Exception e) {
            boolean isRetry = isRetry();
            e.printStackTrace();
            if (e instanceof BusinessException) {
                log.warn("[consume message failed] retry:{} eName:{} code:{} errMsg:{}",
                        isRetry, e.getClass().getSimpleName(), ((BusinessException) e).getErrCode(), e.getMessage());
            } else if (e instanceof LpcRuntimeException) {
                log.warn("[consume message failed] retry:{} eName:{} code:{} errMsg:{}",
                        isRetry, e.getClass().getSimpleName(), ((LpcRuntimeException) e).getErrCode(), e.getMessage());
            } else {
                e.printStackTrace();
                log.warn("[consume message failed] retry:{} eName:{} errMsg:{}",
                        isRetry, e.getClass().getSimpleName(), e.getMessage());
            }
            log.info("[failed messageExt] messageExt:{}", isRetry, e.getMessage(), messageExt);
            // 判断是否需要重试
            if (isRetry) {
                return true;
            }
        }
        return false;
    }


    /**
     * 消费结果
     */
    public enum RocketMqResult {
        /**
         * 消费成功
         */
        SUCCESS,
        /**
         * 消费失败,但不需要重试
         */
        FAIL_NOT_RETRY,
        /**
         * 消费失败,需要重试
         */
        FAIL_AND_RETRY,
        ;
    }

}
