package cn.zull.lpc.common.mq.rocketmq;

import cn.zull.lpc.common.basis.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * @author zurun
 * @date 2018/10/7 23:36:56
 */
@Slf4j
@Component
@ConditionalOnExpression(value = "${lpc.mq.rocketmq.producer.enable:false}")
public class DefaultRocketMqProducer extends AbstractRocketMqProducer {

    @Value("${lpc.mq.rocketmq.producer.group:PG_DEFAULT}")
    private String producerGroup;

    @Override
    protected String producerGroup() {
        log.info("新生产组配置producerGroup = {}",producerGroup);
        if (!StringUtils.isEmpty(producerGroup)) {
            return producerGroup;
        }

        log.warn("请使用新的producerGroup配置项 : iflytek.rocketmq.producerGroup");
        return "PG_DEFAULT";
    }

}
