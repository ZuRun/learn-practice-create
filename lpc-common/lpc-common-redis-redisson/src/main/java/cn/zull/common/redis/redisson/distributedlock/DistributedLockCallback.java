package cn.zull.common.redis.redisson.distributedlock;

/**
 * 分布式锁回调接口
 *
 * @author ZuRun
 * @date 2018/03/31 11:02:53
 */
@FunctionalInterface
public interface DistributedLockCallback<T> extends SuperDistributedLockCallback<T>{
    /**
     * 调用者必须在此方法中实现需要加分布式锁的业务逻辑
     *
     * @param isLocked 是否上锁成功
     * @return
     */
    T process(boolean isLocked);

}
