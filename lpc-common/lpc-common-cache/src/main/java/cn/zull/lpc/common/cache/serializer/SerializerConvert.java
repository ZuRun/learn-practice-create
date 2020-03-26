package cn.zull.lpc.common.cache.serializer;

/**
 * @author zurun
 * @date 2020/3/26 15:05:58
 */
public interface SerializerConvert<T> {

    /**
     * 反序列化
     *
     * @param var
     * @return
     */
    T deserialize(String var, Class<T> c);

    /**
     * 序列化
     *
     * @param t
     * @return
     */
    String serialize(T t);
}
