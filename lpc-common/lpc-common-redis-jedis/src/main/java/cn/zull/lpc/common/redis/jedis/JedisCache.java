package cn.zull.lpc.common.redis.jedis;

import cn.zull.lpc.common.redis.core.StringRedisCache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zurun
 * @date 2020/4/9 10:50:32
 */
public class JedisCache implements StringRedisCache {

    private final RedisTemplate<String, String> redisTemplate;

    public JedisCache(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean expire(String key, long expirationTime, TimeUnit unit) {
        return false;
    }

    @Override
    public long pttl(String key) {
        return 0;
    }

    @Override
    public long incr(String key) {
        return 0;
    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void set(String key, String value) {

    }

    @Override
    public void set(String key, String value, long expirationTime, TimeUnit unit) {

    }

    @Override
    public boolean setNx(String key, String value) {
        return false;
    }

    @Override
    public boolean setNx(String key, String value, long expirationTime, TimeUnit unit) {
        return false;
    }

    @Override
    public String hGet(String key, String hashKey) {
        return null;
    }

    @Override
    public Map<String, String> hGetMap(String key, String... hashKey) {
        return null;
    }

    @Override
    public Map<String, String> hGetAll(String key) {
        return null;
    }

    @Override
    public void hSet(String key, String hKey, String hValue) {

    }

    @Override
    public void hSet(String key, Map<String, String> map) {

    }

    @Override
    public void hSet(String key, Map<String, String> map, long timeout, TimeUnit unit) {

    }

    @Override
    public Boolean hasKey(String key) {
        return null;
    }

    @Override
    public long deleteByPrefix(String prefix) {
        return 0;
    }

    @Override
    public boolean delete(String key) {
        return false;
    }

    @Override
    public boolean sisMember(String key, String member) {
        return false;
    }

    @Override
    public boolean sAdd(String key, String member) {
        return false;
    }

    @Override
    public boolean sAddAll(String key, String... member) {
        return false;
    }

    @Override
    public boolean sAddAll(String key, Collection member) {
        return false;
    }

    @Override
    public boolean sRem(String key, String member) {
        return false;
    }

    @Override
    public boolean sRemMore(String key, Collection member) {
        return false;
    }

    @Override
    public String bLPop(String key, long timeout) throws InterruptedException {
        return null;
    }

    @Override
    public String bLPop(String key, long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public List<String> matchLPop(String key, int length) {
        return null;
    }

    @Override
    public boolean rPush(String key, String... value) {
        return false;
    }

    @Override
    public boolean rPush(String key, List<String> values) {
        return false;
    }

    @Override
    public int llen(String key) {
        return 0;
    }

    @Override
    public boolean pfAdd(String key, String element) {
        return false;
    }

    @Override
    public boolean pfAddAll(String key, Collection<String> elements) {
        return false;
    }

    @Override
    public long pfCount(String key) {
        return 0;
    }

    @Override
    public void pfMerge(String key, String... otherLogNames) {

    }

    @Override
    public Set<String> scanWithMatch(String pattern) {
        return null;
    }

    @Override
    public Set<String> scanWithMatch(String pattern, Integer count) {
        return null;
    }

    @Override
    public long dbsize() {
        return 0;
    }
}
