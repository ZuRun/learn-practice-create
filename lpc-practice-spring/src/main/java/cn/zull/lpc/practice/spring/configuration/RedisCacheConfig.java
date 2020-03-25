package cn.zull.lpc.practice.spring.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2020/3/25 17:06:29
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Override
    public CacheManager cacheManager() {
        return new RedisCacheManager();
    }
}
