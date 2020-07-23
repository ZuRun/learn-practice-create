package cn.zull.lpc.common.monitor;

import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;
import cn.zull.lpc.common.monitor.report.data.MonitorCollectData;
import cn.zull.lpc.common.monitor.report.manager.MonitorManager;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zurun
 * @date 2020/7/14 17:39:47
 */
@Slf4j
public class MonitorDataHandler {
    private static final MonitorDataHandler monitorDataHandler = new MonitorDataHandler();
    private static final Map<MontiorCollectDataType, MonitorManager> map = new ConcurrentHashMap<>(16);

    private MonitorDataHandler() {

    }

    public static MonitorDataHandler getInstance() {
        return monitorDataHandler;
    }

    public boolean register(MonitorManager monitorManager) {
        return map.put(monitorManager.monitorType(), monitorManager) != null;
    }

    public <T extends MonitorManager> void report(MonitorCollectData monitorStatistics, MontiorCollectDataType montiorCollectDataType) {
        map.get(montiorCollectDataType).add(monitorStatistics);
    }

    public void report(MonitorCollectData monitorStatistics) {
        MonitorManager monitorManager = map.get(monitorStatistics.dataType());
        if (monitorManager == null) {
            log.warn("[未找到MonitorManager] dataType:{}", monitorStatistics.dataType());
            return;
        }
        monitorManager.add(monitorStatistics);
//        System.out.println(monitorManager.out());
    }

}
