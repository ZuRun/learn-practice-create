package cn.zull.lpc.common.dubbo.threadpool.monitor;

import cn.zull.lpc.common.basis.utils.JsonUtils;
import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;
import cn.zull.lpc.common.monitor.report.manager.LpcThreadMonitorManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zurun
 * @date 2020/7/22 16:16:32
 */
@Slf4j
public class DubboThreadMonitorManager extends LpcThreadMonitorManager {
    @Override
    public MontiorCollectDataType monitorType() {
        return DubboMonitorDataType.DUBBO_TP;
    }

    @Override
    protected void notifyRefresh(int avg) {
        if (this.getActiveCount() > 100 && (this.getMaximumPoolSize() - this.getActiveCount() < 50)) {
            log.warn("[dubbo线程告警] active:{} maximum:{}", this.getActiveCount(), this.getMaximumPoolSize());
        }
        if (this.getMaxCost() > 1000L) {
            log.warn("[dubbo线程池监控9] avg:{} {}", avg, JsonUtils.toJSONString(this));
        } else if (avg > 500) {
            log.warn("[dubbo线程池监控5] avg:{} {}", avg, JsonUtils.toJSONString(this));
        } else {
            log.info("[dubbo线程池监控0] avg:{} {}", avg, JsonUtils.toJSONString(this));
        }
    }
}
