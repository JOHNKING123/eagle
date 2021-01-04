package cc.msyt.eagle.base.dao;

import cc.msyt.eagle.base.param.ParamListVendor;
import cc.msyt.eagle.core.db.annotation.MyBatisDao;
import cc.msyt.eagle.base.model.BaseVendor;
import cc.msyt.eagle.core.db.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author msyt
 * @since 2019-07-15
 */
@MyBatisDao
public interface BaseVendorDao extends BaseDao<BaseVendor> {


    List<BaseVendor> listByFilter(RowBounds rowBounds, @Param("param")ParamListVendor param);

    List<BaseVendor> listByFilter(@Param("param")ParamListVendor param);
}