/**
 * 
 */
package cc.zhengcq.eagle.core.db.base;

import cc.zhengcq.eagle.core.db.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author zhengcq  2019/07/11
 *
 * 
 */
@MyBatisDao
public interface BaseDao<T extends BaseModel> extends com.baomidou.mybatisplus.mapper.BaseMapper<T> {

	List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

	List<Long> selectIdPage(RowBounds rowBounds, @Param("cm") Map<String, Object> params);

}
