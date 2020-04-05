package cn.zull.lpc.common.redis.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zurun
 * @date 2018/10/15 00:03:25
 */
public interface IRedisCache<V, HK, HV> {
    boolean expire(String key, long expirationTime, TimeUnit unit);

    /**
     * 查看key剩余的过期时间
     *
     * @param key
     * @return time in milliseconds
     * -2 if the key does not exist.
     * -1 if the key exists but has no associated expire.
     */
    long pttl(String key);

    /**
     * 自增
     *
     * @param key
     * @return
     */
    long incr(String key);

    V get(String key);

    void set(String key, V value);

    /**
     * @param key
     * @param value
     * @param expirationTime 过期时间
     * @param unit           TimeUnit
     */
    void set(String key, V value, long expirationTime, TimeUnit unit);

    /**
     * redis SETNX命令
     *
     * @param key
     * @param value
     * @return {@code true} 之前不存在, or {@code false} 缓存中已存在
     * @see <a href="http://redis.io/commands/setnx">Redis Documentation: SETNX</a>
     */
    boolean setNx(String key, V value);

    boolean setNx(String key, V value, long expirationTime, TimeUnit unit);


    /**
     * @param key
     * @param hashKey
     * @return
     */
    HV hGet(String key, HK hashKey);

    /**
     * hGet多个hashKey值
     * <p>
     * 如果key不存在,返回不为null,而是一个空的linkedMap.
     * 如果hashKey未找到,map中不包含此key
     * <p>
     * 不可以通过map.size来判断是否有key
     *
     * @param key
     * @param hashKey
     * @return
     */
    Map<String, String> hGetMap(String key, String... hashKey);

    /**
     * 获取hash类型的集合
     *
     * @param key
     * @return
     */
    Map<String, String> hGetAll(String key);

    /**
     * HSET
     *
     * @param key
     * @param hKey
     * @param hValue
     */
    void hSet(String key, HK hKey, HV hValue);

    void hSet(String key, Map<String, String> map);

    void hSet(String key, Map<String, String> map, long timeout, TimeUnit unit);

    /**
     * 是否包含key
     *
     * @param key
     * @return
     */
    Boolean hasKey(String key);

    /**
     * 根据前缀批量删除
     *
     * @param prefix
     * @return
     */
    long deleteByPrefix(String prefix);

    /**
     * 删除
     *
     * @param key
     * @return
     */
    boolean delete(String key);

    /**
     * set中是否包含member
     *
     * @param key
     * @param member
     * @return
     */
    boolean sisMember(String key, String member);

    /**
     * set中新增
     *
     * @param key
     * @param member
     * @return
     */
    boolean sAdd(String key, String member);

    /**
     * set中新增多string
     *
     * @param key
     * @param member
     * @return
     */
    public boolean sAddAll(String key, String... member);

    /**
     * set中新增collection
     *
     * @param key
     * @param member
     * @return
     */
    public boolean sAddAll(String key, Collection member);

    boolean sRem(String key, String member);

    boolean sRemMore(String key, Collection member);

    /**
     * 目前只支持每次pop一个数据,需要多个的话,参考 {@link #matchLPop}
     *
     * @param key
     * @param timeout
     * @return
     * @throws InterruptedException
     */
    String bLPop(String key, long timeout) throws InterruptedException;

    String bLPop(String key, long timeout, TimeUnit unit) throws InterruptedException;

    /**
     * lPop多个数据,此方法为从head开始pop
     * 两个命令拼接:lrange+ltrip,为保证原子性,需要使用lua脚本
     * 此方法返回值不为空,无结果的话,会返回空集合
     * <p>
     * 注意:此方法非阻塞
     * <p>
     * -- l字段为:需要lRange的长度
     * local l
     * if tonumber(ARGV[1])<=0 then l='1' else l=ARGV[1] end
     * local list=redis.call('lrange',KEYS[1],'0',l-1)
     * redis.call('ltrim',KEYS[1],l,'-1')
     * return list
     *
     * @param key
     * @param length 一次lpop的数量
     * @return
     */
    List<String> matchLPop(String key, int length);

    boolean rPush(String key, String... value);

    boolean rPush(String key, List<String> values);

    /**
     * list长度
     *
     * @param key
     * @return
     */
    int llen(String key);

    boolean pfAdd(String key, String element);

    boolean pfAddAll(String key, Collection<String> elements);

    long pfCount(String key);

    void pfMerge(String key, String... otherLogNames);

    /**
     * 遍历key,此方法会遍历所有key,每次遍历200,遍历完为止,禁用
     *
     * @param pattern
     * @return
     */
    @Deprecated
    Set<String> scanWithMatch(String pattern);

    /**
     * 遍历key,此方法会遍历所有key,慎用
     *
     * @param pattern key的模糊值
     * @param count   一次请求查询数
     * @return
     */
    @Deprecated
    Set<String> scanWithMatch(String pattern, Integer count);

    /**
     * key的数量
     *
     * @return
     */
    long dbsize();
}
