package cn.zull.lpc.practice.spring.cache.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zurun
 * @date 2020/3/25 14:27:41
 */
@Data
@Accessors(chain = true)
public class CacheRespDTO {
    private Integer code;
    private String traceId;

    private Data data;



    @lombok.Data
    @Accessors(chain = true)
    public static class Data {
        private String name;
        private Integer age;
    }
}
