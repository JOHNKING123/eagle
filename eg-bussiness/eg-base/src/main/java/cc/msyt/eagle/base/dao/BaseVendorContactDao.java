package cc.msyt.eagle.base.dao;

import cc.msyt.eagle.base.param.ParamListVendorContact;
import cc.msyt.eagle.core.db.annotation.MyBatisDao;
import cc.msyt.eagle.base.model.BaseVendorContact;
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
public interface BaseVendorContactDao extends BaseDao<BaseVendorContact> {
    List<BaseVendorContact> listByFilter(RowBounds rowBounds, @Param("param") ParamListVendorContact param);

    List<BaseVendorContact> listByFilter(@Param("param")ParamListVendorContact param);
}