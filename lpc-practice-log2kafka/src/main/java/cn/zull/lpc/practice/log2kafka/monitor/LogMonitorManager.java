package cn.zull.lpc.practice.log2kafka.monitor;

import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;
import cn.zull.lpc.common.monitor.report.manager.MonitorManager;

/**
 * @author zurun
 * @date 2020/8/17 10:24:01
 */
public class LogMonitorManager implements MonitorManager<LogMonitorCollectData> {
    @Override
    public void add(LogMonitorCollectData monitorStatistics) {

    }

    @Override
    public String out() {
        return null;
    }

    @Override
    public MontiorCollectDataType monitorType() {
        return MontiorCollectDataTypeEnum.LOG;
    }
}
