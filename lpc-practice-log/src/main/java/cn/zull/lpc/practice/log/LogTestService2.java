package cn.zull.lpc.practice.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ZuRun
 * @date 2020/9/10 18:09:39
 */
@Slf4j
@Service
public class LogTestService2 {
    public void r(){
        System.out.println("----------------LogTestService2------------");
        log.debug("debug");
        log.info("info");
        log.error("error");
        System.out.println(log.isDebugEnabled());
        System.out.println(log.isInfoEnabled());
        System.out.println("----");
    }
}
