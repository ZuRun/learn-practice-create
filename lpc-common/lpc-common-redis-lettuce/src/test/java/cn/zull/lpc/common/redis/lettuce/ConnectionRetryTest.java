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
 * @date 2020/4/21 17:09:02
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LettuceTestApplication.class)
public class ConnectionRetryTest {

    @Autowired
    StringRedisCache redisCache;


    @Test
    public void test() throws InterruptedException {
        int max = Integer.MAX_VALUE;
        for (int i = 0; i < max; i++) {
            String value = redisCache.get("iot:test:b38c5121c0924fc7bf4659ac9857d66f");
            log.info("[] value={}", value);
            Thread.sleep(1000L);
        }
    }
}
