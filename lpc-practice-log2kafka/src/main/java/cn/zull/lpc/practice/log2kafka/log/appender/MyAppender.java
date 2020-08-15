package cn.zull.lpc.practice.log2kafka.log.appender;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import cn.zull.lpc.practice.log2kafka.bean.LogBean;
import cn.zull.lpc.practice.log2kafka.log.Log2kafkaQueue;

/**
 * @author zurun
 * @date 2020/8/11 18:04:16
 */
public class MyAppender extends UnsynchronizedAppenderBase {
    @Override
    protected void append(Object eventObject) {
        LoggingEvent loggingEvent = (LoggingEvent) eventObject;
        LogBean logBean = new LogBean()
                .setTName(loggingEvent.getThreadName())
                .setTimestamp(loggingEvent.getTimeStamp())
                .setLevel(loggingEvent.getLevel().levelStr)
                .setClassName(loggingEvent.getLoggerName())
                .setTraceId(loggingEvent.getMDCPropertyMap().get("traceId"))
                .setMessage(loggingEvent.getFormattedMessage())
                ;
        Log2kafkaQueue.add(logBean);
        System.out.println(logBean);
    }
}
