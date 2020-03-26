package cn.zull.lpc.common.cache.interceptor;

import cn.zull.lpc.common.cache.annotation.Cacheable;
import cn.zull.lpc.common.cache.interceptor.dto.ReqDto;
import cn.zull.lpc.common.cache.interceptor.dto.RespDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zurun
 * @date 2020/3/26 10:46:20
 */
@Slf4j
@Service
public class AspectService {

    @Cacheable(cacheName = "uc", key = "#reqDto.traceId + ':' + #reqDto.data.account")
    public RespDto test2(ReqDto reqDto, String source) {
        return methodInnerCache(reqDto, source);
    }

    @Cacheable(cacheName = "u", key = "#reqDto.traceId + ':' + #reqDto.data.account")
    public RespDto test(ReqDto reqDto, String source) {
        return methodInnerCache(reqDto, source);
    }

    //    @Cacheable(cacheName = "u", key = "#reqDto.traceId + ':' + #reqDto.data")
    public RespDto methodInnerCache(ReqDto reqDto, String source) {
        log.info("[查库操作...]");

        RespDto respDto = new RespDto()
                .setCode(1)
                .setTraceId(reqDto.getTraceId());
        return respDto;
    }
}
