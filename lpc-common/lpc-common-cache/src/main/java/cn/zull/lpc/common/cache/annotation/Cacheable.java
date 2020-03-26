package cn.zull.lpc.common.cache.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 注意,以下场景此注解不生效
 * 1. 不能加在静态方法上
 * 2. 被同一个类中方法调用时
 *
 * @author zurun
 * @date 2020/3/25 18:10:50
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacheable {

    String cacheName();

    /**
     * 使用SpEL表达式
     *
     * @return
     */
    String key();

    /**
     * 生效条件
     *
     * @return
     */
    String condition() default "";

    /**
     * 默认过期时间 30分钟
     *
     * @return
     */
    long ttl() default 30;

    TimeUnit timeUnit() default TimeUnit.MINUTES;
}
