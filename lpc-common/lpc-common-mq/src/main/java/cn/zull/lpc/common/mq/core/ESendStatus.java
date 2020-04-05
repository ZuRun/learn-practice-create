package cn.zull.lpc.common.mq.core;

/**
 * @author zurun
 * @date 2019/1/28 16:35:26
 */
public enum ESendStatus {
    /**
     * 消息发送状态
     */
    SEND_OK,
    FLUSH_DISK_TIMEOUT,
    FLUSH_SLAVE_TIMEOUT,
    SLAVE_NOT_AVAILABLE;
}
