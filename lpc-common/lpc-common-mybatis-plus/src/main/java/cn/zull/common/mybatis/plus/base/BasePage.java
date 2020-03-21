package cn.zull.common.mybatis.plus.base;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @param <P> 传参
 * @param <T> 分页返回的实体
 * @author zurun
 * @date 2018/6/19 11:45:22
 */
public class BasePage<P, T> extends Page<T> {
    private P params;

    public P getParams() {
        return params;
    }

    public void setParams(P params) {
        this.params = params;
    }
}
