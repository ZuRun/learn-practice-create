package cn.zull.lpc.common.dubbo.threadpool.monitor;

import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;
import cn.zull.lpc.common.monitor.report.data.adapter.ThreadPoolMonitorCollectDataAdapter;

/**
 * @author zurun
 * @date 2020/7/22 11:10:39
 */
public class DubboThreadPoolMonitorCollectData extends ThreadPoolMonitorCollectDataAdapter {

    @Override
    public MontiorCollectDataType dataType() {
        return DubboMonitorDataType.DUBBO_TP;
    }
}
