package cn.zull.lpc.practice.mybatis.service;

import cn.zull.lpc.common.basis.exception.LpcRuntimeException;
import cn.zull.lpc.practice.mybatis.entity.test.TestDeviceDynamicInfo;
import cn.zull.lpc.practice.mybatis.entity.test.TestDeviceInfo;
import cn.zull.lpc.practice.mybatis.mapper.DeviceDynamicInfoMapper;
import cn.zull.lpc.practice.mybatis.mapper.DeviceInfoMapper;
import cn.zull.lpc.practice.mybatis.mapper.test.TestDeviceDynamicInfoMapper;
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
    @Autowired
    TestDeviceDynamicInfoMapper testDeviceDynamicInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    public void test1(String name) {
        String deviceId = "X0990104FF040100002018121300000825";
        Map info = deviceDynamicInfoMapper.getDeviceDynamicInfoByDeviceId(deviceId);
        Map deviceInfo = deviceInfoMapper.getDeviceInfoByDeviceId(deviceId);
        System.out.println(info);
        System.out.println(deviceInfo);
    }

    /**
     * @param did
     * @param exception 是否报异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertTest1(String did, boolean exception) {
        TestDeviceInfo deviceInfo = new TestDeviceInfo()
                .setDid(did)
                .setDesc("test:" + did)
                .setSum(0);
        System.out.println("deviceInfo_insert_" + testDeviceInfoMapper.insert(deviceInfo));

        if (exception) {
            throw new LpcRuntimeException("主动中断");
        }

        TestDeviceDynamicInfo dynamicInfo = new TestDeviceDynamicInfo()
                .setDid(did)
                .setDevName("设备名_" + did);
        System.out.println("deviceDynamicInfo_insert_" + testDeviceDynamicInfoMapper.insert(dynamicInfo));
    }

    /**
     * 更新数据(检查版本号)
     *
     * @param did
     * @param desc
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateWithVersion(String did, String desc) {
        TestDeviceInfo deviceInfo = testDeviceInfoMapper.getByDid(did);
        deviceInfo.setSum(deviceInfo.getSum() + 1);
        deviceInfo.setDesc(desc);
        int result = testDeviceInfoMapper.updateDeviceInfoWithVersion(deviceInfo);
        System.out.println("r___" + result);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public void update2(String did, String desc) {
        TestDeviceInfo deviceInfo = testDeviceInfoMapper.getByDid(did);
        deviceInfo.setSum(deviceInfo.getSum() + 1);
        deviceInfo.setDesc(desc);
        for (int i = 0; i < 10; i++) {
            int result = testDeviceInfoMapper.updateDeviceInfoWithVersion(deviceInfo);
            System.out.println("___" + result);

        }
    }
}
