package cn.zull.lpc.common.monitor.report.manager;


import cn.zull.lpc.common.monitor.report.data.MonitorCollectData;

/**
 * @author zurun
 * @date 2020/7/17 10:56:48
 */
public interface ThreadMonitorManager<T extends MonitorCollectData> extends MonitorManager<T> {
    long getSumCost();

    int getAvgCost();

    int getMaxCost();

    int getMinCost();

    int getPoolSize();

    int getLargestPoolSize();

    int getActiveCount();

    int getMaximumPoolSize();
}