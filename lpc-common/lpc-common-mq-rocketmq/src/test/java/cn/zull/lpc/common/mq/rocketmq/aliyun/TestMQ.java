package cn.zull.lpc.common.mq.rocketmq.aliyun;

import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.message.Message;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.message.MessageExt;

import java.util.Properties;

/**
 * @author zurun
 * @date 2019/1/24 21:25:21
 */
public class TestMQ {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();

    //    ConsumerBean
    public void test() {
        ProducerBean producer = new ProducerBean();
        Properties properties = new Properties();
        properties.setProperty("AccessKey", "");
        properties.setProperty("SecretKey", "");
        producer.setProperties(properties);
        ConsumerBean consumerBean = new ConsumerBean();
        consumerBean.setProperties(properties);
//        consumerBean.

        Message message = new Message();

        MessageExt messageExt=new MessageExt();
        messageExt.getKeys();
    }
}
