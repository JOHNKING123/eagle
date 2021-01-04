package cc.msyt.eagle.pur.service;

import cc.msyt.eagle.core.common.entity.JsonResult;
import cc.msyt.eagle.core.common.enums.pur.EPurPoBizStatus;
import cc.msyt.eagle.core.common.enums.pur.EPurSubPoBizStatus;
import cc.msyt.eagle.core.common.utils.DateUtils;
import cc.msyt.eagle.core.common.utils.JSonUtils;
import cc.msyt.eagle.core.common.utils.StringUtils;
import cc.msyt.eagle.core.db.entity.Page;
import cc.msyt.eagle.pur.client.VenderPoClient;
import cc.msyt.eagle.pur.model.PurPo;
import cc.msyt.eagle.pur.dao.PurPoDao;
import cc.msyt.eagle.core.db.base.BaseServiceImpl;
import cc.msyt.eagle.pur.model.PurSubPo;
import cc.msyt.eagle.pur.model.PurSubPoVendor;
import cc.msyt.eagle.pur.param.ParamListPurPo;
import cc.msyt.eagle.vendor.model.VendorPo;
import cc.msyt.eagle.vendor.model.VendorPoLine;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  采购单 服务实现类
 * @author    msyt
 * @date 	  2019-07-16
 * @version   v1.0.0
 * @since     2019-07-16
 */
@Service
public class PurPoService extends BaseServiceImpl<PurPoDao, PurPo> {

    @Autowired
    private PurSubPoService purSubPoService;

    @Autowired
    private PurSubPoVendorService purSubPoVendorService;

    @Autowired
    private VenderPoClient venderPoClient;
    /**
     * 保存采购订单信息
     * @param userIdx
     * @param purPo
     */
    @Transactional
    public void savePo(Long userIdx,PurPo purPo){

        Date curDate = new Date();
        if(!StringUtils.isZero(purPo.getIdx())){

            purPo.setUpdateByMemberIdx(userIdx);
        }else{
            String orderNo = String.format("%d%s",System.currentTimeMillis(),StringUtils.randomNumber(4));
            purPo.setOrderNo(orderNo);
            purPo.setBizStatus(EPurPoBizStatus.E_WAIT_PUSH.getCode());
            purPo.setCreateByMemberIdx(userIdx);
            purPo.setUpdateByMemberIdx(userIdx);
        }

        this.insertOrUpdate(purPo);

        if(purPo.getSubPoList() != null
                && !purPo.getSubPoList().isEmpty()) {
            for(PurSubPo purSubPo:purPo.getSubPoList()) {
                purSubPo.setPoIdx(purPo.getIdx());
                purSubPo.setBizStatus(EPurSubPoBizStatus.E_WAIT_CONFIRME.getCode());
                purSubPoService.savePurSubPo(userIdx,purSubPo);
            }// end of for(PurSubPo purSubPo:purPo.getSubPoList())
        } // end of if(purPo.getSubPoList() != null ..

        // 计算采购单的金额，商品数量等信息
        recalPo(userIdx,purPo);
    }

    /**
     * 计算采购单的金额，商品数量等信息
     * @param userIdx
     * @param purPo
     */
    public void recalPo(Long userIdx,PurPo purPo){

        List<PurSubPo> subPoList = this.purSubPoService.getByPoIdx(purPo.getIdx());

        if(subPoList != null && !subPoList.isEmpty()) {
            Integer numOfSubOrder = 0; // 子单数
            Integer numOfPurItem = 0; // 采购总数
            Long  totalAmt = 0L; // 总金额
            for(PurSubPo purSubPo:subPoList) {
                numOfSubOrder++;
                numOfPurItem = numOfPurItem + purSubPo.getQtyOfPurchase();
                totalAmt = totalAmt + purSubPo.getSubAmt();

                purPo.setNumOfSubOrder(numOfSubOrder);
                purPo.setNumOfPurItem(numOfPurItem);
                purPo.setTotalAmt(totalAmt);
                purPo.setUpdateByMemberIdx(userIdx);

                this.updateById(purPo);
            }
        }
    }

    /**
     *  推送采购单到各个供应商
     * @param userIdx 用户Idx
     * @param poIdx 采购单Idx
     */
    @Transactional
    public void pushPurPo(Long userIdx,Long poIdx){

        PurPo purPo = this.selectById(poIdx);

        List<PurSubPo> subPoList = this.purSubPoService.getByPoIdx(poIdx);

        List<PurSubPoVendor> allSubPoVendorList = this.purSubPoVendorService.getByPoIdx(poIdx);

        // 1. 根据采购单分别生成各个供应商的采购单
        if(purPo != null && subPoList != null && !subPoList.isEmpty()
                && allSubPoVendorList != null && !allSubPoVendorList.isEmpty()) {

            //  key:vendorId  供应商Id
            //  value:List<PurSubPoVendor>  供应商采购项列表
            Map<Long,List<VendorPoLine>> vendorPoLineListMap = new HashMap<>();



            //  key:subPoIdx  采购子单Idx
            //  value:List<PurSubPoVendor>  采购子单供应商列表
            Map<Long,List<PurSubPoVendor>> subPoVendorListMap = allSubPoVendorList.stream().collect(Collectors.groupingBy(vo->vo.getSubPoIdx()));

            for(PurSubPo subPo:subPoList){
                if(!subPoVendorListMap.containsKey(subPo.getIdx())) {
                    continue;
                }

                List<PurSubPoVendor> subPoVendorList = subPoVendorListMap.get(subPo.getIdx());

                for(PurSubPoVendor purSubPoVendor:subPoVendorList){

                    VendorPoLine vendorPoLine = new VendorPoLine();
                    BeanUtils.copyProperties(subPo,vendorPoLine);

                    vendorPoLine.setIdx(null);
                    vendorPoLine.setPoIdx(null);

                    vendorPoLine.setSupplyPrice(purSubPoVendor.getSupplyPrice());
                    vendorPoLine.setCurrency(purSubPoVendor.getCurrency());
                    vendorPoLine.setAvailableStock(purSubPoVendor.getAvailableStock());
                    vendorPoLine.setDeliveryPeriod(purSubPoVendor.getDeliveryPeriod());
                    vendorPoLine.setShipMethod(purSubPoVendor.getShipMethod());
                    vendorPoLine.setExchangeRate(purSubPoVendor.getExchangeRate());
                    vendorPoLine.setPurPrice(purSubPoVendor.getPurPrice());
                    vendorPoLine.setQtyOfPurchase(purSubPoVendor.getQtyOfPur());

                    if(vendorPoLineListMap.containsKey(purSubPoVendor.getVendorIdx())) {
                        List<VendorPoLine> tmpLs = vendorPoLineListMap.get(purSubPoVendor.getVendorIdx());
                        tmpLs.add(vendorPoLine);
                    }else{
                        List<VendorPoLine> tmpLs = new LinkedList<>();
                        tmpLs.add(vendorPoLine);
                        vendorPoLineListMap.put(purSubPoVendor.getVendorIdx(),tmpLs);
                    }
                }

            }

            // 各个供应商的采购订单
            List<VendorPo> vendorPoList = new LinkedList<>();

            for(Long vendorIdx:vendorPoLineListMap.keySet()){
                VendorPo vendorPo  = new VendorPo();

                vendorPo.setVendorIdx(vendorIdx);
                vendorPo.setPurPoIdx(purPo.getIdx());
                vendorPo.setPurPoNo(purPo.getOrderNo());

                vendorPo.setPoLineList(vendorPoLineListMap.get(vendorIdx));

                vendorPoList.add(vendorPo);
            }


            // 推送各个供应商采购单到供应商端
            logger.warn("pushPurPo-start-invoke-batchReveiveVendorPo vendorPoList:{}", JSonUtils.toJson(vendorPoList));
            JsonResult  pushVendorPoRs = venderPoClient.batchReveiveVendorPo(userIdx,vendorPoList);
            if(pushVendorPoRs.isSucceeded()){
                logger.warn("pushPurPo-end-invoke-batchReveiveVendorPo vendorPoList:{} rs:{}", JSonUtils.toJson(vendorPoList),JSonUtils.toJson(pushVendorPoRs));
                purPo.setBizStatus(EPurPoBizStatus.E_PUSHED.getCode());
                this.updateById(purPo);
            }else{
                logger.error("pushPurPo-fail-invoke-batchReveiveVendorPo vendorPoList:{} rs:{}", JSonUtils.toJson(vendorPoList),JSonUtils.toJson(pushVendorPoRs));
            }
        }
    }
    /**
     *  提交审核
     * @param userIdx 用户Idx
     * @param purPo 采购单
     */
    public void commitReview(Long userIdx,PurPo purPo){

        purPo.setBizStatus(EPurPoBizStatus.E_WAIT_REVIEW.getCode());
        purPo.setUpdateByMemberIdx(userIdx);
        this.baseDao.updateById(purPo);
    }

    @Transactional
    public void deletePurPo(Long poIdx){

        this.purSubPoService.deleteByPoIdx(poIdx);

        Wrapper<PurPo> wrapper = new EntityWrapper<>();
        wrapper.eq("idx",poIdx);

        baseDao.delete(wrapper);
    }


    public Page<PurPo> listByFilterForPage(Page page, ParamListPurPo param){

        page.setRecords(baseDao.listByFilter(page,param));

        return page;
    }
}
