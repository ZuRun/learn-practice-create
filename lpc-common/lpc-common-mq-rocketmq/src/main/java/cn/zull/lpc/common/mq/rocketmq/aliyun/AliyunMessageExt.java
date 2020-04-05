package cn.zull.lpc.common.mq.rocketmq.aliyun;

import cn.zull.lpc.common.mq.core.IMessageExt;
import com.aliyun.openservices.ons.api.Message;
import org.springframework.util.Assert;


/**
 * @author zurun
 * @date 2019/1/27 20:19:43
 */
public class AliyunMessageExt implements IMessageExt<Message> {
    private Message message;

    public AliyunMessageExt(Message message) {
        Assert.notNull(message);
        this.message = message;
    }

    @Override
    public String getKeys() {
        return message.getKey();
    }

    @Override
    public String getMsgId() {
        return message.getMsgID();
    }

    @Override
    public String getTags() {
        return message.getTag();
    }

    @Override
    public String getTopic() {
        return message.getTopic();
    }

    @Override
    public String getUserProperty(String name) {
        return message.getUserProperties(name);
    }

    @Override
    public void putUserProperty(String key, String value) {
        message.putUserProperties(key, value);
    }

    @Override
    public byte[] getBody() {
        return message.getBody();
    }

    @Override
    public Message get() {
        return message;
    }

    @Override
    public String toString() {
        return message.toString();
    }
}
