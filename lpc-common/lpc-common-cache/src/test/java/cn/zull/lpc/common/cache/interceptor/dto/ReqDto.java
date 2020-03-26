package cn.zull.lpc.common.cache.interceptor.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zurun
 * @date 2020/3/26 10:47:12
 */
@Data
@Accessors(chain = true)
public class ReqDto {
    private Integer code;
    private String traceId;
    private Data data;

    @lombok.Data
    public static class Data {
        private String account;
        private Integer dCode;
        private String reqData;
    }
}
