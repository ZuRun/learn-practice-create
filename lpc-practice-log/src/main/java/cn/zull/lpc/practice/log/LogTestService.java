package cn.zull.lpc.practice.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * @author ZuRun
 * @date 2020/9/10 16:13:37
 */
@Slf4j
@Service
public class LogTestService implements CommandLineRunner {
    @Autowired
    LogTestService2 logTestService2;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("----");

        log.debug("debug");
        log.info("info");
        log.error("error");
        System.out.println(log.isDebugEnabled());
        System.out.println(log.isInfoEnabled());
        System.out.println("----");

        logTestService2.r();
    }
}
