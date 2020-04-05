package cn.zull.lpc.common.mq.rocketmq.apache;


import cn.zull.lpc.common.mq.core.IMessageExt;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.Assert;

/**
 * @author zurun
 * @date 2019/1/27 21:22:42
 */
@Slf4j
public class ApacheMessageExt implements IMessageExt<Message> {
    private Message message;

    public ApacheMessageExt(Message message) {
        Assert.notNull(message);
        this.message = message;
    }

    @Override
    public String getKeys() {
        return message.getKeys();
    }

    @Override
    public String getMsgId() {
        if (message instanceof MessageExt) {
            return ((MessageExt) message).getMsgId();
        }
        log.warn("don't have msg id");
        return "don't have msg id";

    }

    @Override
    public String getTags() {
        return message.getTags();
    }

    @Override
    public String getTopic() {
        return message.getTopic();
    }

    @Override
    public String getUserProperty(String name) {
        return message.getUserProperty(name);
    }

    @Override
    public void putUserProperty(String key, String value) {
        message.putUserProperty(key, value);
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
