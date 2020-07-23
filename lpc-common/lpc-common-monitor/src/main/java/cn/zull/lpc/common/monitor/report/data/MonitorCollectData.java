package cn.zull.lpc.common.monitor.report.data;

import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;
import cn.zull.lpc.common.monitor.model.MontiorCollectDataTypeEnum;

/**
 * @author zurun
 * @date 2020/7/14 16:14:10
 */
public interface MonitorCollectData {

    int getCost();

    default MontiorCollectDataType dataType() {
        return MontiorCollectDataTypeEnum.DEF_TP;
    }
}
