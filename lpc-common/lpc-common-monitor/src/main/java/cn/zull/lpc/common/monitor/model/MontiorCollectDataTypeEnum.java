package cn.zull.lpc.common.monitor.model;

/**
 * @author zurun
 * @date 2020/7/20 16:58:13
 */
public enum MontiorCollectDataTypeEnum implements MontiorCollectDataType {
    DEF_TP,
    LPC_THREAD_POOL,
    LPC_MYBATIS,
    ;

    @Override
    public String getType() {
        return name();
    }
}
