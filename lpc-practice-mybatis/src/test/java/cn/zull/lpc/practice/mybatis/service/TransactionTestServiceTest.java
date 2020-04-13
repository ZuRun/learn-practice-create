package cn.zull.lpc.practice.mybatis.service;

import cn.zull.lpc.practice.mybatis.LpcMybatisPlusApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author zurun
 * @date 2020/4/13 16:24:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LpcMybatisPlusApplication.class)
public class TransactionTestServiceTest {

    @Autowired
    TransactionTestService transactionTestService;

    @Test
    public void test1() {
        transactionTestService.test1("");
    }
}