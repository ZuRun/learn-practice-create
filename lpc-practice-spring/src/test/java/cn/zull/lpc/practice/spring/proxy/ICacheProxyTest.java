package cn.zull.lpc.practice.spring.proxy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author zurun
 * @date 2020/4/20 11:34:12
 */
@Slf4j
@RunWith(SpringRunner.class)
public class ICacheProxyTest {

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            ICache iCache = ICacheProxy.getICache();
            String a1 = iCache.get("a1");
            log.info("a1 get : {}", a1);
            iCache.set("b1", "c1");
            ICacheProxy.data1 = false;
            System.out.println("-----------");
            String a2 = iCache.get("a2");
            log.info("a2 get : {}", a2);
            iCache.set("b2", "c2");
            System.out.println("-----------===================");
        }


    }
}