package cn.zull.lpc.common.mq.core;

/**
 * @author zurun
 * @date 2019/1/27 20:50:23
 */
@FunctionalInterface
public interface IMessageListener {
    /**
     * @param messageExt
     * @return 是否重试
     */
    boolean consumer(IMessageExt messageExt);
}
