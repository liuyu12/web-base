package cn.edu.ahtcm.webcommon.tkmybatis;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * @author liuyu
 */
public interface IService<T> {
    /**
     * 单个保存
     * @param model
     */
    void save(T model);

    /**
     * 批量保存
     * @param models
     */
    void save(List<T> models);

    /**
     * 通过Id删除
     * @param id
     */
    void deleteById(Object id);

    /**
     * 通过id批量删除
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 更新
     * @param model
     */
    void update(T model);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    T findById(Object id);

    /**
     * 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     * @param fieldName
     * @param value
     * @return
     * @throws TooManyResultsException
     */
    T findBy(String fieldName, Object value) throws TooManyResultsException;

    /**
     * 通过多个ID查找//eg：ids -> “1,2,3,4”
     * @param ids
     * @return
     */
    List<T> findByIds(String ids);

    /**
     * 按条件查询
     * @param condition
     * @return
     */
    List<T> findByCondition(Condition condition);

    /**
     * 查询所有
     * @return
     */
    List<T> findAll();
}
