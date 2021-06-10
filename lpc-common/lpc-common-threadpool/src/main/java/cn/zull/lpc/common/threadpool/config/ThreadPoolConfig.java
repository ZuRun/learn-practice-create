package cn.zull.lpc.common.threadpool.config;

import cn.zull.lpc.common.threadpool.ParallelExecutor;
import cn.zull.lpc.common.threadpool.TransactionParallelExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Jared.Zu
 * @date 2021/6/10 17:06:38
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ParallelExecutor parallelExecutor() {
        return new TransactionParallelExecutor(10, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100));
    }
}
