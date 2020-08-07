package cn.zull.lpc.practice.mybatis.service.pool;

import cn.zull.lpc.common.basis.utils.JsonUtils;
import com.alibaba.druid.stat.DruidStatManagerFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zurun
 * @date 2020/8/4 18:14:17
 */
@Slf4j
@Service
public class DruidMonitorService {
    public void getList() {
        System.out.println("-------start----------");
        List<Map<String, Object>> dataSourceStatDataList = DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
        System.out.println(JsonUtils.toJSONString(dataSourceStatDataList));
    }
}
