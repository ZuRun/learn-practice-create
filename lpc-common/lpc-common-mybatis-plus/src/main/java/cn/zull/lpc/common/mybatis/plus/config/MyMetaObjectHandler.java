package cn.zull.lpc.common.mybatis.plus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatis-plus自定义填充公共字段 ,即没有传的字段自动填充
 * 此类用于生成和修改创建时间和修改时间
 *
 * @author zurun
 * @date 2018/5/3 22:35:15
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入数据的时候写入创建时间
     * 此处没做非空判断,默认所有表都有此字段
     * 如果有不包含此字段的情况,需要先metaObject.getValue()判断是否为null,为null就不setValue了
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("createTime")){
            metaObject.setValue("createTime", new Date());
        }
        if (metaObject.hasGetter("updateTime")) {
            metaObject.setValue("updateTime", new Date());
        }
    }

    /**
     * update测试无效,没找到原因
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
