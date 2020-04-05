package cn.zull.lpc.common.mybatis.plus.base;


import cn.zull.lpc.common.mybatis.plus.config.MyMetaObjectHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;


/**
 * 暂时包含创建时间和更新时间
 * 因为id有的是long有的是uuid,暂时在实体类中自己定义或者自建在加2个base类继承此类 吧
 *
 * @author zurun
 * @date 2018/5/2 00:59:28
 */
@Data
public class BaseEntity {

    /**
     * 创建时间
     * 此字段不需要手动set
     *
     * @see MyMetaObjectHandler
     */
    @TableField(value = "create_time", fill = INSERT)
    protected Date createTime;
    /**
     * 更新时间
     * 此字段不需要手动set
     *
     * @see MyMetaObjectHandler
     */
    @TableField(value = "update_time", update = "now()", fill = INSERT)
    protected Date updateTime;


}
