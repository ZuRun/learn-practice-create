package cn.zull.lpc.common.mq.core;

/**
 * @author zurun
 * @date 2019/1/28 14:17:53
 */
public interface IMqSendResult {
    ESendStatus getSendStatus();

    String getMsgId();
}
