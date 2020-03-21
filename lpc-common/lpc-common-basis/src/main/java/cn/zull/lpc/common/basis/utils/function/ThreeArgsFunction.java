package cn.zull.lpc.common.basis.utils.function;

/**
 * @author zurun
 * @date 2019/2/18 17:40:06
 */
@FunctionalInterface
public interface ThreeArgsFunction<T, U, V, R> {

    /**
     * 三个入参,一个出参
     *
     * @param t
     * @param u
     * @param v
     * @return
     */
    R apply(T t, U u, V v);

}
