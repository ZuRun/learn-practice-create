package cn.zull.lpc.common.mq.rocketmq.aliyun.example;

import cn.zull.lpc.common.basis.utils.StringUtils;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Properties;

/**
 * @author zurun
 * @date 2019/1/31 09:57:14
 */
public class SimpleMQProducer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.GROUP_ID, MqConfig.CONSUMER_ID);
        properties.setProperty(PropertyKeyConst.AccessKey, MqConfig.ACCESS_KEY);
        properties.setProperty(PropertyKeyConst.SecretKey, MqConfig.SECRET_KEY);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, MqConfig.ONSADDR);

        Producer producer = ONSFactory.createProducer(properties);
        producer.start();
        Message message = new Message(MqConfig.TOPIC, MqConfig.TAG, "key001", StringUtils.getBytesUtf8("xxxxxxxxbody"));
        producer.send(message);
    }
}
