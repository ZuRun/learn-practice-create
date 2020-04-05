package cn.zull.lpc.common.mq.core;

/**
 * @author zurun
 * @date 2019/1/27 20:06:32
 */
public interface IMessageExt<T> {
    String getKeys();

    String getMsgId();

    String getTags();

    String getTopic();

    String getUserProperty(final String name);

    void putUserProperty(String key, String value);

    byte[] getBody();

    T get();

    @Override
    String toString();
}
