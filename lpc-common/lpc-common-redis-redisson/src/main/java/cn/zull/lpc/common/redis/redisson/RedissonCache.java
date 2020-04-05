package cn.zull.lpc.common.redis.redisson;

import cn.zull.lpc.common.redis.core.IRedisCache;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zurun
 * @date 2018/10/18 15:35:31
 */
@Slf4j
public class RedissonCache implements IRedisCache<String, String, String> {
    final RedissonClient redissonClient;

    public RedissonCache(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean expire(String key, long expirationTime, TimeUnit unit) {
        return redissonClient.getBucket(key).expire(expirationTime, unit);
    }

    @Override
    public long pttl(String key) {
        return redissonClient.getBucket(key).remainTimeToLive();
    }

    @Override
    public long incr(String key) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        return atomicLong.incrementAndGet();
    }

    @Override
    public String get(String key) {
        return (String) redissonClient.getBucket(key).get();
    }

    @Override
    public void set(String key, String value) {
        RBucket<String> rBucket = redissonClient.getBucket(key);
        rBucket.set(value);
    }

    @Override
    public void set(String key, String value, long expirationTime, TimeUnit unit) {
        RBucket<String> rBucket = redissonClient.getBucket(key);
        rBucket.set(value, expirationTime, unit);
    }

    @Override
    public boolean setNx(String key, String value) {
        RBucket<String> rBucket = redissonClient.getBucket(key);
        return rBucket.trySet(value);
    }

    @Override
    public boolean setNx(String key, String value, long expirationTime, TimeUnit unit) {
        RBucket<String> rBucket = redissonClient.getBucket(key);
        return rBucket.trySet(value, expirationTime, unit);
    }

    @Override
    public String hGet(String key, String hashKey) {
        RMap<String, String> rMap = redissonClient.getMap(key);
        return rMap.get(hashKey);
    }

    @Override
    public Map<String, String> hGetMap(String key, String... hashKey) {
        Set<String> hashKeys = Sets.newHashSet(hashKey);
        RMap<String, String> rMap = redissonClient.getMap(key);
        return rMap.getAll(hashKeys);
    }

    @Override
    public Map<String, String> hGetAll(String key) {
        RMap<String, String> rMap = redissonClient.getMap(key);
        return rMap.readAllMap();
    }

    @Override
    public void hSet(String key, String hKey, String hValue) {
        RMap<String, String> rMap = redissonClient.getMap(key);
        rMap.put(hKey, hValue);
    }

    @Override
    public void hSet(String key, Map<String, String> map) {
        if (map.size() == 0) {
            log.warn("[redis参数非法] hSet map不能为空 key:{}", key);
            return;
        }
        RMap<String, String> rMap = redissonClient.getMap(key);
        rMap.putAll(map);
    }

    @Override
    public void hSet(String key, Map<String, String> map, long expirationTime, TimeUnit unit) {
        if (map.size() == 0) {
            log.warn("[redis参数非法] hSet map不能为空 key:{}", key);
            return;
        }
        RBatch batch = redissonClient.createBatch();
        batch.getMap(key).putAllAsync(map);
        batch.getBucket(key).expireAsync(expirationTime, unit);
        batch.execute();
    }

    @Override
    public Boolean hasKey(String key) {
        return redissonClient.getBucket(key).isExists();
    }

    @Override
    public long deleteByPrefix(String prefix) {
        return redissonClient.getKeys().deleteByPattern(prefix + "*");
    }

    @Override
    public boolean delete(String key) {
        RBucket<String> rBucket = redissonClient.getBucket(key);
        return rBucket.delete();
    }

    @Override
    public boolean sisMember(String key, String member) {
        return redissonClient.getSet(key).contains(member);
    }

    @Override
    public boolean sAdd(String key, String member) {
        return redissonClient.getSet(key).add(member);
    }

    @Override
    public boolean sAddAll(String key, String... member) {
        return redissonClient.getSet(key).addAll(Arrays.asList(member));
    }

    @Override
    public boolean sAddAll(String key, Collection member) {
        return redissonClient.getSet(key).addAll(member);
    }

    @Override
    public boolean sRem(String key, String member) {
        return redissonClient.getSet(key).remove(member);
    }

    @Override
    public boolean sRemMore(String key, Collection member) {
        return redissonClient.getSet(key).remove(member);
    }

    @Override
    public String bLPop(String key, long timeout) throws InterruptedException {
        return bLPop(key, timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public String bLPop(String key, long timeout, TimeUnit unit) throws InterruptedException {
        RBlockingQueue<String> blockingQueue = redissonClient.getBlockingQueue(key);
        return blockingQueue.poll(timeout, unit);
    }

    @Override
    public List<String> matchLPop(String key, int length) {
        if (length <= 0) {
            length = 1;
        }
        final String script = "local l=ARGV[1] local list=redis.call('lrange',KEYS[1],'0',l-1) redis.call('ltrim',KEYS[1],l,'-1') return list";
        List<String> list = redissonClient.getScript().eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.MULTI, Collections.singletonList(key), length);
        return list;
    }

    @Override
    public boolean rPush(String key, String... value) {
        return rPush(key, Arrays.asList(value));
    }

    @Override
    public boolean rPush(String key, List<String> values) {
        RList<String> list = redissonClient.getList(key);
        return list.addAll(values);
    }

    /**
     * list长度
     *
     * @param key
     * @return
     */
    @Override
    public int llen(String key) {
        RList<String> list = redissonClient.getList(key);
        return list.size();
    }

    @Override
    public boolean pfAdd(String key, String element) {
        RHyperLogLog<String> hyperLogLog = redissonClient.getHyperLogLog(key);
        return hyperLogLog.add(element);
    }

    @Override
    public boolean pfAddAll(String key, Collection<String> elements) {
        RHyperLogLog<String> hyperLogLog = redissonClient.getHyperLogLog(key);
        return hyperLogLog.addAll(elements);
    }

    public RFuture<Boolean> pfAddAllAsync(String key, Collection<String> elements) {
        RHyperLogLog<String> hyperLogLog = redissonClient.getHyperLogLog(key);
        return hyperLogLog.addAllAsync(elements);
    }

    @Override
    public long pfCount(String key) {
        RHyperLogLog<String> hyperLogLog = redissonClient.getHyperLogLog(key);
        return hyperLogLog.count();
    }

    @Override
    public void pfMerge(String key, String... otherLogNames) {
        RHyperLogLog<String> hyperLogLog = redissonClient.getHyperLogLog(key);
        hyperLogLog.mergeWith(otherLogNames);
    }


    @Override
    public Set<String> scanWithMatch(String pattern) {
        RKeys rKeys = redissonClient.getKeys();
        Iterable<String> keys = rKeys.getKeysByPattern(pattern, 200);
        return Sets.newHashSet(keys);
    }

    @Override
    public Set<String> scanWithMatch(String pattern, Integer count) {
        RKeys rKeys = redissonClient.getKeys();
        Iterable<String> keys = rKeys.getKeysByPattern(pattern, count);
        return Sets.newHashSet(keys);
    }

    @Override
    public long dbsize() {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.count();
    }
}
