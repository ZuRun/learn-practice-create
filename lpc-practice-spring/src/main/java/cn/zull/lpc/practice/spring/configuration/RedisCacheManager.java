package cn.zull.lpc.practice.spring.configuration;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;

/**
 * @author zurun
 * @date 2020/3/25 17:09:58
 */
public class RedisCacheManager implements CacheManager {
    @Override
    public Cache getCache(String name) {
        return null;
    }

    @Override
    public Collection<String> getCacheNames() {
        return null;
    }
}
