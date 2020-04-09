package cn.zull.lpc.common.redis.jedis;

import cn.zull.lpc.common.redis.core.IRedisCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zurun
 * @date 2020/4/9 10:53:43
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JedisTestApplication.class)
public class RedisTemplateTest {

    @Autowired
    IRedisCache<String, String, String> iRedisCache;

    @Test
    public void test() {
        Map<String, String> map = new HashMap<>(16);
        map.put("a", "aa");
        map.put("b", null);
        map.put("c", "empty");
        map.put("d", "");
        try {
            iRedisCache.hSet("test:s", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("——————分割线");

        System.out.println(iRedisCache.hGetAll("test:s"));

    }
}
