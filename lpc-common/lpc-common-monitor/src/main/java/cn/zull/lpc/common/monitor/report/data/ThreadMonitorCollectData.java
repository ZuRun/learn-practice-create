package cn.zull.lpc.common.monitor.report.data;

/**
 * @author zurun
 * @date 2020/7/17 17:11:10
 */
public interface ThreadMonitorCollectData extends MonitorCollectData {

    int getPoolSize();

    int getActiveCount();

    /**
     * 被使用过的最大线程数
     *
     * @return
     */
    int getLargestPoolSize();

    /**
     * 允许最大线程数
     *
     * @return
     */
    int getMaximumPoolSize();
}
