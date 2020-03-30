package cn.zull.lpc.common.cache.interceptor;

import cn.zull.lpc.common.cache.manager.ICacheManager;
import cn.zull.lpc.common.cache.monitor.HitRateManger;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

/**
 * @author zurun
 * @date 2020/3/30 10:07:59
 */
@Slf4j
@Aspect
@Component
@ConditionalOnBean(ICacheManager.class)
class BaseCacheAspect {
    protected final ExpressionParser parser = new SpelExpressionParser();
    protected final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
    protected final ICacheManager<String> iCacheManager;
    protected final HitRateManger hitRateManger;

    protected BaseCacheAspect(ICacheManager<String> iCacheManager, HitRateManger hitRateManger) {
        this.iCacheManager = iCacheManager;
        this.hitRateManger = hitRateManger;
    }


    protected boolean checkCondition(String condition, EvaluationContext context) {
        if ("".equals(condition)) {
            return true;
        }
        Expression expression = parser.parseExpression(condition);
        Object conditionResult = expression.getValue(context);
        if (conditionResult instanceof Boolean) {
            return (Boolean) conditionResult;
        }
        log.warn("[表达式错误] condition={}", condition);
        return false;
    }

    protected Object proceed(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }
}
