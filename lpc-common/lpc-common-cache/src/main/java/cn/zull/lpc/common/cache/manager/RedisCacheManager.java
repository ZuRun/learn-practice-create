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
//        checkCacheSize(cacheName, cache);
        return true;
    }

    @Override
    public boolean setCache(String cacheName, Object cache, long ttl, TimeUnit timeUnit) {
//        checkCacheSize(cacheName, cache);

        return true;
    }

    @Override
    public boolean delCache(String cacheName) {
        return false;
    }

    private void checkCacheSize(String cacheName, String cache) {
        if (cache.length() > 5000L) {
            log.warn("[cache较大] cacheName={} cacheLength={}", cacheName, cache.length());
        }
    }

    public static void main(String[] args) {
        String str = "123 中文";
        System.out.println(str.length());
    }
}
