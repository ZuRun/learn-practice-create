package cn.zull.lpc.learn.spring.bean;

import lombok.Data;

/**
 * @author Jared.Zu
 * @date 2021/8/17 11:03:36
 */
@Data
public class Teacher {
    private String teacherName;

    public void print() {
        System.out.println("my name is " + teacherName);
    }

    public Teacher() {
        this.teacherName = "_teacherName";
    }
}
