package cn.zull.lpc.common.monitor.report.manager;

import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;
import cn.zull.lpc.common.monitor.report.data.MonitorCollectData;

/**
 * @author zurun
 * @date 2020/7/17 14:21:56
 */
public interface MonitorManager<T extends MonitorCollectData> {
    void add(T monitorStatistics);

    String out();

    MontiorCollectDataType monitorType();
}
