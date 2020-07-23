package cn.zull.lpc.common.mybatis.plus.interceptor;

import cn.zull.lpc.common.monitor.MonitorDataHandler;
import cn.zull.lpc.common.monitor.report.data.adapter.MybatisMonitorCollectDataAdapter;
import cn.zull.lpc.common.monitor.report.manager.LpcMybatisMonitorManager;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * @author zurun
 * @date 2020/7/23 11:46:47
 */
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})})
public class LpcInterceptor implements Interceptor {
    private final MonitorDataHandler monitorDataHandler = MonitorDataHandler.getInstance();

    {
        System.out.println("------- mybatis plus monitor -----");
        monitorDataHandler.register(new LpcMybatisMonitorManager());
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MybatisMonitorCollectDataAdapter data = new MybatisMonitorCollectDataAdapter();
        Object proceed = data.workAspect(() -> {
            try {
                return invocation.proceed();
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        monitorDataHandler.report(data);
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
