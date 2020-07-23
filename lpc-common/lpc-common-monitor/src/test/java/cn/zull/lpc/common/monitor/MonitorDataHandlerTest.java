package cn.zull.lpc.common.monitor;

import cn.zull.lpc.common.monitor.model.MontiorCollectDataType;
import cn.zull.lpc.common.monitor.model.MontiorCollectDataTypeEnum;
import cn.zull.lpc.common.monitor.report.data.adapter.ThreadPoolMonitorCollectDataAdapter;
import cn.zull.lpc.common.monitor.report.manager.LpcThreadMonitorManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**
 * @author zurun
 * @date 2020/7/22 10:27:54
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitorTestApplication.class)
public class MonitorDataHandlerTest {

    @Autowired
    private MonitorDataHandler monitorDataHandler;

    @Test
    public void t() throws InterruptedException {
        monitorDataHandler.register(new LpcThreadMonitorManager());
        Random random = new Random(100);
        for (int i = 0; i < 6_200; i++) {
            TestMonitorStatistics m = new TestMonitorStatistics();
            m.workAspect(() -> {
                try {
                    Thread.sleep(random.nextInt(500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            monitorDataHandler.report(m);
        }
    }

    public static class TestMonitorStatistics extends ThreadPoolMonitorCollectDataAdapter {

        @Override
        public MontiorCollectDataType dataType() {
            return MontiorCollectDataTypeEnum.LPC_THREAD_POOL;
        }
    }
}