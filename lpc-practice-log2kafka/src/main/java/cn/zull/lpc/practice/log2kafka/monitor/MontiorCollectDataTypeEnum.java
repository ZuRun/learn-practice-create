package cn.zull.lpc.practice.log2kafka.monitor;

import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;

/**
 * @author zurun
 * @date 2020/7/20 16:58:13
 */
public enum MontiorCollectDataTypeEnum implements MontiorCollectDataType {
    LOG
    ;

    @Override
    public String getType() {
        return name();
    }
}
