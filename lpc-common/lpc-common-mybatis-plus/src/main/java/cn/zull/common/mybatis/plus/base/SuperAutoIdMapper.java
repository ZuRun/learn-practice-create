package cn.zull.common.mybatis.plus.base;

/**
 * id为自动生成的Long类型的
 * 实体mapper继承此mapper,方便以后进行扩展
 *
 * @author zurun
 * @date 2018/6/15 15:45:37
 */
public interface SuperAutoIdMapper<T extends BaseAutoIdEntity> extends SuperMapper<T> {
    /**
     * 更新或插入
     *
     * @param entity
     * @return
     */
    default Integer saveOrUpdate(T entity) {
        if (entity.getId() == null || entity.getId() == 0) {
            return this.insert(entity);
        }
        return this.updateById(entity);
    }
}
