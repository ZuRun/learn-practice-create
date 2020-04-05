package cn.zull.lpc.common.mq.rocketmq.apache;


import cn.zull.lpc.common.mq.core.ESendStatus;
import cn.zull.lpc.common.mq.core.IMqSendResult;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;

/**
 * @author zurun
 * @date 2019/1/28 16:33:28
 */
public class ApacheSendResult implements IMqSendResult {
    SendResult sendResult;

    public ApacheSendResult(SendResult sendResult) {
        this.sendResult = sendResult;
    }

    @Override
    public ESendStatus getSendStatus() {
        SendStatus sendStatus = sendResult.getSendStatus();
        return ESendStatus.valueOf(sendStatus.name());
    }

    @Override
    public String getMsgId() {
        return sendResult.getMsgId();
    }
}
