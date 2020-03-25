package cn.zull.lpc.create.cache.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zurun
 * @date 2020/3/25 18:10:50
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacheable {

    /**
     * 默认过期时间 30分钟
     *
     * @return
     */
    long expire() default 30;

    TimeUnit timeUnit() default TimeUnit.MINUTES;
}
