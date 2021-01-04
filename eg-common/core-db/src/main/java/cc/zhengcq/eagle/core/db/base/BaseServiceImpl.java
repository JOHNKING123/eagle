package cc.zhengcq.eagle.core.db.base;

import cc.zhengcq.eagle.core.db.entity.Page;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;


/**
 * Created by zhengcq
 */
public class BaseServiceImpl<M extends BaseDao<T>, T extends BaseModel> implements BaseService<T> {
    protected static final Logger logger   = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    protected M baseDao;

    @SuppressWarnings("unchecked")
    protected Class<T> currentModleClass() {
        return ReflectionKit.getSuperClassGenricType(getClass(), 1);
    }

    /**
     * <p>
     * 批量操作 SqlSession
     * </p>
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModleClass());
    }

    /**
     * 获取SqlStatement
     *
     * @param sqlMethod
     * @return
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModleClass()).getSqlStatement(sqlMethod.getMethod());
    }

    /**
     * <p>
     * 判断数据库操作是否成功
     * </p>
     * <p>
     * 注意！！ 该方法为 Integer 判断，不可传入 int 基本类型
     * </p>
     *
     * @param result
     *            数据库操作返回影响条数
     * @return boolean
     */
    protected static boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    /**
     * <p>
     * TableId 注解存在更新记录，否插入一条记录
     * </p>
     *
     * @param entity
     *            实体对象
     * @return boolean
     */
    public boolean insertOrUpdate(T entity) {
        entity.preUpdate();
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal)) {
                    return insert(entity);
                } else {
					/*
					 * 更新成功直接返回，失败执行插入逻辑
					 */
                    boolean rlt = updateById(entity);
                    if (!rlt) {
                        return insert(entity);
                    }
                    return rlt;
                }
            } else {
                throw new MybatisPlusException("Error:  Can not execute. Could not find @TableId.");
            }
        }
        return false;
    }

    public boolean insert(T entity) {
        entity.preInsert();
        return retBool(baseDao.insert(entity));
    }



    public boolean deleteById(Serializable id) {
        return retBool(baseDao.deleteById(id));
    }


    public boolean updateById(T entity) {
        entity.preUpdate();
        return retBool(baseDao.updateById(entity));
    }

    public boolean update(T entity, Wrapper<T> wrapper) {
        entity.preUpdate();
        return retBool(baseDao.update(entity, wrapper));
    }



    public T selectById(Serializable id) {
        return baseDao.selectById(id);
    }


    public T selectOne(Wrapper<T> wrapper) {
        return SqlHelper.getObject(baseDao.selectList(wrapper));
    }



    public int selectCount(Wrapper<T> wrapper) {
        return SqlHelper.retCount(baseDao.selectCount(wrapper));
    }

    public List<T> selectList(Wrapper<T> wrapper) {
        return baseDao.selectList(wrapper);
    }

    public Page<T> selectPage(Page<T> page) {
        page.setRecords(baseDao.selectPage(page, null));
        return page;
    }


    public Page<T> selectPage(Page<T> page, Wrapper<T> wrapper) {
        SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseDao.selectPage(page, wrapper));
        return page;
    }


}
