package cn.zull.lpc.common.basis.utils.function;

/**
 * 回调函数,没有入参与出参
 *
 * @author zurun
 * @date 2018/7/28 22:01:10
 */
@FunctionalInterface
public interface NoArgsFunction {

    /**
     * 执行回调的业务代码
     */
    void todo();
}
