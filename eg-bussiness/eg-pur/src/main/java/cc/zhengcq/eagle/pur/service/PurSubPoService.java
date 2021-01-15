package cc.zhengcq.eagle.pur.service;

import cc.zhengcq.eagle.core.common.entity.JsonResult;
import cc.zhengcq.eagle.core.common.enums.EStatus;
import cc.zhengcq.eagle.core.common.utils.JSonUtils;
import cc.zhengcq.eagle.core.common.utils.StringUtils;
import cc.zhengcq.eagle.pur.client.VenderPoClient;
import cc.zhengcq.eagle.pur.model.PurPo;
import cc.zhengcq.eagle.pur.model.PurSubPo;
import cc.zhengcq.eagle.pur.dao.PurSubPoDao;
import cc.zhengcq.eagle.core.db.base.BaseServiceImpl;
import cc.zhengcq.eagle.pur.model.PurSubPoVendor;
import cc.zhengcq.eagle.pur.param.ParamPushSubPoVendor;
import cc.zhengcq.eagle.vendor.model.VendorPo;
import cc.zhengcq.eagle.vendor.model.VendorPoLine;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  采购子单 服务实现类
 * @author    msyt
 * @date 	  2019-07-16
 * @version   v1.0.0
 * @since     2019-07-16
 */
@Service
public class PurSubPoService extends BaseServiceImpl<PurSubPoDao, PurSubPo> {

    @Autowired
    private PurSubPoVendorService purSubPoVendorService;
    @Autowired
    private PurPoService purPoService;

    @Autowired
    private VenderPoClient venderPoClient;

    public void savePurSubPo(Long userIdx,PurSubPo purSubPo){

        if(!StringUtils.isZero(purSubPo.getIdx())) {
            purSubPo.setUpdateByMemberIdx(userIdx);
        }else{
            purSubPo.setQtyOfNeed(0);
            purSubPo.setNumOfVendor(0);
            purSubPo.setDeliveryPeriod(0);
            purSubPo.setSubAmt(0L);
            purSubPo.setCreateByMemberIdx(userIdx);
            purSubPo.setUpdateByMemberIdx(userIdx);
        }
        this.insertOrUpdate(purSubPo);



        if(purSubPo.getSubPoVendorList() != null
                && !purSubPo.getSubPoVendorList().isEmpty()) {
            Integer qtyOfPurchase = 0; // 采购数量
            Integer numOfVendor = 0; //供应商数量
            Integer deliveryPeriod = 0; // 总运期(运期最大值)
            Long subAmt = 0L; // 子单金额
            for(PurSubPoVendor subPoVendor:purSubPo.getSubPoVendorList()) {

                subPoVendor.setPoIdx(purSubPo.getPoIdx());
                subPoVendor.setSubPoIdx(purSubPo.getIdx());
                subPoVendor.setCreateByMemberIdx(userIdx);
                // 金额(RMB)
                BigDecimal qtyOfPutTmp = new BigDecimal(subPoVendor.getQtyOfPur());
                BigDecimal purPriceTmp = new BigDecimal(subPoVendor.getPurPrice());
                BigDecimal amtTmp = purPriceTmp.multiply(subPoVendor.getExchangeRate()).multiply(qtyOfPutTmp).setScale(2,BigDecimal.ROUND_HALF_UP);
                subPoVendor.setAmt(amtTmp.longValue());
                purSubPoVendorService.insertOrUpdate(subPoVendor);


                subAmt = subAmt + amtTmp.longValue();

                // 运期最大值
                if(subPoVendor.getDeliveryPeriod() >= deliveryPeriod) {
                    deliveryPeriod = subPoVendor.getDeliveryPeriod();
                }
                numOfVendor++;

                qtyOfPurchase = qtyOfPurchase + subPoVendor.getQtyOfPur();
            }
            purSubPo.setNumOfVendor(numOfVendor);
            purSubPo.setQtyOfPurchase(qtyOfPurchase);
            purSubPo.setDeliveryPeriod(deliveryPeriod);
            purSubPo.setSubAmt(subAmt);

            // 如果需求数大于采购数，设置距采购需求数量
            if(purSubPo.getQtyOfRequire() > purSubPo.getQtyOfPurchase()) {
                purSubPo.setQtyOfNeed(purSubPo.getQtyOfRequire()-purSubPo.getQtyOfPurchase());
            }else{
                purSubPo.setQtyOfNeed(0);
            }

            // 计算平均采购价格
            BigDecimal qtyOfPurchaseTmp = new BigDecimal(purSubPo.getQtyOfPurchase());
            BigDecimal subAmtTmp = new BigDecimal(purSubPo.getSubAmt());
            BigDecimal avgPurPriceTmp = subAmtTmp.divide(qtyOfPurchaseTmp,2,BigDecimal.ROUND_HALF_UP);
            purSubPo.setAvgPurPrice(avgPurPriceTmp.intValue());

            this.updateById(purSubPo);
        } // end of  if(purSubPo.getSubPoVendorList() != null...
    }


    public void pushSubPoVendor(Long userIdx, PurSubPo purSubPo, ParamPushSubPoVendor param){

        PurPo purPo = this.purPoService.selectById(purSubPo.getPoIdx());

        List<Long> vendorIdxList = param.getVendorIdxs().stream().map(vo->Long.valueOf(vo)).collect(Collectors.toList());
        List<PurSubPoVendor> subPoVendorList = this.purSubPoVendorService.getBySubPoIdxAndVendorIdxs(purSubPo.getIdx(),vendorIdxList);
        // 各个供应商的采购订单
        List<VendorPo> vendorPoList = new LinkedList<>();
        for(PurSubPoVendor subPoVendor:subPoVendorList){
            VendorPo vendorPo  = new VendorPo();

            vendorPo.setVendorIdx(subPoVendor.getVendorIdx());
            vendorPo.setPurPoIdx(purPo.getIdx());
            vendorPo.setPurPoNo(purPo.getOrderNo());
            vendorPoList.add(vendorPo);

            List<VendorPoLine> vendorPoLines = new LinkedList<>();


            VendorPoLine vendorPoLine = new VendorPoLine();
            BeanUtils.copyProperties(purSubPo,vendorPoLine);

            vendorPoLine.setIdx(null);
            vendorPoLine.setPoIdx(null);

            vendorPoLine.setSupplyPrice(subPoVendor.getSupplyPrice());
            vendorPoLine.setCurrency(subPoVendor.getCurrency());
            vendorPoLine.setAvailableStock(subPoVendor.getAvailableStock());
            vendorPoLine.setDeliveryPeriod(subPoVendor.getDeliveryPeriod());
            vendorPoLine.setShipMethod(subPoVendor.getShipMethod());
            vendorPoLine.setExchangeRate(subPoVendor.getExchangeRate());
            vendorPoLine.setPurPrice(subPoVendor.getPurPrice());
            vendorPoLine.setQtyOfPurchase(subPoVendor.getQtyOfPur());

            vendorPoLines.add(vendorPoLine);

            vendorPo.setPoLineList(vendorPoLines);
        }

        if(!vendorPoList.isEmpty()){
            //  重新推送各个供应商采购单到供应商端
            logger.warn("pushSubPoVendor-start-invoke-batchReveiveVendorPo vendorPoList:{}", JSonUtils.toJson(vendorPoList));
            JsonResult pushVendorPoRs = venderPoClient.batchReveiveVendorPo(userIdx,vendorPoList);
            if(pushVendorPoRs.isSucceeded()){
                logger.warn("pushSubPoVendor-end-invoke-batchReveiveVendorPo vendorPoList:{} rs:{}", JSonUtils.toJson(vendorPoList),JSonUtils.toJson(pushVendorPoRs));
            }else{
                logger.error("pushPurPo-fail-invoke-batchReveiveVendorPo vendorPoList:{} rs:{}", JSonUtils.toJson(vendorPoList),JSonUtils.toJson(pushVendorPoRs));
            }
        }

    }

    @Transactional
    public void deleteByPoIdx(Long poIdx){

        this.purSubPoVendorService.deleteByPoIdx(poIdx);

        QueryWrapper<PurSubPo> wrapper = new QueryWrapper<>();
        wrapper.eq("poIdx",poIdx);
        this.baseMapper.delete(wrapper);
    }

    @Transactional
    public void deletePurSubPo(Long subPoIdx){

        this.purSubPoVendorService.deleteBySubPoIdx(subPoIdx);

        this.deleteById(subPoIdx);
    }

    public List<PurSubPo> getByPoIdx(Long poIdx){
        if(StringUtils.isZero(poIdx)) {
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<PurSubPo> wrapper = new QueryWrapper<>();
        wrapper.eq("status", EStatus.E_VALID.getCode());
        wrapper.eq("po_idx",poIdx);

        return baseMapper.selectList(wrapper);
    }

    public PurSubPo getByPoIdxAndItemIdx(Long poIdx,Long itemIdx){
        if(StringUtils.isZero(poIdx) || StringUtils.isZero(itemIdx)) {
            return null;
        }
        QueryWrapper<PurSubPo> wrapper = new QueryWrapper<>();
        wrapper.eq("status", EStatus.E_VALID.getCode());
        wrapper.eq("po_idx",poIdx);
        wrapper.eq("item_idx",itemIdx);

       List<PurSubPo> ls = baseMapper.selectList(wrapper);
       if(ls != null && !ls.isEmpty()){
           return ls.get(0);
       }
       return null;
    }

    public List<PurSubPo> listByPoIdxAndItemIdxs(Long poIdx,List<Long> itemIdxs){
        if(StringUtils.isZero(poIdx) || itemIdxs == null || itemIdxs.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<PurSubPo> wrapper = new QueryWrapper<>();
        wrapper.eq("status", EStatus.E_VALID.getCode());
        wrapper.eq("po_idx",poIdx);
        wrapper.in("item_idx",itemIdxs);

        List<PurSubPo> ls = baseMapper.selectList(wrapper);

        return ls;
    }
}
