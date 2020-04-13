package cn.zull.lpc.practice.mybatis.service;

import cn.zull.lpc.practice.mybatis.entity.test.TestDeviceInfo;
import cn.zull.lpc.practice.mybatis.mapper.DeviceDynamicInfoMapper;
import cn.zull.lpc.practice.mybatis.mapper.DeviceInfoMapper;
import cn.zull.lpc.practice.mybatis.mapper.test.TestDeviceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author zurun
 * @date 2020/4/13 15:50:08
 */
@Slf4j
@Service
public class TransactionTestService {

    @Autowired
    DeviceInfoMapper deviceInfoMapper;
    @Autowired
    DeviceDynamicInfoMapper deviceDynamicInfoMapper;
    @Autowired
    TestDeviceInfoMapper testDeviceInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    public void test1(String name) {
        String deviceId = "X0990104FF040100002018121300000825";
        Map info = deviceDynamicInfoMapper.getDeviceDynamicInfoByDeviceId(deviceId);
        Map deviceInfo = deviceInfoMapper.getDeviceInfoByDeviceId(deviceId);
        System.out.println(info);
        System.out.println(deviceInfo);
    }

    @Transactional
    public void insertTest(String did) {
        TestDeviceInfo deviceInfo = new TestDeviceInfo()
                .setDid(did)
                .setDesc("test:" + did)
                .setSum(0);
        testDeviceInfoMapper.insert(deviceInfo);
    }

}
