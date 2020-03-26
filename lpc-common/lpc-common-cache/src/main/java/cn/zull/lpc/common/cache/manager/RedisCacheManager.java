package cn.zull.lpc.common.cache.manager;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author zurun
 * @date 2020/3/26 14:58:10
 */
@Slf4j
public class RedisCacheManager implements ICacheManager<String> {

    @Override
    public <V> V getCache(String cacheName, Class<V> returnType) {
        return null;
    }

    @Override
    public boolean setCache(String cacheName, Object cache) {

        return true;
    }

    @Override
    public boolean setCache(String cacheName, Object cache, long ttl, TimeUnit timeUnit) {
        return true;
    }

    private void checkCacheSize(String cacheName, String cache) {
        if (cache.length() > 5000L) {
            log.warn("[cache较大] cacheName={} cache={}", cacheName, cache);
        }
    }
}
