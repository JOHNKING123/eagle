package cc.msyt.eagle.base.dao;

import cc.msyt.eagle.base.param.ParamListVendorItem;
import cc.msyt.eagle.core.db.annotation.MyBatisDao;
import cc.msyt.eagle.base.model.BaseVendorItem;
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
public interface BaseVendorItemDao extends BaseDao<BaseVendorItem> {

    List<BaseVendorItem> listByFilter(RowBounds rowBounds, @Param("param") ParamListVendorItem param);

    List<BaseVendorItem> listByFilter(@Param("param")ParamListVendorItem param);
}