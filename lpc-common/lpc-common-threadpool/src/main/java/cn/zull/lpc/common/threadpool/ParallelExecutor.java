package cn.zull.lpc.common.threadpool;

import java.util.List;

/**
 * @author Jared.Zu
 * @date 2021/6/10 18:00:22
 */
public interface ParallelExecutor {
    Future batchExecute(List<Runnable> runnableList);

}
