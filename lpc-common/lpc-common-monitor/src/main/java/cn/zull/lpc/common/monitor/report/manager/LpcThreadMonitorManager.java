package cn.zull.lpc.common.monitor.report.manager;

import cn.zull.lpc.common.basis.utils.JsonUtils;
import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;
import cn.zull.lpc.common.monitor.model.MontiorCollectDataTypeEnum;
import cn.zull.lpc.common.monitor.report.data.ThreadMonitorCollectData;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zurun
 * @date 2020/7/17 11:14:09
 */
@Slf4j
public class LpcThreadMonitorManager extends AbstractMonitorManager<ThreadMonitorCollectData> implements ThreadMonitorManager<ThreadMonitorCollectData> {

    /**
     * 当前时刻线程
     *
     * @return
     */
    private int poolSize = 0;
    private int activeCount = 0;
    private int largestPoolSize = 0;
    private int maximumPoolSize = 0;


    @Override
    public int getPoolSize() {
        return poolSize;
    }

    @Override
    public int getLargestPoolSize() {
        return largestPoolSize;
    }

    @Override
    public int getActiveCount() {
        return activeCount;
    }

    @Override
    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    @Override
    public void add(ThreadMonitorCollectData monitorStatistics) {
        compare(monitorStatistics);
        this.poolSize = monitorStatistics.getPoolSize();
        this.activeCount = monitorStatistics.getActiveCount();
        this.largestPoolSize = monitorStatistics.getLargestPoolSize();
        this.maximumPoolSize = monitorStatistics.getMaximumPoolSize();
    }

    @Override
    public String out() {
        return JsonUtils.toJSONString(this);
    }

    @Override
    public MontiorCollectDataType monitorType() {
        return MontiorCollectDataTypeEnum.LPC_THREAD_POOL;
    }


    /**
     * 通知刷新监控时间段
     *
     * @param avg
     */
    @Override
    protected void notifyRefresh(int avg) {
        log.info("[线程池监控] monitorType:{} avg:{} {}", monitorType().getType(), avg, JsonUtils.toJSONString(this));
    }

}
