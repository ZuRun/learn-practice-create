package cn.zull.lpc.practice.log2kafka.controller;

import cn.zull.lpc.common.basis.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
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

    @RequestMapping("t1")
    public String t1() {
        String uuid = UUIDUtils.simpleUUID();
        log.debug("[t1] {}", uuid);
        log.info("[t1] {}", uuid);
        log.warn("[t1] {}", uuid);
        log.error("[t1] {}", uuid);
        return "ok";
    }
}
