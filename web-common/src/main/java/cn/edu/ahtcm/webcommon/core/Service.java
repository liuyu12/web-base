package cn.edu.ahtcm.webcommon.core;

import java.util.List;

/**
 * JpaService
 * @author liuyu
 */
public interface Service<T,P> {
    /**
     * 添加
     * @param entity 实体
     */
    default void save(T entity){}

    /**
     * 修改
     * @param entity 实体
     */
    default void update(T entity){}

    /**
     * 根据id删除
     * @param id 主键
     */
    default void remove(P id){}

    /**
     * 根据id查询
     * @param id 主键
     * @return
     */
    default T getById(P id){return null;}

    /**
     * 查询全部
     * @return
     */
    default List<T> listAll(){return null;}
}
