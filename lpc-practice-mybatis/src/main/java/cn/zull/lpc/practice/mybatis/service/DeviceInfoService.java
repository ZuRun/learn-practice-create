package cn.zull.lpc.practice.mybatis.service;

import cn.zull.lpc.practice.mybatis.mapper.DeviceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zurun
 * @date 2020/4/9 15:56:24
 */
@Slf4j
@Service
public class DeviceInfoService {

    @Resource
    DeviceInfoMapper deviceInfoMapper;


    @Transactional(rollbackFor = Exception.class)
    public long getDeviceCount() {
        return deviceInfoMapper.getCount();
    }
}
