package cn.zull.lpc.common.monitor.report.manager;

import cn.zull.lpc.common.basis.utils.JsonUtils;
import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;
import cn.zull.lpc.common.monitor.model.MontiorCollectDataTypeEnum;
import cn.zull.lpc.common.monitor.report.data.adapter.MybatisMonitorCollectDataAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zurun
 * @date 2020/7/23 11:53:25
 */
@Slf4j
public class LpcMybatisMonitorManager extends AbstractMonitorManager<MybatisMonitorCollectDataAdapter> {

    @Override
    public void add(MybatisMonitorCollectDataAdapter monitorStatistics) {
        compare(monitorStatistics);
    }

    @Override
    public String out() {
        return JsonUtils.toJSONString(this);
    }

    @Override
    public MontiorCollectDataType monitorType() {
        return MontiorCollectDataTypeEnum.LPC_MYBATIS;
    }

    @Override
    void notifyRefresh(int avg) {
        if (this.getMaxCost() > 100) {
            log.warn("[sql线程池监控9] avg:{} {}", avg, JsonUtils.toJSONString(this));
        } else if (avg > 20) {
            log.warn("[sql线程池监控5] avg:{} {}", avg, JsonUtils.toJSONString(this));
        } else {
            log.info("[sql线程池监控0] avg:{} {}", avg, JsonUtils.toJSONString(this));
        }
    }
}
