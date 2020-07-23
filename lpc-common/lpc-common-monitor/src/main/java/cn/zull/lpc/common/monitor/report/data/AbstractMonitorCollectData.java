package cn.zull.lpc.common.monitor.report.data;


import cn.zull.lpc.common.basis.utils.function.NoArgsFunction;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * @author zurun
 * @date 2020/7/14 16:29:38
 */
@Slf4j
public abstract class AbstractMonitorCollectData implements MonitorCollectData {
    //    protected WarningLevel warningLevel = WarningLevel.NORMAL_LEVEL;
//    protected Status status;
    protected long startTime;
    protected int cost;

    public AbstractMonitorCollectData() {
        this.startTime = System.currentTimeMillis();
    }

    public Object workAspect(Supplier supplier) {
        Object o = supplier.get();
        this.complete();
        return o;
    }

    public void workAspect(NoArgsFunction noArgsFunction) {
        noArgsFunction.todo();
        this.complete();
    }

    private void complete() {
        long now = System.currentTimeMillis();
        int cost = (int) (now - startTime);
        this.cost = cost;
//        if (cost > 1_000L) {
//            this.warningLevel = WarningLevel.DANGER_LEVEL;
//        } else if (cost > 0_500L) {
//            this.warningLevel = WarningLevel.WARNING_LEVEL;
//        }
    }

    @Override
    public int getCost() {
        return cost;
    }
}
