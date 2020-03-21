package cn.zull.lpc.common.basis.model;

import lombok.Data;

/**
 * @author zurun
 * @date 2018/3/11 12:51:57
 */
@Data
public abstract class BaseResult {
    private int code = 0;
    private String traceId;
    private String message;
    private Object data;
}
