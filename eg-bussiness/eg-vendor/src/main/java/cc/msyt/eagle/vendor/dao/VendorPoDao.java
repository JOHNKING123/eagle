package cc.msyt.eagle.vendor.dao;

import cc.msyt.eagle.core.db.annotation.MyBatisDao;
import cc.msyt.eagle.core.db.base.BaseDao;
import cc.msyt.eagle.vendor.model.VendorPo;
import cc.msyt.eagle.vendor.param.ParamListVendorPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author msyt
 * @since 2019-07-16
 */
@MyBatisDao
public interface VendorPoDao extends BaseDao<VendorPo> {
    List<VendorPo> listByFilter(RowBounds rowBounds, @Param("param") ParamListVendorPo param);

    List<VendorPo> listByFilter(@Param("param") ParamListVendorPo param);
}