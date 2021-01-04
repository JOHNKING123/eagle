package cc.zhengcq.eagle.base.service;

import cc.zhengcq.eagle.base.model.BaseVendor;
import cc.zhengcq.eagle.base.dao.BaseVendorDao;
import cc.zhengcq.eagle.base.model.BaseVendorContact;
import cc.zhengcq.eagle.base.param.ParamListVendor;
import cc.zhengcq.eagle.core.common.utils.StringUtils;
import cc.zhengcq.eagle.core.db.base.BaseServiceImpl;
import cc.zhengcq.eagle.core.db.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  供应商 服务实现类
 * @author    msyt
 * @date 	  2019-07-15
 * @version   v1.0.0
 * @since     2019-07-15
 */
@Service
public class BaseVendorService extends BaseServiceImpl<BaseVendorDao, BaseVendor> {

    @Autowired
    private BaseVendorContactService baseVendorContactService;

    @Transactional
    public void   saveVendor(Long userIdx,BaseVendor baseVendor){

        if(!StringUtils.isZero(baseVendor.getIdx())){

            baseVendor.setUpdateByMemberIdx(userIdx);
        }else{
            baseVendor.setCreateByMemberIdx(userIdx);
            baseVendor.setUpdateByMemberIdx(userIdx);
        }
        this.insertOrUpdate(baseVendor);

        if(baseVendor.getVendorContactList() != null
                && !baseVendor.getVendorContactList().isEmpty()) {
            for (BaseVendorContact baseVendorContact:baseVendor.getVendorContactList()){
                baseVendorContact.setVendorIdx(baseVendor.getIdx());
                baseVendorContactService.saveVendorContact(userIdx,baseVendorContact);
            }
        }
    }


    public Page<BaseVendor> listByFilterForPage(Page page, ParamListVendor param){

        page.setRecords(baseDao.listByFilter(page,param));

        return page;
    }

    public List<BaseVendor> listByFilter(ParamListVendor param){
        return baseDao.listByFilter(param);
    }
}
