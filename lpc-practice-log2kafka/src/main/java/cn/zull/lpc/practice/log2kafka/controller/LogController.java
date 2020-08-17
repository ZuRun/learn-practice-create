package cn.zull.lpc.practice.log2kafka.controller;

import cn.zull.lpc.common.basis.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2020/8/11 18:16:35
 */
@Slf4j
@RestController
@RequestMapping("log")
public class LogController {

    @Value("${lpc.biz.req.sleep:0}")
    private int sleepTime;

    @Value("${lpc.biz.req.cycles:20}")
    private int cycles;

    @RequestMapping("t1")
    public String t1() throws InterruptedException {
        String t = UUIDUtils.simpleUUID();
        String uuid = t + "_" + t + "_" + t;
        MDC.put("traceId", t);
        for (int i = 0; i < cycles; i++) {
            String msg = uuid + ":" + i;
            log.debug("[t1] {}", msg);
            log.info("[t2] {}", msg);
            log.warn("[t3] {}", msg);
            log.error("[t4] {}", msg);
        }
        if (sleepTime > 0) {
            Thread.sleep(sleepTime);
        }
        return "ok";
    }
}
