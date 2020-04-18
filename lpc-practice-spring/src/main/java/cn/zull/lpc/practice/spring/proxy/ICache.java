package cn.zull.lpc.practice.spring.proxy;

/**
 * @author zurun
 * @date 2020/4/20 11:30:19
 */
public interface ICache {

    String get(String key);

    void set(String key, String value);
}
