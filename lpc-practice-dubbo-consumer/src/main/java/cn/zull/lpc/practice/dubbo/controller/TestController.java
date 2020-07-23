package cn.zull.lpc.practice.dubbo.controller;

import cn.zull.lpc.common.dubbo.constants.Version;
import cn.zull.lpc.practice.basic.api.Test1Rservice;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2020/7/22 15:02:50
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Reference(version = Version.v1, url = "dubbo://localhost:19351")
    private Test1Rservice test1Rservice;

    @RequestMapping("t1")
    public void t1() {
        new Thread(test1Rservice::test).start();
    }
}
