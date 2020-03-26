package cn.zull.lpc.common.cache.monitor;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zurun
 * @date 2020/3/26 16:48:58
 */
@Component
public class HitRateManger {
    private final Map<String, HitRateModel> map = new ConcurrentHashMap<>(2 << 7);
    private final AtomicLong sum = new AtomicLong(0);
    private final AtomicLong hit = new AtomicLong(0);

    public void addHit(String type) {
        getHitRateModel(type).addHit();
        hit.incrementAndGet();
    }

    public void addSum(String type) {
        getHitRateModel(type).addSum();
        sum.incrementAndGet();
    }

    public long getSum(String type) {
        return getHitRateModel(type).getSum();
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

    /**
     * 获取命中率
     *
     * @return
     */
    public long getHitRate(String type) {
        return map.get(type).getHitRate();
    }

    private HitRateModel getHitRateModel(String type) {
        HitRateModel hitRateModel = map.get(type);
        if (hitRateModel == null) {
            synchronized (this) {
                hitRateModel = new HitRateModel();
                map.put(type, hitRateModel);
            }
        }
        return hitRateModel;
    }
}
