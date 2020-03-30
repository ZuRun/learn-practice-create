package cn.zull.lpc.common.cache.annotation;

import java.lang.annotation.*;

/**
 * 根据条件删除缓存
 * <p>
 * 注意,以下场景此注解不生效
 * 1. 不能加在静态方法上
 * 2. 被同一个类中方法调用时
 *
 * @author zurun
 * @date 2020/3/30 10:02:46
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheEvict {
    /**
     * 暂时不考虑多个的情况,如有需要再行添加
     *
     * @return
     */
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
     * 是否在方法执行前删除
     *
     * @return
     */
    boolean beforeInvocation() default true;
}
