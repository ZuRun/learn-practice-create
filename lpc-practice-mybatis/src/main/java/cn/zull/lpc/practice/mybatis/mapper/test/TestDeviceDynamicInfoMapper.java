package cn.zull.lpc.practice.mybatis.mapper.test;

import cn.zull.lpc.practice.mybatis.entity.test.TestDeviceDynamicInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zurun
 * @date 2020/4/13 17:42:00
 */
@Mapper
public interface TestDeviceDynamicInfoMapper {

    int insert(TestDeviceDynamicInfo deviceDynamicInfo);
}
