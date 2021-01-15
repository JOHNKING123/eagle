package cc.zhengcq.eagle.core.db.base;


import cc.zhengcq.eagle.core.db.entity.Page;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhengcq （ 泛型：T 是实体 ）
 * @param <T> 实体
 */
public interface BaseService<T> {
    /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity
     *            实体对象
     * @return boolean
     */
    boolean insert(T entity);



    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id
     *            主键ID
     * @return boolean
     */
    boolean deleteById(Serializable id);


    /**
     * <p>
     * 根据 ID 修改
     * </p>
     *
     * @param entity
     *            实体对象
     * @return boolean
     */
    boolean updateById(T entity);

    /**
     * 根据 whereEntity 条件，更新记录
     * @param entity entity
     * @param wrapper wrapper
     * @return
     */
    boolean update(T entity, Wrapper<T> wrapper);


    /**
     * <p>
     * TableId 注解存在更新记录，否插入一条记录
     * </p>
     *
     * @param entity
     *            实体对象
     * @return boolean
     */
    boolean insertOrUpdate(T entity);

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id
     *            主键ID
     * @return T
     */
    T selectById(Serializable id);


    /**
     * <p>
     * 根据 Wrapper，查询一条记录
     * </p>
     *
     * @param wrapper
     *            实体对象
     * @return T
     */
    T selectOne(Wrapper<T> wrapper);



    /**
     * <p>
     * 根据 Wrapper 条件，查询总记录数
     * </p>
     *
     * @param wrapper
     *            实体对象
     * @return int
     */
    int selectCount(Wrapper<T> wrapper);

    /**
     * <p>
     * 查询列表
     * </p>
     *
     * @param wrapper
     *            实体包装类 {@link Wrapper}
     * @return
     */
    List<T> selectList(Wrapper<T> wrapper);

    /**
     * <p>
     * 翻页查询
     * </p>
     *
     * @param page
     *            翻页对象
     * @return
     */
    Page<T> selectPage(Page<T> page);




    /**
     * <p>
     * 翻页查询
     * </p>
     *
     * @param page
     *            翻页对象
     * @param wrapper
     *            实体包装类 {@link Wrapper}
     * @return
     */
    Page<T> selectPage(Page<T> page, Wrapper<T> wrapper);
}
