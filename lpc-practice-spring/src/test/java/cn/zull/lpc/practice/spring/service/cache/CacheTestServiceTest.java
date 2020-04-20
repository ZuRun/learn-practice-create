package cn.zull.lpc.practice.spring.service.cache;

import cn.zull.lpc.practice.spring.SpringTestApplication;
import cn.zull.lpc.practice.spring.cache.dto.CacheRespDTO;
import cn.zull.lpc.practice.spring.cache.service.CacheTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zurun
 * @date 2020/3/25 14:47:42
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringTestApplication.class)
public class CacheTestServiceTest {

    @Autowired
    CacheTestService cacheTestService;

    @Test
    public void fixedCache() {
        log.info("[fixedNull] {}", cacheTestService.fixedNull());
        log.info("[fixedNull] {}", cacheTestService.fixedNull());
        log.info("--------------");
        log.info("[fixedResult] {}", cacheTestService.fixedResult());
        log.info("[fixedResult] {}", cacheTestService.fixedResult());

    }

    @Test
    public void get() {
        int age = 5;
        String name = "张三";
        CacheRespDTO cacheRespDTO = cacheTestService.get(age, name);
        CacheRespDTO cacheRespDTO2 = cacheTestService.get(age, name);
        System.out.println(cacheRespDTO);
        System.out.println(cacheRespDTO2);

    }
}