package cn.zull.lpc.practice.test.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jared.zu
 * @date 2020/8/22 22:30:47
 */
@Slf4j
@Configuration
public class TestSwitchConfig implements CommandLineRunner {

    @Value("${lpc.p.test.switch.item:}")
    String configItems;

    private Map<String, ITestSwitch> map = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        String[] split = configItems.split(",");

        Map<String, ITestSwitch> beansOfType = SpringApplicationContext.getApplicationContext().getBeansOfType(ITestSwitch.class);
        beansOfType.forEach((key, bean) -> {
            map.put(bean.name(), bean);
        });

        for (int i = 0; i < split.length; i++) {
            String item = split[i];

            ITestSwitch iTestSwitch = map.get(item);
            if (iTestSwitch == null) {
                log.warn("[未找到test类型] {}", item);
                continue;
            }
            log.info("[run] {}", iTestSwitch.name());
            new Thread((() -> iTestSwitch.run()), "t_" + item + "_thread").start();
        }
    }
}
