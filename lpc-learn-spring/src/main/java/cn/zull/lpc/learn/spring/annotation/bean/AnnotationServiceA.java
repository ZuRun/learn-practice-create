package cn.zull.lpc.learn.spring.annotation.bean;

import org.springframework.stereotype.Component;

/**
 * @author jared.zu
 * @date 2021/8/19 00:06:46
 */
@Component
public class AnnotationServiceA {
    public void print() {
        System.out.println("AnnotationServiceA------");
    }
}
