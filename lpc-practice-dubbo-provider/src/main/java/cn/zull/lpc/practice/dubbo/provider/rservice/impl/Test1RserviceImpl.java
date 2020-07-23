package cn.zull.lpc.practice.dubbo.provider.rservice.impl;

import cn.zull.lpc.practice.basic.api.Test1Rservice;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.Random;

/**
 * @author zurun
 * @date 2020/7/22 14:51:08
 */
@Service(version = "1.0")
public class Test1RserviceImpl implements Test1Rservice {
    Random random = new Random(500);

    @Override
    public void test() {
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-test-1-");
    }
}
