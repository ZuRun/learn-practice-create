package cn.zull.lpc.practice.dubbo.provider;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zurun
 * @date 2020/7/22 15:00:56
 */
@SpringBootApplication
public class DubboProviderApplication {
    public static void main(String[] args){
        new SpringApplicationBuilder(DubboProviderApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
