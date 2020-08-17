package cn.zull.lpc.practice.log2kafka.monitor;

import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;
import cn.zull.lpc.common.monitor.report.data.MonitorCollectData;

/**
 * @author zurun
 * @date 2020/8/17 10:17:32
 */
public class LogMonitorCollectData implements MonitorCollectData {
    private int cost;

    public LogMonitorCollectData(int cost) {
        this.cost = cost;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public MontiorCollectDataType dataType() {
        return null;
    }
}
