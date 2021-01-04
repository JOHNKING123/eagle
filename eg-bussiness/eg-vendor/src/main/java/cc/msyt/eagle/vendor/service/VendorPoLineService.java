package cc.msyt.eagle.vendor.service;

import cc.msyt.eagle.core.common.entity.JsonResult;
import cc.msyt.eagle.core.common.enums.EStatus;
import cc.msyt.eagle.core.common.utils.StringUtils;
import cc.msyt.eagle.pur.param.ParamRevVendorItemChg;
import cc.msyt.eagle.vendor.client.PurSubPoVendorClient;
import cc.msyt.eagle.vendor.model.VendorPo;
import cc.msyt.eagle.vendor.model.VendorPoLine;
import cc.msyt.eagle.vendor.dao.VendorPoLineDao;
import cc.msyt.eagle.core.db.base.BaseServiceImpl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 *  采购子单 服务实现类
 * @author    msyt
 * @date 	  2019-07-16
 * @version   v1.0.0
 * @since     2019-07-16
 */
@Service
public class VendorPoLineService extends BaseServiceImpl<VendorPoLineDao, VendorPoLine> {

    @Autowired
    private PurSubPoVendorClient purSubPoVendorClient;

    public JsonResult pushLineChg2Pur(Long userIdx, VendorPo vendorPo,VendorPoLine poLine){


        ParamRevVendorItemChg paramRevVendorItemChg = new ParamRevVendorItemChg();
        paramRevVendorItemChg.setPurPoIdx(vendorPo.getPurPoIdx());
        paramRevVendorItemChg.setItemIdx(poLine.getItemIdx());
        paramRevVendorItemChg.setVendorIdx(vendorPo.getVendorIdx());
        paramRevVendorItemChg.setAvailableStock(poLine.getAvailableStock());
        paramRevVendorItemChg.setDeliveryPeriod(poLine.getDeliveryPeriod());
        paramRevVendorItemChg.setShipMethod(poLine.getShipMethod());
        paramRevVendorItemChg.setSupplyPrice(poLine.getSupplyPrice());

        JsonResult rs = purSubPoVendorClient.revVendorItemChg(userIdx,paramRevVendorItemChg);

        return rs;
    }

    public List<VendorPoLine> getByPoIdx(Long poIdx){
        if(StringUtils.isZero(poIdx)){
            return Collections.EMPTY_LIST;
        }
        Wrapper<VendorPoLine> wrapper = new EntityWrapper<>();
        wrapper.eq("status", EStatus.E_VALID.getCode());
        wrapper.eq("po_idx",poIdx);

        return baseDao.selectList(wrapper);
    }


}
