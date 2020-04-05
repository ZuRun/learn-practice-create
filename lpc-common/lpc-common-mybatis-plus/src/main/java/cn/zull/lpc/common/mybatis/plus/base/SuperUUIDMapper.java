package cn.zull.lpc.common.mybatis.plus.base;


import cn.zull.lpc.common.basis.utils.UUIDUtils;
import org.springframework.util.StringUtils;

/**
 * id为uuid
 * 实体mapper继承此mapper,方便以后进行扩展
 *
 * @author zurun
 * @date 2018/6/15 15:45:37
 */
public interface SuperUUIDMapper<T extends BaseGuidEntity> extends SuperMapper<T> {
    /**
     * 更新或插入
     *
     * @param entity
     * @return
     */
    default Integer saveOrUpdate(T entity) {

        if (StringUtils.isEmpty(entity.getId())) {
            entity.setId(UUIDUtils.simpleUUID());
            return this.insert(entity);
        }
        return this.updateById(entity);
    }
}
