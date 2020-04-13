package cn.zull.lpc.common.cache.configuration;

import cn.zull.lpc.common.cache.manager.ConcurrentMapCacheManager;
import cn.zull.lpc.common.cache.manager.ICacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2020/3/26 17:15:45
 */
@Configuration
@ComponentScan("cn.zull.lpc.common.cache")
public class CacheConfig {

    @Bean("iCacheManager")
    public ICacheManager iCacheManager() {
        return new ConcurrentMapCacheManager();
//        return new RedisCacheManager();
    }
}
