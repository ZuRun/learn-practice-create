package cn.zull.lpc.common.dubbo.threadpool;

import cn.zull.lpc.common.dubbo.threadpool.monitor.DubboThreadMonitorManager;
import cn.zull.lpc.common.dubbo.threadpool.monitor.DubboThreadPoolMonitorCollectData;
import cn.zull.lpc.common.monitor.MonitorDataHandler;
import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.threadpool.ThreadPool;
import com.alibaba.dubbo.common.threadpool.support.AbortPolicyWithReport;
import com.alibaba.dubbo.common.utils.NamedThreadFactory;

import java.util.concurrent.*;

/**
 * @author zurun
 * @date 2020/7/21 17:34:04
 */
public class LpcDubboThreadPool implements ThreadPool {
    @Override
    public Executor getExecutor(URL url) {
        String name = url.getParameter(Constants.THREAD_NAME_KEY, "lpc-dubbo-");
        int threads = url.getParameter(Constants.THREADS_KEY, Constants.DEFAULT_THREADS);
        int queues = url.getParameter(Constants.QUEUES_KEY, Constants.DEFAULT_QUEUES);
        System.out.println("-------dubbo-----thread-----pool-----");
        return new LpcThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<Runnable>() :
                        (queues < 0 ? new LinkedBlockingQueue<Runnable>()
                                : new LinkedBlockingQueue<Runnable>(queues)),
                new NamedThreadFactory("lpc-dubbo", true), new AbortPolicyWithReport(name, url));
    }

    public static class LpcThreadPoolExecutor extends ThreadPoolExecutor {
        /**
         * 上报每次线程执行情况
         */
        private final MonitorDataHandler monitorDataHandler = MonitorDataHandler.getInstance();

        public LpcThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
                                     long keepAliveTime, TimeUnit unit,
                                     BlockingQueue<Runnable> workQueue,
                                     ThreadFactory threadFactory,
                                     RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
            monitorDataHandler.register(new DubboThreadMonitorManager());
        }

        @Override
        public void execute(Runnable command) {
            DubboThreadPoolMonitorCollectData collectData = new DubboThreadPoolMonitorCollectData();
            super.execute(() -> {
                collectData.workAspect(command::run);
                collectData.setMaximumPoolSize(this.getMaximumPoolSize());
                collectData.setActiveCount(this.getActiveCount());
                collectData.setPoolSize(this.getPoolSize());
                collectData.setLargestPoolSize(this.getLargestPoolSize());
                monitorDataHandler.report(collectData);
            });
        }
    }
}
