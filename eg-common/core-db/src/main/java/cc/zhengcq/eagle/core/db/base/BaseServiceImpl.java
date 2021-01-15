package cc.zhengcq.eagle.core.db.base;

import cc.zhengcq.eagle.core.db.entity.Page;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.*;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.*;
import com.baomidou.mybatisplus.core.toolkit.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;


/**
 * Created by zhengcq
 * @param <T> 实体
 * @param <M> dao 操作类
 */
public class BaseServiceImpl<M extends BaseDao<T>, T extends BaseModel> extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * 日志对象
     */
    protected static final Logger logger   = LoggerFactory.getLogger(BaseServiceImpl.class);


    /**
     * <p>
     * TableId 注解存在更新记录，否插入一条记录
     * </p>
     *
     * @param entity
     *            实体对象
     * @return boolean
     */
    @Override
    public boolean insertOrUpdate(T entity) {
        return this.saveOrUpdate(entity);
    }

    /**
     * 插入数据
     * @param entity
     *            实体对象
     * @return
     */
    @Override
    public boolean insert(T entity) {
        entity.preInsert();
        return retBool(baseMapper.insert(entity));
    }


    /**
     * 根据主键id 删除
     * @param id
     *            主键ID
     * @return 结果
     */
    @Override
    public boolean deleteById(Serializable id) {
        return retBool(baseMapper.deleteById(id));
    }

    /**
     * 根据主键跟新对象
     * @param entity
     *            实体对象
     * @return 结果
     */
    @Override
    public boolean updateById(T entity) {
        entity.preUpdate();
        return retBool(baseMapper.updateById(entity));
    }

    /**
     * 根据 whereEntity 条件，更新记录
     * @param entity entity
     * @param wrapper wrapper
     * @return
     */
    @Override
    public boolean update(T entity, Wrapper<T> wrapper) {
        entity.preUpdate();
        return retBool(baseMapper.update(entity, wrapper));
    }


    /**
     * 根据主键返回记录
     * @param id
     *            主键ID
     * @return 结果
     */
    @Override
    public T selectById(Serializable id) {
        return baseMapper.selectById(id);
    }

    /**
     * 根据条件返回一条记录
     * @param wrapper wrapper
     * @return 结果
     */
    @Override
    public T selectOne(Wrapper<T> wrapper) {
        return this.baseMapper.selectOne(wrapper);
    }


    /**
     * 根据条件返回记录数
     * @param wrapper wrapper
     * @return 结果
     */
    @Override
    public int selectCount(Wrapper<T> wrapper) {
       return this.baseMapper.selectCount(wrapper);
    }

    /**
     * 根据条件返回记录列表
     * @param wrapper wrapper
     * @return 结果
     */
    @Override
    public List<T> selectList(Wrapper<T> wrapper) {
        return baseMapper.selectList(wrapper);
    }

    /**
     * 分页获取记录
     * @param page page
     * @return 结果
     */
    @Override
    public Page<T> selectPage(Page<T> page) {
       return baseMapper.selectPage(page, null);
    }


    /**
     * 根据条件分页获取记录
     * @param page page
     * @param wrapper wrapper
     * @return 结果
     */
    @Override
    public Page<T> selectPage(Page<T> page, Wrapper<T> wrapper) {
       return baseMapper.selectPage(page, wrapper);
    }

}
