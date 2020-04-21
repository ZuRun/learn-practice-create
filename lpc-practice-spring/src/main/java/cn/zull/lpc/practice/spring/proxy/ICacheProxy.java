package cn.zull.lpc.practice.spring.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * 动态代理
 * <p>
 * 需求:
 * 根据参数data1,决定代理的实现类
 *
 * @author zurun
 * @date 2020/4/20 11:33:20
 */
public class ICacheProxy implements MethodInterceptor {
    /**
     * 是否是数据源1
     */
    public static Supplier<Boolean> supplier = () -> true;
    private DataSource1Cache instance1 = new DataSource1Cache();
    private DataSource2Cache instance2 = new DataSource2Cache();

    private static ICacheProxy iCacheProxy = new ICacheProxy();
    private final ICache iCache;


    public ICacheProxy() {
        this.iCache = (ICache) createObj(new Temp());
    }

    public static ICache getICache() {
        return iCacheProxy.iCache;
    }

    public Object createObj(ICache target) {
        //加载需要创建子类的类
        Enhancer enhancer = new Enhancer();
        //设置代理目标
        enhancer.setSuperclass(target.getClass());
        //设置回调
        enhancer.setCallback(this);
        enhancer.setClassLoader(target.getClass().getClassLoader());
        //返回子类对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (supplier.get()) {
            Class clz = instance1.getClass();
            Method method1 = clz.getDeclaredMethod(method.getName(), method.getParameterTypes());
            return method1.invoke(instance1, args);
        } else {
            Class clz = instance2.getClass();
            Method method1 = clz.getDeclaredMethod(method.getName(), method.getParameterTypes());
            return method1.invoke(instance2, args);
        }
    }

    @Slf4j
    public static class Temp implements ICache {

        @Override
        public String get(String key) {
            log.warn("[old get] key:{}", key);
            return null;
        }

        @Override
        public void set(String key, String value) {
            log.warn("[old set] key:{} val:{}", key, value);

        }
    }
}
