package cn.zull.lpc.practice.spring.proxy;

import cn.zull.lpc.common.basis.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zurun
 * @date 2020/4/20 11:30:48
 */
@Slf4j
public class DataSource2Cache implements ICache {
    @Override
    public String get(String key) {
        log.info("[data-2] get key:{}", key);
        String val = key + UUIDUtils.simpleUUID();
        log.info("[data-2] get:{} val:{}", key, val);
        return val;
    }

    @Override
    public void set(String key, String value) {
        log.info("[data-2] set key:{} val:{}", key, value);
    }
}
