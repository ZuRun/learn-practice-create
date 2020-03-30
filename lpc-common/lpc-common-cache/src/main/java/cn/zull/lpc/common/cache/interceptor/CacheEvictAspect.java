package cn.zull.lpc.common.cache.interceptor;

import cn.zull.lpc.common.cache.annotation.CacheEvict;
import cn.zull.lpc.common.cache.manager.ICacheManager;
import cn.zull.lpc.common.cache.monitor.HitRateManger;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author zurun
 * @date 2020/3/30 11:16:36
 */
@Slf4j
public class CacheEvictAspect extends BaseCacheAspect {
    protected CacheEvictAspect(ICacheManager<String> iCacheManager, HitRateManger hitRateManger) {
        super(iCacheManager, hitRateManger);
    }

    @Pointcut(value = "@annotation(cn.zull.lpc.common.cache.annotation.CacheEvict)")
    public void cachePointcut() {
    }

    @Around(value = "cachePointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Class targetClass = pjp.getTarget().getClass();
        String methodName = pjp.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature) pjp.getSignature()).getMethod().getParameterTypes();
        Method method = targetClass.getMethod(methodName, parameterTypes);

        return cacheEvict(pjp, targetClass, methodName, method);
    }

    public Object cacheEvict(ProceedingJoinPoint pjp, Class targetClass, String methodName, Method method) throws Throwable {
        CacheEvict annotation = method.getAnnotation(CacheEvict.class);
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

        if (StringUtils.isEmpty(spel)) {
            log.warn("[cache异常] 未配置key class={} method={}", targetClass.getName(), methodName);
            return proceed(pjp);
        }
        Expression expression = parser.parseExpression(spel);
        // 拼接缓存key
        String key = expression.getValue(context).toString();
        String cacheNamePrefix = annotation.cacheName();
        String cacheName = cacheNamePrefix + ":" + key;

        // 删除缓存
        boolean beforeInvocation = annotation.beforeInvocation();
        if (beforeInvocation) {
            iCacheManager.delCache(cacheName);
            return proceed(pjp);
        } else {
            Object proceed = proceed(pjp);
            iCacheManager.delCache(cacheName);
            return proceed;
        }
    }
}
