package cn.zull.lpc.common.redis.lettuce.configuration;

import cn.zull.lpc.common.redis.core.StringRedisCache;
import cn.zull.lpc.common.redis.lettuce.LettuceCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author zurun
 * @date 2020/4/3 11:09:50
 */
@Slf4j
@Configuration
@ComponentScan("cn.zull.lpc.common.redis.lettuce")
public class LettuceConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory factory) {
        StringRedisTemplate stringTemplate = new StringRedisTemplate();
        stringTemplate.setConnectionFactory(factory);
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        stringTemplate.setKeySerializer(redisSerializer);
        stringTemplate.setHashKeySerializer(redisSerializer);
        stringTemplate.setValueSerializer(redisSerializer);
        stringTemplate.afterPropertiesSet();
        return stringTemplate;
    }

    @Bean("lettuceCache")
    public StringRedisCache lettuceCache(RedisTemplate<String, String> redisTemplate) {

        return new LettuceCache(redisTemplate);
    }
}
