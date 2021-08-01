package cn.zull.lpc.learn.spring.service;

import cn.zull.lpc.learn.spring.bean.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jared.zu
 * @date 2021/8/1 21:36:25
 */
public class SpringXmlService {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext xmlContext = new ClassPathXmlApplicationContext("spring.xml");
        Student student = xmlContext.getBean(Student.class);
        student.setName("name_test");
        System.out.println(student.getName());
    }
}
