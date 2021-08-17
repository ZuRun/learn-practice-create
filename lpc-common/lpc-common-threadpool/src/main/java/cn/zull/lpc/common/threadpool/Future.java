package cn.zull.lpc.common.threadpool;

import java.util.concurrent.TimeUnit;

/**
 * @author Jared.Zu
 * @date 2021/6/10 17:47:11
 */
public interface Future {

    void get() throws InterruptedException;

    @Deprecated
    boolean get(long timeout, TimeUnit unit) throws InterruptedException;

}
