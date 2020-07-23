package cn.zull.lpc.common.dubbo.threadpool.monitor;

import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;

/**
 * @author zurun
 * @date 2020/7/22 16:20:32
 */
public enum DubboMonitorDataType implements MontiorCollectDataType {
    DUBBO_TP;


    @Override
    public String getType() {
        return name();
    }
}
