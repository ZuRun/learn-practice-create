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
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author zurun
 * @date 2020/3/26 10:08:12
 */
@Slf4j
@Aspect
@Component
public class CacheAspect {
    private final ExpressionParser parser = new SpelExpressionParser();
    private final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
    private final ICacheManager<String> iCacheManager;
    private final HitRateManger hitRateManger;

    public CacheAspect(ICacheManager<String> iCacheManager, HitRateManger hitRateManger) {
        this.iCacheManager = iCacheManager;
        this.hitRateManger = hitRateManger;
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

        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (Cacheable.class.equals(annotation.annotationType())) {
                return cacheable(pjp, targetClass, methodName, method, method.getAnnotation(Cacheable.class));
            }
        }
        return pjp.proceed();
    }

    private Object cacheable(ProceedingJoinPoint pjp, Class targetClass, String methodName, Method method, Cacheable annotation) throws Throwable {
        Class<?> returnType = method.getReturnType();
        Object[] arguments = pjp.getArgs();
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], arguments[len]);
        }
        String spel = annotation.key();
        long ttl = annotation.ttl();

        if (StringUtils.isEmpty(spel)) {
            log.warn("[cache异常] 未配置key class={} method={}", targetClass.getName(), methodName);
            return pjp.proceed();
        }
        Expression expression = parser.parseExpression(spel);
        String key = expression.getValue(context).toString();
        String cacheName = annotation.cacheName() + ":" + key;

        if (log.isDebugEnabled()) {
            log.debug("[尝试获取缓存] class:{} method:{}", targetClass.getSimpleName(), methodName);
        }

        String type = targetClass.getName() + "_" + methodName;
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
        Object proceed = pjp.proceed();
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
