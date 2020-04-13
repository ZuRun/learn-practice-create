package cn.zull.lpc.practice.mybatis.service;

import cn.zull.lpc.practice.mybatis.LpcMybatisPlusApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @author zurun
 * @date 2020/4/9 16:05:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LpcMybatisPlusApplication.class)
class DeviceInfoServiceTest {

    @Autowired
    DeviceInfoService deviceInfoService;

    @Test
    public void getDeviceCount() throws InterruptedException {
        final int threadSize = 3;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);


        Runnable runnable = () -> {
            try {
                for (int i = 0; i < 20; i++) {
                    System.out.println(deviceInfoService.getDeviceCount());
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                countDownLatch.countDown();
            }

        };

        for (int i = 0; i < threadSize; i++) {
            new Thread(runnable).start();
        }
        countDownLatch.await();

    }
}