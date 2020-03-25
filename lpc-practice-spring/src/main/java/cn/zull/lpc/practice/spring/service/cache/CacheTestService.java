package cn.zull.lpc.practice.spring.service.cache;

import cn.zull.lpc.common.basis.utils.UUIDUtils;
import cn.zull.lpc.practice.spring.dto.CacheRespDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author zurun
 * @date 2020/3/25 14:26:49
 */
@Slf4j
@Service
public class CacheTestService {

    @Cacheable(value = "fn")
    public String fixedNull() {
        return null;
    }

    @Cacheable(value = "fr")
    public String fixedResult() {
        return "fixedResult";
    }

    @Cacheable
    public CacheRespDTO get(int age, String name) {
        CacheRespDTO dto = newDto(age, name);
        return dto;
    }


    private CacheRespDTO newDto(int age, String name) {
        CacheRespDTO dto = new CacheRespDTO()
                .setCode(-1)
                .setTraceId(UUIDUtils.simpleUUID())
                .setData(new CacheRespDTO.Data().setAge(age).setName(name));
        return dto;
    }
}
