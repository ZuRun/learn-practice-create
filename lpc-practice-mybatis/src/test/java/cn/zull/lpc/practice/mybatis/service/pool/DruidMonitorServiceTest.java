package cn.zull.lpc.practice.mybatis.service.pool;

import cn.zull.lpc.practice.mybatis.LpcMybatisPlusApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zurun
 * @date 2020/8/4 18:15:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LpcMybatisPlusApplication.class)
public class DruidMonitorServiceTest {

    @Autowired
    DruidMonitorService druidMonitorService;

    @Test
    public void getList() {
        druidMonitorService.getList();
    }
}