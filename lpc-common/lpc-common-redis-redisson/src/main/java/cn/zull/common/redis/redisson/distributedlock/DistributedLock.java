package cn.zull.common.redis.redisson.distributedlock;


import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @author zurun
 * @date 2018/10/18 16:46:20
 */
public interface DistributedLock {
    /**
     * 默认锁超时时间
     */
    long DEFAULT_TIMEOUT = 1000L;

    /**
     * 尝试获取锁
     *
     * @param lockName 锁名
     * @param callback
     * @param waitTime 获取锁最长等待时间(ms)
     * @param <T>
     * @return
     */
    default <T> T tryLock(String lockName, DistributedLockCallback<T> callback, long waitTime) {
        return tryLock(lockName, callback, waitTime, DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS, false);
    }

    /**
     * 尝试获取锁
     *
     * @param lockName 锁名
     * @param callback
     * @param waitTime 获取锁最长等待时间(ms)
     * @return
     */
    default void tryLock(String lockName, DistributedLockConsumerCallback callback, long waitTime) {
        tryLock(lockName, callback, waitTime, DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS, false);
    }

    /**
     * 尝试获取锁,2s等待
     *
     * @param lockName 锁名
     * @param callback
     * @param <T>
     * @return
     */
    default <T> T tryLock(String lockName, DistributedLockCallback<T> callback) {
        return tryLock(lockName, callback, 2000);
    }

    /**
     * 尝试获取锁,2s等待
     *
     * @param lockName 锁名
     * @param callback
     * @return
     */
    default void tryLock(String lockName, DistributedLockConsumerCallback callback) {
        tryLock(lockName, callback, 2000);
    }

    /**
     * 尝试获取锁
     *
     * @param lockName  锁名
     * @param callback
     * @param waitTime  获取锁最长等待时间
     * @param leaseTime 锁超时时间。超时后自动释放锁。
     * @param timeUnit
     * @param fairLock  是否使用公平锁
     * @param <T>
     * @return
     */
    <T> T tryLock(String lockName, SuperDistributedLockCallback<T> callback, long waitTime, long leaseTime, TimeUnit timeUnit, boolean fairLock);


    /**
     * 获取锁
     *
     * @param lockName 锁名
     * @param fairLock 是否使用公平锁
     * @return
     */
    RLock getLock(String lockName, boolean fairLock);

    /**
     * 获取锁,不使用公平锁
     *
     * @param lockName 锁名
     * @return
     */
    default RLock getLock(String lockName) {
        return getLock(lockName, false);
    }
}
