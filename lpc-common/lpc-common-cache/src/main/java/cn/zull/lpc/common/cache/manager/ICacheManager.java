package cn.zull.lpc.common.cache.manager;

import java.util.concurrent.TimeUnit;

/**
 * @author zurun
 * @date 2020/3/26 14:58:22
 */
public interface ICacheManager<K> {

    /**
     * 读取缓存
     *
     * @param cacheName
     * @return
     */
    <V> V getCache(K cacheName, Class<V> returnType);

    boolean setCache(K cacheName, Object cache);

    boolean setCache(K cacheName, Object cache, long ttl, TimeUnit timeUnit);

    boolean delCache(K cacheName);
}
