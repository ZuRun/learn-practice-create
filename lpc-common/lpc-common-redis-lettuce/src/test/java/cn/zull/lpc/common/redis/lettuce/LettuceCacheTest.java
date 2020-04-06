package cn.zull.lpc.common.redis.lettuce;

import cn.zull.lpc.common.redis.core.StringRedisCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zurun
 * @date 2020/4/3 11:13:35
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LettuceTestApplication.class)
public class LettuceCacheTest {

    @Autowired
    StringRedisCache redisCache;


    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 300; i++) {
            redisCache.get("test:" + i);
            System.out.println("---------------------------" + i);
            Thread.sleep(10L);
        }
    }
}