package cn.zull.lpc.practice.mybatis.entity.test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author zurun
 * @date 2020/4/13 17:17:24
 */
@Getter
@Setter
@Accessors(chain = true)
public class TestDeviceDynamicInfo {
    private Long id;
    private String did;
    private String devName;
}
