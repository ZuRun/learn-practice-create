package cn.zull.lpc.common.monitor.report.manager;

import cn.zull.lpc.common.monitor.report.data.MonitorCollectData;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zurun
 * @date 2020/7/23 11:55:40
 */
public abstract class AbstractMonitorManager<T extends MonitorCollectData> implements MonitorManager<T> {
    private AtomicInteger maxCost = new AtomicInteger(0);
    private AtomicInteger minCost = new AtomicInteger(Integer.MAX_VALUE);
    private AtomicLong sumCost = new AtomicLong(0);
    private AtomicInteger num = new AtomicInteger(0);
    /**
     * 时间戳标记,每秒变更一次
     */
    private volatile transient long timeMark = System.currentTimeMillis() + 1000L;
    private volatile transient int lastAvg = 0;


    protected void compare(MonitorCollectData monitorStatistics) {
        int cost = monitorStatistics.getCost();
        num.getAndIncrement();
        sumCost.addAndGet(cost);

        compareMax(monitorStatistics);
        compareMin(monitorStatistics);

        // 重置
        tryReset();

    }

    /**
     * 此处不保证原子性,reset的同时其他线程可能在操作max,min的值
     */
    private synchronized void tryReset() {
        long now = System.currentTimeMillis();
        if (now > timeMark) {
            synchronized (this) {
                if (now > timeMark) {
                    int avg = (int) (this.sumCost.get() / num.get());

                    notifyRefresh(avg);
                    lastAvg = avg;
                    sumCost.set(0);
                    num.set(0);
                    minCost.set(avg);
                    maxCost.set(avg);
                    timeMark = now + 1000;
                }
            }
        }
    }

    abstract void notifyRefresh(int avg);

    /**
     * 比较最大值
     *
     * @param monitorStatistics
     * @return
     */
    private boolean compareMax(MonitorCollectData monitorStatistics) {
        int cost = monitorStatistics.getCost();
        int max = maxCost.get();
        if (cost > max) {
            while (true) {
                max = maxCost.get();
                if (cost > max) {
                    boolean b = maxCost.compareAndSet(max, cost);
                    if (b) {
                        return true;
                    }
                } else {
                    // 比最大值小,则直接退出
                    break;
                }
            }
        }
        return false;
    }

    /**
     * 比较最小值
     *
     * @param monitorStatistics
     * @return
     */
    private boolean compareMin(MonitorCollectData monitorStatistics) {
        int cost = monitorStatistics.getCost();
        int min = minCost.get();
        if (cost < min) {
            while (true) {
                min = minCost.get();
                if (cost < min) {
                    boolean b = minCost.compareAndSet(min, cost);
                    if (b) {
                        return true;
                    }
                } else {
                    // 比最小值大,则直接退出
                    break;
                }
            }
        }
        return false;
    }

    public int getNum() {
        return num.get();
    }

    public long getSumCost() {
        return this.sumCost.get();
    }

    public int getAvgCost() {
        int num = this.num.get();
        // 次数过少数据不准
        if (num < 5 && lastAvg > 0) {
            return lastAvg;
        }
        return (int) (this.sumCost.get() / num);
    }

    public int getMaxCost() {
        return this.maxCost.get();
    }

    public int getMinCost() {
        return this.minCost.get();
    }

}
