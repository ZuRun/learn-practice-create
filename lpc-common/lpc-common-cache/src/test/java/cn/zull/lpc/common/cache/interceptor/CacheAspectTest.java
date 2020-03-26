package cn.zull.lpc.common.cache.interceptor;

import cn.zull.lpc.common.basis.utils.UUIDUtils;
import cn.zull.lpc.common.cache.interceptor.dto.ReqDto;
import cn.zull.lpc.common.cache.interceptor.dto.RespDto;
import cn.zull.lpc.common.cache.monitor.HitRateManger;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zurun
 * @date 2020/3/26 10:34:15
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LpcCacheTestApplication.class)
public class CacheAspectTest {
    @Autowired
    AspectService aspectService;
    @Autowired
    HitRateManger hitRateManger;

    @Test
    public void t() {
        String traceId = UUIDUtils.simpleUUID();

        for (int i = 0; i < 100; i++) {
            ReqDto reqDto = new ReqDto().setCode(0)
                    .setTraceId(traceId)
                    .setData(new ReqDto.Data().setDCode(1).setReqData("test"));

            for (int j = 0; j < 10; j++) {
                reqDto.getData().setAccount(i + ":" + j);
                RespDto respDto = aspectService.test(reqDto, "shit");
                Assert.assertTrue(traceId.equals(respDto.getTraceId()));
            }
        }
        log.info("[hitRate] {}", hitRateManger.getHitRate());
        for (int i = 0; i < 100; i++) {
            ReqDto reqDto = new ReqDto().setCode(0)
                    .setTraceId(traceId)
                    .setData(new ReqDto.Data().setDCode(1).setReqData("test"));

            for (int j = 0; j < 10; j++) {
                reqDto.getData().setAccount(i + ":" + j);
                RespDto respDto = aspectService.test2(reqDto, "shit");
                Assert.assertTrue(traceId.equals(respDto.getTraceId()));
            }
        }

        log.info("[hitRate] {}", hitRateManger.getHitRate());

    }
}