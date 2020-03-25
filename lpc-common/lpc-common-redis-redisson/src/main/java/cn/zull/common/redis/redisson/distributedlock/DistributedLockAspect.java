package cn.zull.common.redis.redisson.distributedlock;

import cn.zull.common.redis.redisson.exception.DistributedLockException;
import cn.zull.lpc.common.basis.constants.ErrorCode;
import cn.zull.lpc.common.basis.exception.BusinessException;
import cn.zull.lpc.common.basis.exception.LpcRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author zurun
 * @date 2018/4/2 14:35:01
 */
@Slf4j
@Aspect
@Component
public class DistributedLockAspect {
    final DistributedLock lock;

    public DistributedLockAspect(DistributedLock lock) {
        this.lock = lock;
    }

    @Pointcut(value = "@annotation(cn.zull.common.redis.redisson.distributedlock.DistributedLockAnnotation)")
    public void distributedLockAspect() {
    }

    @Around(value = "distributedLockAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        //切点所在的类
        Class targetClass = pjp.getTarget().getClass();
        //使用了注解的方法
        String methodName = pjp.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature) pjp.getSignature()).getMethod().getParameterTypes();
        Method method = targetClass.getMethod(methodName, parameterTypes);
        Object[] arguments = pjp.getArgs();
        log.info("[分布式锁切面] class:{} method:{}", targetClass.getSimpleName(), methodName);

        return lock(pjp, targetClass, method, arguments);
    }

    /**
     * 方法锁,根据类名和方法名作为lockName
     *
     * @param annotation
     * @param targetClass
     * @param method
     * @return
     */
    private String getLockName(DistributedLockAnnotation annotation, Class targetClass, Method method, Object[] arguments) {
        String lockName;
        if (StringUtils.isEmpty(annotation.lockName())) {
            lockName = targetClass.getName().concat(method.getName());
        } else {
            lockName = annotation.lockName();
        }
        int[] args = annotation.args();
        if (args.length == 1 && args[0] == -1) {
            // 锁名不加参数
        } else {
            for (int i = 0, length = args.length; i < length; i++) {
                int index = args[i];
                if (index >= arguments.length) {
                    log.warn("[分布式锁参数配置错误]");
                    throw new DistributedLockException(ErrorCode.distributedLock.ANNOATION_ARGS_ERROR);
                }
                Object obj = arguments[i];
                if (obj instanceof String || obj instanceof Integer || obj instanceof Long) {
                    if (i + 1 == args.length) {
                        lockName += obj;
                    } else {
                        lockName = lockName + obj + ":";
                    }
                } else {
                    log.warn("[分布式锁方法参数类型非法] index:{} arg:{}", index, obj);
                    throw new DistributedLockException(ErrorCode.distributedLock.ANNOATION_ARGS_ERROR);
                }
            }
        }

        log.info("[分布式方法锁] lockName:{}", lockName);
        return lockName;
    }

    /**
     * 锁
     *
     * @param pjp
     * @param method
     * @param targetClass
     * @return
     */
    private Object lock(ProceedingJoinPoint pjp, Class targetClass, Method method, Object[] arguments) {
        DistributedLockAnnotation annotation = method.getAnnotation(DistributedLockAnnotation.class);
        final String lockName = getLockName(annotation, targetClass, method, arguments);
        return lock.tryLock(lockName, (DistributedLockCallback<Object>) isLock -> proceed(pjp, targetClass, method, isLock),
                annotation.waitTime(), annotation.leaseTime(), annotation.timeUnit(), annotation.fairLock());
    }

    private Object proceed(ProceedingJoinPoint pjp, Class targetClass, Method method, boolean isLocked) {
        // 如果没有成功上锁,则不允许执行方法中内容,直接抛异常
        if (!isLocked) {
            log.warn("[切面获取锁失败] class:{} method:{}", targetClass.getClass().getSimpleName(), method.getName());
            throw new DistributedLockException("未能成功上锁!");
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            if (throwable instanceof BusinessException) {
                log.warn("[分布式锁切面捕获异常] eName:{} msg:{}", throwable.getClass().getSimpleName(), throwable.getMessage());
                throw (BusinessException) throwable;
            }
            log.error("[分布式锁切面捕获异常] eName:{} msg:{}", throwable.getClass().getSimpleName(), throwable.getMessage());
            if (throwable instanceof LpcRuntimeException) {
                throw (LpcRuntimeException) throwable;
            }
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            }
            throw new DistributedLockException(throwable, ErrorCode.distributedLock.DEFAULT_LOCK_FAIL);
        }
    }

    public static void main(String[] argss) {
        int a = 0;
        Object b = 1;
        System.out.println(a + "xx");
        System.out.println(b instanceof Integer);
        System.out.println(b instanceof Long);
        System.out.println("---" + b);
        int[] args = {1, 0};
        int arg = args[0];
        System.out.println(arg);
        System.out.println(args[1]);
//        System.out.println(args[2]);

    }
}
