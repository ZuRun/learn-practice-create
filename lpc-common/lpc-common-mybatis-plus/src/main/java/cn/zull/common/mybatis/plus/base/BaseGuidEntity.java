package cn.zull.common.mybatis.plus.base;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author zurun
 * @date 2018/6/15 16:36:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseGuidEntity extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.UUID)
    protected String id;

}
