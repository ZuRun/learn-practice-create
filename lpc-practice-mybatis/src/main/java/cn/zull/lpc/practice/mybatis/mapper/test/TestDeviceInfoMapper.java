package cn.zull.lpc.practice.mybatis.mapper.test;

import cn.zull.lpc.practice.mybatis.entity.test.TestDeviceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zurun
 * @date 2020/4/13 17:17:06
 */
@Mapper
public interface TestDeviceInfoMapper {

    TestDeviceInfoMapper getByDid(@Param("did") String did);

    int insert(TestDeviceInfo deviceInfo);
}
