package cn.zull.lpc.common.cache.interceptor;

import cn.zull.lpc.common.cache.annotation.Cacheable;
import cn.zull.lpc.common.cache.manager.ICacheManager;
import cn.zull.lpc.common.cache.monitor.HitRateManger;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author zurun
 * @date 2020/3/26 10:08:12
 */
@Slf4j
@Aspect
@Component
@ConditionalOnClass(ICacheManager.class)
public class CacheAbleAspect extends BaseCacheAspect {

    public CacheAbleAspect(ICacheManager<String> iCacheManager, HitRateManger hitRateManger) {
        super(iCacheManager, hitRateManger);
    }

    @Pointcut(value = "@annotation(cn.zull.lpc.common.cache.annotation.Cacheable)")
    public void cachePointcut() {
    }

    @Around(value = "cachePointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        //切点所在的类
        Class targetClass = pjp.getTarget().getClass();
        //使用了注解的方法
        String methodName = pjp.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature) pjp.getSignature()).getMethod().getParameterTypes();
        Method method = targetClass.getMethod(methodName, parameterTypes);

        Cacheable cacheable = method.getAnnotation(Cacheable.class);
        return cacheable(pjp, targetClass, methodName, method, cacheable);
//
//        Annotation[] annotations = method.getAnnotations();
//        for (Annotation annotation : annotations) {
//            if (Cacheable.class.equals(annotation.annotationType())) {
//                return cacheable(pjp, targetClass, methodName, method, method.getAnnotation(Cacheable.class));
//            }
//        }
//        return pjp.proceed();
    }

    private Object cacheable(ProceedingJoinPoint pjp, Class targetClass, String methodName, Method method, Cacheable annotation) throws Throwable {
        Class<?> returnType = method.getReturnType();
        Object[] arguments = pjp.getArgs();

        // 解析spEl表达式
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], arguments[len]);
        }
        // key表达式
        String spel = annotation.key();
        // 执行cache的条件
        String condition = annotation.condition();
        // 检查条件,不符合条件直接返回
        if (!checkCondition(condition, context)) {
            return proceed(pjp);
        }

        long ttl = annotation.ttl();

        if (StringUtils.isEmpty(spel)) {
            log.warn("[cache异常] 未配置key class={} method={}", targetClass.getName(), methodName);
            return proceed(pjp);
        }
        Expression expression = parser.parseExpression(spel);
        // 拼接缓存key
        String key = expression.getValue(context).toString();
        String cacheNamePrefix = annotation.cacheName();
        String cacheName = cacheNamePrefix + ":" + key;

        if (log.isDebugEnabled()) {
            log.debug("[尝试获取缓存] class:{} method:{}", targetClass.getSimpleName(), methodName);
        }
        // 缓存类型,用于计算缓存命中率
        String type = cacheNamePrefix;
        // 计算总次数
        hitRateManger.addSum(type);
        Object cache = iCacheManager.getCache(cacheName, returnType);
        if (null != cache) {
            // 增加命中次数
            hitRateManger.addHit(type);
            if (log.isDebugEnabled()) {
                log.debug("[获取到cache] key={} value:{}", cacheName, cache);
            }
            return cache;
        }
        log.info("[未获取到cache] key={} hitRate={} allHitRate={} sum={}",
                cacheName, hitRateManger.getHitRate(type), hitRateManger.getHitRate(), hitRateManger.getSum(type));
        Object proceed = proceed(pjp);
        if (proceed == null) {
            return null;
        }
        if (ttl == -1) {
            iCacheManager.setCache(cacheName, proceed);
        } else {
            iCacheManager.setCache(cacheName, proceed, ttl, annotation.timeUnit());
        }
        if (log.isDebugEnabled()) {
            log.debug("[写cache] key={} value={}", cacheName, proceed);
        }
        return proceed;
    }


}
