package cn.zull.lpc.practice.log2kafka.log.appender;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import cn.zull.lpc.practice.log2kafka.model.LogModel;
import cn.zull.lpc.practice.log2kafka.log.Log2kafkaQueue;

/**
 * @author zurun
 * @date 2020/8/11 18:04:16
 */
public class MyAppender extends UnsynchronizedAppenderBase {
    @Override
    protected void append(Object eventObject) {
        LoggingEvent loggingEvent = (LoggingEvent) eventObject;
        LogModel logBean = new LogModel()
                .setTName(loggingEvent.getThreadName())
                .setTimestamp(String.valueOf(loggingEvent.getTimeStamp()))
                .setLevel(loggingEvent.getLevel().levelStr)
                .setClassName(loggingEvent.getLoggerName())
                .setTraceId(loggingEvent.getMDCPropertyMap().get("traceId"))
                .setMessage(loggingEvent.getFormattedMessage())
                ;
        Log2kafkaQueue.add(logBean);
//        System.out.println(logBean);
    }
}