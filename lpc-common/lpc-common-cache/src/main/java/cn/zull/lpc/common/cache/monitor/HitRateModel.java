package cn.zull.lpc.common.cache.monitor;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zurun
 * @date 2020/3/26 16:36:32
 */
public class HitRateModel {
    private AtomicLong sum = new AtomicLong(0);
    private AtomicLong hit = new AtomicLong(0);

    public void addHit() {
        if (hit.incrementAndGet() > 0x7f0fffff) {
            synchronized (this) {
                hit.set(0);
                sum.set(0);
            }
        }
    }

    public void addSum() {
        if (sum.incrementAndGet() > 0x7f0fffff) {
            synchronized (this) {
                hit.set(0);
                sum.set(0);
            }
        }
    }

    public long getSum() {
        return sum.get();
    }

    /**
     * 获取命中率
     *
     * @return
     */
    public long getHitRate() {
        long sumNum = sum.get();
        long hitNum = hit.get();
        if (sumNum == 0) {
            return 0L;
        }
        return hitNum * 100 / sumNum;
    }
}
