package cn.zull.lpc.practice.spring.proxy.config;

/**
 * @author zurun
 * @date 2020/4/20 17:28:41
 */
public class ParamConfig {
    private Boolean dataSource1 = true;


    public Boolean getDataSource1() {
        return dataSource1;
    }

    public ParamConfig setDataSource1(Boolean dataSource1) {
        this.dataSource1 = dataSource1;
        return this;
    }
}
