package cn.zull.lpc.common.redis.redisson.distributedlock;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁,方法上注解
 *
 * @author zurun
 * @date 2018/4/2 14:07:54
 * @see DistributedLockAspect
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLockAnnotation {
    /**
     * 锁的名称。
     * 如果lockName可以确定，直接设置该属性.
     */
    String lockName() default "";

    /**
     * 序号,方法锁 key:lockName+方法上的参数,从0开始
     * -1 则不加方法上的参数,单纯方法锁
     *
     * @return
     */
    int[] args() default -1;

    /**
     * 是否使用公平锁。
     * 公平锁即先来先得。
     */
    boolean fairLock() default false;

    /**
     * 是否使用尝试锁。 暂时只支持尝试锁
     */
//    boolean tryLock() default true;

    /**
     * 最长等待时间。
     * 该字段只有当tryLock()返回true才有效。
     */
    long waitTime() default 3L;

    /**
     * 锁超时时间。
     * 超时时间过后，锁自动释放。
     * 建议：
     * 尽量缩简需要加锁的逻辑。
     */
    long leaseTime() default 5L;

    /**
     * 时间单位。默认为秒。
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
