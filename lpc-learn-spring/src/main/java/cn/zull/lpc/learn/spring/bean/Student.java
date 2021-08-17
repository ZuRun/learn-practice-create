package cn.zull.lpc.learn.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jared.zu
 * @date 2021/8/1 21:34:43
 */
@Data
public class Student {
//    @Autowired
    Teacher teacher;

    private Long id;
    private String name;

    public void print() {
        System.out.println("student:" + name);
        teacher.print();
    }
}
