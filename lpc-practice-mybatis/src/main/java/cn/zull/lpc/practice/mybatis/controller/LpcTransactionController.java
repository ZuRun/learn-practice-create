package cn.zull.lpc.practice.mybatis.controller;

import cn.zull.lpc.practice.mybatis.service.LpcTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jared.Zu
 * @date 2021/6/9 16:06:14
 */
@RestController
public class LpcTransactionController {

    @Autowired
    LpcTransactionService lpcTransactionService;

    @RequestMapping("test")
    public void test() {
        lpcTransactionService.test();
    }
}
