package cn.zull.lpc.practice.kafka2es.model;

import lombok.Data;

/**
 * @author zurun
 * @date 2020/8/11 18:55:48
 */
@Data
public class LogModel {
    private long timestamp;
    private String tName;
    private String level;
    private String className;
    private String traceId;
    private String message;
}
