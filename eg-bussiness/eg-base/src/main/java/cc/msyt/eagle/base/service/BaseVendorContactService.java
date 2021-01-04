package cc.msyt.eagle.base.service;

import cc.msyt.eagle.base.model.BaseVendorContact;
import cc.msyt.eagle.base.dao.BaseVendorContactDao;
import cc.msyt.eagle.base.param.ParamListVendorContact;
import cc.msyt.eagle.core.common.utils.StringUtils;
import cc.msyt.eagle.core.db.base.BaseServiceImpl;
import cc.msyt.eagle.core.db.entity.Page;
import org.springframework.stereotype.Service;

/**
 *  供应商联系人关系表 服务实现类
 * @author    msyt
 * @date 	  2019-07-15
 * @version   v1.0.0
 * @since     2019-07-15
 */
@Service
public class BaseVendorContactService extends BaseServiceImpl<BaseVendorContactDao, BaseVendorContact> {

    public void saveVendorContact(Long userIdx,BaseVendorContact vendorContact){

        if(!StringUtils.isZero(vendorContact.getIdx())) {
            vendorContact.setUpdateByMemberIdx(userIdx);
        }else{
            vendorContact.setCreateByMemberIdx(userIdx);
            vendorContact.setUpdateByMemberIdx(userIdx);
        }

        this.insertOrUpdate(vendorContact);
    }


    public Page<BaseVendorContact> listByFilterForPage(Page page, ParamListVendorContact param){

        page.setRecords(baseDao.listByFilter(page,param));

        return page;
    }
}
