package cc.zhengcq.eagle.base.service;

import cc.zhengcq.eagle.base.model.BaseVendorItem;
import cc.zhengcq.eagle.base.dao.BaseVendorItemDao;
import cc.zhengcq.eagle.base.param.ParamListVendorItem;
import cc.zhengcq.eagle.core.common.enums.EStatus;
import cc.zhengcq.eagle.core.common.utils.StringUtils;
import cc.zhengcq.eagle.core.db.base.BaseServiceImpl;
import cc.zhengcq.eagle.core.db.entity.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 供应商商品表 服务实现类
 *
 * @author msyt
 * @version v1.0.0
 * @date 2019-07-15
 * @since 2019-07-15
 */
@Service
public class BaseVendorItemService extends BaseServiceImpl<BaseVendorItemDao, BaseVendorItem> {

    public void saveVendorItem(Long userIdx, BaseVendorItem vendorItem) {

        if (!StringUtils.isZero(vendorItem.getIdx())) {
            vendorItem.setUpdateByMemberIdx(userIdx);
        } else {
            vendorItem.setCreateByMemberIdx(userIdx);
            vendorItem.setUpdateByMemberIdx(userIdx);
        }

        this.insertOrUpdate(vendorItem);
    }

    public BaseVendorItem getByVendorIdAndItemId(Long vendorIdx, Long itemIdx) {
        if (StringUtils.isZero(vendorIdx)
                || StringUtils.isZero(itemIdx)) {
            return null;
        }
        QueryWrapper<BaseVendorItem> wrapper = new QueryWrapper<>();

        wrapper.eq("status", EStatus.E_VALID.getCode());
        wrapper.eq("vendor_idx", vendorIdx);
        wrapper.eq("item_idx", itemIdx);

        List<BaseVendorItem> ls = baseMapper.selectList(wrapper);

        if (ls != null && !ls.isEmpty()) {
            return ls.get(0);
        }

        return null;
    }

    public Page<BaseVendorItem> listByFilterForPage(Page page, ParamListVendorItem param) {

        page.setRecords(baseMapper.listByFilter(page.getRowBounds(), param));

        return page;
    }
}
