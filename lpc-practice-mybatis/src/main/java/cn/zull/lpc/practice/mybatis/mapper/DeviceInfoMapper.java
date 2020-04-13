package cn.zull.lpc.practice.mybatis.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author zurun
 * @date 2020/4/9 14:40:33
 */
@Mapper
public interface DeviceInfoMapper {

    Long getCount();

    Map getDeviceInfoByDeviceId(@Param("device_id") String deviceId);
}
