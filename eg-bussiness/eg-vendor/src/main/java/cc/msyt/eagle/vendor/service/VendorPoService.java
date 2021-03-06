package cc.msyt.eagle.vendor.service;

import cc.msyt.eagle.core.common.entity.JsonResult;
import cc.msyt.eagle.core.common.enums.EStatus;
import cc.msyt.eagle.core.common.enums.vendor.EVendorPoBizStatus;
import cc.msyt.eagle.core.common.utils.DateUtils;
import cc.msyt.eagle.core.common.utils.JSonUtils;
import cc.msyt.eagle.core.common.utils.StringUtils;
import cc.msyt.eagle.core.db.entity.Page;
import cc.msyt.eagle.pur.param.ParamRevVendorItemConfirm;
import cc.msyt.eagle.vendor.client.PurSubPoVendorClient;
import cc.msyt.eagle.vendor.model.VendorPo;
import cc.msyt.eagle.vendor.dao.VendorPoDao;
import cc.msyt.eagle.core.db.base.BaseServiceImpl;
import cc.msyt.eagle.vendor.model.VendorPoLine;
import cc.msyt.eagle.vendor.param.ParamListVendorPo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  供应商采购单 服务实现类
 * @author    msyt
 * @date 	  2019-07-16
 * @version   v1.0.0
 * @since     2019-07-16
 */
@Service
public class VendorPoService extends BaseServiceImpl<VendorPoDao, VendorPo> {

    @Autowired
    private VendorPoLineService vendorPoLineService;

    @Autowired
    private PurSubPoVendorClient purSubPoVendorClient;

    public void saveVendorPo(Long userIdx,VendorPo vendorPo){

        if(!StringUtils.isZero(vendorPo.getIdx())){

            vendorPo.setUpdateByMemberIdx(userIdx);
        }else{
            String orderNo = String.format("%d%s",System.currentTimeMillis(),StringUtils.randomNumber(4));
            vendorPo.setOrderNo(orderNo);
            vendorPo.setBizStatus(EVendorPoBizStatus.E_WAIT_CONFIRME.getCode());
            vendorPo.setCreateByMemberIdx(userIdx);
            vendorPo.setUpdateByMemberIdx(userIdx);
        }

        this.insertOrUpdate(vendorPo);

        if(vendorPo.getPoLineList() != null
            && !vendorPo.getPoLineList().isEmpty()) {
            for(VendorPoLine poLine:vendorPo.getPoLineList()){
                poLine.setPoIdx(vendorPo.getIdx());
                // 金额(RMB)
                BigDecimal qtyOfPutTmp = new BigDecimal(poLine.getQtyOfPurchase());
                BigDecimal  purPriceTmp = new BigDecimal(poLine.getPurPrice());
                BigDecimal amtTmp = purPriceTmp.multiply(poLine.getExchangeRate()).multiply(qtyOfPutTmp).setScale(2,BigDecimal.ROUND_HALF_UP);
                poLine.setAmt(amtTmp.longValue());

                poLine.setCreateByMemberIdx(userIdx);

                vendorPoLineService.insertOrUpdate(poLine);
            }
        }

        recalPo(userIdx,vendorPo);

    }

    public void reveiveVendorPo(Long userIdx,VendorPo vendorPo){

        VendorPo vendorPoDb = this.getByPurPoIdxAndVendorIdex(vendorPo.getPurPoIdx(),vendorPo.getVendorIdx());
        if(vendorPoDb != null){
            List<VendorPoLine> poLineDbList = this.vendorPoLineService.getByPoIdx(vendorPoDb.getIdx());
            Map<Long,VendorPoLine> vendorPoLineDbMap = poLineDbList.stream().collect(Collectors.toMap(vo->vo.getItemIdx(),vo->vo));

            List<VendorPoLine> poLineList = new LinkedList<>();
            for(VendorPoLine poLine:vendorPo.getPoLineList()){
                if(vendorPoLineDbMap.containsKey(poLine.getItemIdx())){
                    VendorPoLine poLineDb = vendorPoLineDbMap.get(poLine.getItemIdx());
                    poLineDb.setQtyOfPurchase(poLine.getQtyOfPurchase());
                    poLineDb.setPurPrice(poLine.getPurPrice());
                    poLineDb.setExchangeRate(poLine.getExchangeRate());
                    poLineList.add(poLineDb);
                }else{
                    poLineList.add(poLine);
                }
            }
        }else{
            this.saveVendorPo(userIdx,vendorPo);
        }
    }

    /**
     * 计算采购单的金额，商品数量等信息
     * @param userIdx
     * @param vendorPo
     */
    public void recalPo(Long userIdx,VendorPo vendorPo){

        List<VendorPoLine> poLineList = vendorPoLineService.getByPoIdx(vendorPo.getIdx());

        if(poLineList != null && !poLineList.isEmpty()) {
            Integer numOfPurItem = 0; // 采购总数
            Long  totalAmt = 0L; // 总金额
            for(VendorPoLine poLine:poLineList) {
                numOfPurItem = numOfPurItem + poLine.getQtyOfPurchase();
                totalAmt = totalAmt + poLine.getAmt();

                vendorPo.setNumOfPurItem(numOfPurItem);
                vendorPo.setTotalAmt(totalAmt);
                vendorPo.setUpdateByMemberIdx(userIdx);

                this.updateById(vendorPo);
            }
        }
    }


    public void confirmVendorPo(Long userIdx,VendorPo vendorPo){

        vendorPo.setBizStatus(EVendorPoBizStatus.E_VENDOR_CONFIRMED.getCode());
        vendorPo.setUpdateByMemberIdx(userIdx);
        this.updateById(vendorPo);

        List<VendorPoLine> vendorPoLines = this.vendorPoLineService.getByPoIdx(vendorPo.getIdx());

        if(vendorPoLines != null && !vendorPoLines.isEmpty()) {
            List<String> itemIdexs = vendorPoLines.stream().map(vo->vo.getItemIdx().toString()).collect(Collectors.toList());
            ParamRevVendorItemConfirm paramRevVendorItemConfirm  = new ParamRevVendorItemConfirm();
            paramRevVendorItemConfirm.setPoIdx(vendorPo.getPurPoIdx());
            paramRevVendorItemConfirm.setVendorIdx(vendorPo.getVendorIdx());
            paramRevVendorItemConfirm.setItemIdxList(itemIdexs);

            logger.warn("confirmVendorPo-start-invoke-revVendorItemConfirm param:{}", JSonUtils.toJson(paramRevVendorItemConfirm));
            JsonResult rs = purSubPoVendorClient.revVendorItemConfirm(userIdx,paramRevVendorItemConfirm);
            if(rs != null && rs.isSucceeded()){
                logger.warn("confirmVendorPo-end-invoke-revVendorItemConfirm param:{} rs:{}", JSonUtils.toJson(paramRevVendorItemConfirm),JSonUtils.toJson(rs));
            }else{
                logger.error("confirmVendorPo-fail-invoke-revVendorItemConfirm param:{} rs:{}", JSonUtils.toJson(paramRevVendorItemConfirm),JSonUtils.toJson(rs));
            }

        }



    }

    public VendorPo getByPurPoIdxAndVendorIdex(Long purPoIdx,Long vendorIdx) {
        if(StringUtils.isZero(purPoIdx) || StringUtils.isZero(vendorIdx)) {
            return null;
        }
        Wrapper<VendorPo> wrapper = new EntityWrapper<>();
        wrapper.eq("status", EStatus.E_VALID.getCode());
        wrapper.eq("pur_po_idx",purPoIdx);
        wrapper.eq("vendor_idx",vendorIdx);

        List<VendorPo> ls = this.baseDao.selectList(wrapper);

        if(ls != null && !ls.isEmpty()) {
            return ls.get(0);
        }
        return null;
    }

    public Page<VendorPo> listByFilterForPage(Page page, ParamListVendorPo param){

        page.setRecords(baseDao.listByFilter(page,param));

        return page;
    }
}
