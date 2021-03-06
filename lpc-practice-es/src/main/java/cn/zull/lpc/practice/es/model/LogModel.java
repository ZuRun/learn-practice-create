package cn.zull.lpc.practice.es.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zurun
 * @date 2020/8/11 18:55:48
 */
@Data
@Accessors(chain = true)
public class LogModel {
    private String timestamp;
    private String tName;
    private String level;
    private String className;
    private String traceId;
    private String message;
}
