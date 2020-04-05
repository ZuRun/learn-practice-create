package cn.zull.lpc.common.mybatis.plus.base;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 实体mapper可继承此mapper,方便以后进行扩展
 *
 * @author zurun
 * @date 2018/6/15 15:45:37
 */
public interface SuperMapper<T> extends BaseMapper<T> {

    /**
     * 根据条件获取一条记录
     *
     * @param wrapper 实体对象封装操作类,可为null
     * @return
     */
    default T selectOneByWrapper(Wrapper<T> wrapper) {
        List<T> list = this.selectList(wrapper);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
}
