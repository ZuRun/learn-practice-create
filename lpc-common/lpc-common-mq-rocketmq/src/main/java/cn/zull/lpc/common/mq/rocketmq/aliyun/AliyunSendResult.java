package cn.zull.lpc.common.mq.rocketmq.aliyun;


import cn.zull.lpc.common.mq.core.ESendStatus;
import cn.zull.lpc.common.mq.core.IMqSendResult;
import com.aliyun.openservices.ons.api.SendResult;

/**
 * @author zurun
 * @date 2019/1/28 16:40:43
 */
public class AliyunSendResult implements IMqSendResult {
    private SendResult sendResult;

    public AliyunSendResult(SendResult sendResult) {
        this.sendResult = sendResult;
    }

    @Override
    public ESendStatus getSendStatus() {
        return ESendStatus.SEND_OK;
    }

    @Override
    public String getMsgId() {
        return sendResult.getMessageId();
    }

    public String getTopic() {
        return sendResult.getTopic();
    }
}
