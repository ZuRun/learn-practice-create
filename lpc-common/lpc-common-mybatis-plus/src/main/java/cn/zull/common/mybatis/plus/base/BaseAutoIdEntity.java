package cn.zull.common.mybatis.plus.base;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zurun
 * @date 2018/6/15 16:36:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseAutoIdEntity extends BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;
}
