package cn.zull.lpc.common.monitor.report.data.adapter;

import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;
import cn.zull.lpc.common.monitor.model.MontiorCollectDataTypeEnum;
import cn.zull.lpc.common.monitor.report.data.AbstractMonitorCollectData;
import cn.zull.lpc.common.monitor.report.data.ThreadMonitorCollectData;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zurun
 * @date 2020/7/22 11:01:12
 */
@Slf4j
public class ThreadPoolMonitorCollectDataAdapter extends AbstractMonitorCollectData implements ThreadMonitorCollectData {
    /**
     * 当前pool中申请的线程数
     */
    private int poolSize;
    /**
     * 正在使用线程数
     */
    private int activeCount;
    /**
     * 线程最多被使用了多少个
     */
    private int largestPoolSize;
    /**
     * 允许最大线程数
     */
    private int maximumPoolSize;

    public ThreadPoolMonitorCollectDataAdapter() {
        super();
    }

    @Override
    public int getPoolSize() {
        return poolSize;
    }

    @Override
    public int getActiveCount() {
        return activeCount;
    }

    @Override
    public int getLargestPoolSize() {
        return largestPoolSize;
    }

    @Override
    public int getMaximumPoolSize() {
        return this.maximumPoolSize;
    }

    public ThreadPoolMonitorCollectDataAdapter setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
        return this;
    }

    public ThreadPoolMonitorCollectDataAdapter setLargestPoolSize(int largestPoolSize) {
        this.largestPoolSize = largestPoolSize;
        return this;
    }

    public ThreadPoolMonitorCollectDataAdapter setPoolSize(int poolSize) {
        this.poolSize = poolSize;
        return this;
    }

    public ThreadPoolMonitorCollectDataAdapter setActiveCount(int activeCount) {
        this.activeCount = activeCount;
        return this;
    }

    @Override
    public MontiorCollectDataType dataType() {
        return MontiorCollectDataTypeEnum.LPC_THREAD_POOL;
    }
}
