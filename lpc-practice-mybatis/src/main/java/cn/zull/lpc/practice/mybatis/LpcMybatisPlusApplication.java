package cn.zull.lpc.practice.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zurun
 * @date 2020/4/9 15:52:54
 */
@SpringBootApplication
@MapperScan({"cn.zull.lpc.practice.mybatis.mapper", "cn.zull.lpc.practice.mybatis.mapper.test"})
public class LpcMybatisPlusApplication {
    public static void main(String[] args) {
        SpringApplication.run(LpcMybatisPlusApplication.class, args);
    }
}
