package cn.zull.lpc.common.monitor.report.data.adapter;

import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;
import cn.zull.lpc.common.monitor.model.MontiorCollectDataTypeEnum;
import cn.zull.lpc.common.monitor.report.data.AbstractMonitorCollectData;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zurun
 * @date 2020/7/23 11:51:06
 */
@Slf4j
public class MybatisMonitorCollectDataAdapter extends AbstractMonitorCollectData {
    @Override
    public MontiorCollectDataType dataType() {
        return MontiorCollectDataTypeEnum.LPC_MYBATIS;
    }
}
