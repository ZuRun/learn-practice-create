package cn.zull.lpc.practice.mybatis.entity;

import cn.zull.lpc.common.mybatis.plus.base.BaseGuidEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备基本信息表
 * @author zurun
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceInfoModel extends BaseGuidEntity {

    /**
     * 设备原始名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 设备平台，平台英文名称，用于唯一标识，如mcloud
     */
    @TableField(value = "platform")
    private String platform;

    /**
     * 设备型号标识
     */
    @TableField(value = "model_id")
    private String modelId;

    /**
     * 设备序列号
     */
    @TableField(value = "device_id")
    private String deviceId;

    /**
     * 设备原平台编号
     */
    @TableField(value = "platform_device_id")
    private String platformDeviceId;

    /**
     * ctei码
     */
    @TableField(value = "ctei")
    private String ctei;

    /**
     * CTEI码状态 0：失效 1：有效
     */
    @TableField(value = "ctei_state")
    private Integer cteiState;

    /**
     * 设备的mac地址，不带冒号，大写
     */
    @TableField(value = "mac")
    private String mac;

    /**
     * mac地址状态 0：失效 1：有效
     */
    @TableField(value = "mac_state")
    private Integer macState;

    /**
     * 设备的imei码
     */
    @TableField(value = "imei")
    private String imei;

    /**
     * imei码状态 0：失效 1：有效
     */
    @TableField(value = "imei_state")
    private Integer imeiState;

    /**
     * 用于统计数据的deviceId
     */
    @TableField(value = "statistic_device_id")
    private String statisticDeviceId;
}
