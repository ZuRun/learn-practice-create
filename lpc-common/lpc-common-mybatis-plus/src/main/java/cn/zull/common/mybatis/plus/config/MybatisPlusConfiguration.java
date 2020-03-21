package cn.zull.common.mybatis.plus.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2019/7/31 21:11:27
 */
@Configuration
@ComponentScan("cn.zull.common.mybatis.plus")
public class MybatisPlusConfiguration {

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

//    @Bean
//    public MetaObjectHandler metaObjectHandler() {
//        return new MyMetaObjectHandler();
//    }
}
