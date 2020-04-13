package cn.zull.lpc.practice.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author zurun
 * @date 2020/4/13 16:47:31
 */
@Mapper
public interface DeviceDynamicInfoMapper {

    Map getDeviceDynamicInfoById(@Param("id") Integer id);

    /**
     * 根据设备id获取动态信息
     *
     * @param deviceId
     * @return
     */
    Map getDeviceDynamicInfoByDeviceId(@Param("device_id") String deviceId);
}
