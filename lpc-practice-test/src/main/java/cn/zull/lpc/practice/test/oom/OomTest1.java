package cn.zull.lpc.practice.test.oom;

import cn.zull.lpc.common.basis.utils.UUIDUtils;
import cn.zull.lpc.practice.test.config.ITestSwitch;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jared.zu
 * @date 2020/8/22 22:42:40
 */
@Slf4j
@Component
public class OomTest1 implements ITestSwitch {
    final List<TestItem1> list;

    public OomTest1() {
        this.list = new ArrayList<>(1 << 20);
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            for (int j = 0; j < 50; j++) {
                TestItem1 item = new TestItem1();
                item.setId(i);
                i++;
                item.setVal(UUIDUtils.simpleUUID());
                list.add(item);
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String name() {
        return "oom1";
    }

    @Data
    public static class TestItem1 {
        private int id;
        private String val;
        private Map<String, TestItem2> map = new HashMap<>(8);

        public void setVal(String val) {
            this.val = val;
            TestItem2 item2 = new TestItem2();
            item2.setVal(val);
            map.put("key1", item2);
            map.put("key2", item2);
        }
    }

    @Data
    public static class TestItem2 {
        private String val;
    }
}
