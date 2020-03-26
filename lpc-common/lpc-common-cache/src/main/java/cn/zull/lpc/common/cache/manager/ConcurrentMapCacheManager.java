package cn.zull.lpc.common.cache.manager;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author zurun
 * @date 2020/3/26 14:58:10
 */
@Slf4j
public class ConcurrentMapCacheManager implements ICacheManager<String> {
    private final Map<String, Object> map = new ConcurrentHashMap(16);

    @Override
    public <V> V getCache(String cacheName, Class<V> returnType) {
        return (V) map.get(cacheName);
    }

    @Override
    public boolean setCache(String cacheName, Object cache) {
        map.put(cacheName, cache);
        return true;
    }

    @Override
    public boolean setCache(String cacheName, Object cache, long ttl, TimeUnit timeUnit) {
        setCache(cacheName, cache);
        return true;
    }
}
