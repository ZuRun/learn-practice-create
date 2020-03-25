package cn.zull.common.redis.redisson.distributedlock;


import cn.zull.common.redis.redisson.exception.DistributedLockException;
import cn.zull.lpc.common.basis.constants.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zurun
 * @date 2018/10/18 17:18:03
 */
@Slf4j
@Component
public class DistributedLockTemplate implements DistributedLock {
    private final RedissonClient redissonClient;

    public DistributedLockTemplate(@Qualifier("redissonClient") RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public <T> T tryLock(String lockName, SuperDistributedLockCallback<T> callback, long waitTime, long leaseTime, TimeUnit timeUnit, boolean fairLock) {
        RLock lock = getLock(lockName, fairLock);
        // 锁是否被释放
        boolean isReleaseLock = true;
        try {
            boolean isLocked = lock.tryLock(waitTime, leaseTime, timeUnit);
            isReleaseLock = false;
            T t = null;

            // 判断callback是否需要有返回值
            if (callback instanceof DistributedLockCallback) {
                t = ((DistributedLockCallback<T>) callback).process(isLocked);
            } else if (callback instanceof DistributedLockConsumerCallback) {
                ((DistributedLockConsumerCallback) callback).process(isLocked);
            }

            if (isLocked) {
                lock.unlock();
            }
            return t;
        } catch (InterruptedException e) {
            log.warn("[分布式锁中断异常] msg:{}", e.getMessage());
            throw new DistributedLockException(ErrorCode.distributedLock.DISTRIBUTED_LOCK_INTERRUPTED_EXCEPTION);
        } finally {
            if (!isReleaseLock) {
                lock.unlock();
            }
        }
    }


    @Override
    public RLock getLock(String lockName, boolean fairLock) {
        RLock lock;
        if (fairLock) {
            lock = redissonClient.getFairLock(lockName);
        } else {
            lock = redissonClient.getLock(lockName);
        }
        return lock;
    }

}
