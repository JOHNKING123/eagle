package cc.msyt.eagle.pur.dao;

import cc.msyt.eagle.core.db.annotation.MyBatisDao;
import cc.msyt.eagle.pur.model.PurPo;
import cc.msyt.eagle.core.db.base.BaseDao;
import cc.msyt.eagle.pur.param.ParamListPurPo;
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
public interface PurPoDao extends BaseDao<PurPo> {

    List<PurPo> listByFilter(RowBounds rowBounds, @Param("param") ParamListPurPo param);

    List<PurPo> listByFilter(@Param("param")ParamListPurPo param);
}