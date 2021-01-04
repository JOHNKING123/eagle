package cc.zhengcq.eagle.pur.dao;

import cc.zhengcq.eagle.core.db.annotation.MyBatisDao;
import cc.zhengcq.eagle.pur.model.PurPo;
import cc.zhengcq.eagle.core.db.base.BaseDao;
import cc.zhengcq.eagle.pur.param.ParamListPurPo;
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