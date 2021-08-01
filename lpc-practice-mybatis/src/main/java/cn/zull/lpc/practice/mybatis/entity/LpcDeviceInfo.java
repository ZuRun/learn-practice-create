package cn.zull.lpc.practice.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jared.Zu
 * @date 2021/6/9 15:19:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lpc_device_info")
public class LpcDeviceInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String did;
    private String remark;
    private Integer sum;
}
