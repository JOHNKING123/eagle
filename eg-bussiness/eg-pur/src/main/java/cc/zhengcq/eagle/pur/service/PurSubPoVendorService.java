package cc.zhengcq.eagle.pur.service;

import cc.zhengcq.eagle.core.common.enums.EStatus;
import cc.zhengcq.eagle.core.common.enums.pur.EPurPoBizStatus;
import cc.zhengcq.eagle.core.common.enums.pur.EPurSubPoBizStatus;
import cc.zhengcq.eagle.core.common.enums.pur.EPurSubPoVendorBizStatus;
import cc.zhengcq.eagle.core.common.utils.StringUtils;
import cc.zhengcq.eagle.pur.model.PurPo;
import cc.zhengcq.eagle.pur.model.PurSubPo;
import cc.zhengcq.eagle.pur.model.PurSubPoVendor;
import cc.zhengcq.eagle.pur.dao.PurSubPoVendorDao;
import cc.zhengcq.eagle.core.db.base.BaseServiceImpl;
import cc.zhengcq.eagle.pur.param.ParamRevVendorItemChg;
import cc.zhengcq.eagle.pur.param.ParamRevVendorItemConfirm;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  采购子单供应商 服务实现类
 * @author    msyt
 * @date 	  2019-07-16
 * @version   v1.0.0
 * @since     2019-07-16
 */
@Service
public class PurSubPoVendorService extends BaseServiceImpl<PurSubPoVendorDao, PurSubPoVendor> {

    @Autowired
    private PurSubPoService purSubPoService;
    @Autowired
    private PurPoService purPoService;

    public void deleteByPoIdx(Long poIdx){
        Wrapper<PurSubPoVendor> wrapper = new EntityWrapper<>();
        wrapper.eq("po_idx",poIdx);
        baseDao.delete(wrapper);
    }

    public void deleteBySubPoIdx(Long subPoIdx){
        Wrapper<PurSubPoVendor> wrapper = new EntityWrapper<>();
        wrapper.eq("sub_po_idx",subPoIdx);
        baseDao.delete(wrapper);
    }
    public void deleteByPidx(Long pidx){
        Wrapper<PurSubPoVendor> wrapper = new EntityWrapper<>();
        wrapper.eq("pidx",pidx);
        baseDao.delete(wrapper);
    }

    public PurSubPoVendor getChangeRecord(Long pidx){
        if(StringUtils.isZero(pidx)){
            return null;
        }
        Wrapper<PurSubPoVendor> wrapper = new EntityWrapper<>();
        wrapper.eq("pidx",pidx);
        wrapper.eq("status",EStatus.E_NOT_ACTIVE.getCode());

        List<PurSubPoVendor> ls = baseDao.selectList(wrapper);
        if(ls != null && !ls.isEmpty()){
            return ls.get(0);
        }

        return null;
    }
    public List<PurSubPoVendor>  getByPoIdx(Long poIdx){
        if(StringUtils.isZero(poIdx)) {
            return Collections.EMPTY_LIST;
        }
        Wrapper<PurSubPoVendor> wrapper = new EntityWrapper<>();
        wrapper.eq("status", EStatus.E_VALID.getCode());
        wrapper.eq("po_idx",poIdx);

        return baseDao.selectList(wrapper);
    }
    public List<PurSubPoVendor>  getBySubPoIdx(Long subPoIdx){
        if(StringUtils.isZero(subPoIdx)) {
            return Collections.EMPTY_LIST;
        }
        Wrapper<PurSubPoVendor> wrapper = new EntityWrapper<>();
        wrapper.eq("status", EStatus.E_VALID.getCode());
        wrapper.eq("sub_po_idx",subPoIdx);

        return baseDao.selectList(wrapper);
    }

    public PurSubPoVendor getBySubPoIdxAndVendorIdx(Long subPoIdx,Long vendorIdx){
        if(StringUtils.isZero(subPoIdx) || StringUtils.isZero(vendorIdx)) {
            return null;
        }
        Wrapper<PurSubPoVendor> wrapper = new EntityWrapper<>();
        wrapper.eq("status", EStatus.E_VALID.getCode());
        wrapper.eq("sub_po_idx",subPoIdx);
        wrapper.eq("vendor_idx",vendorIdx);

        List<PurSubPoVendor> ls = baseDao.selectList(wrapper);
        if(ls != null && !ls.isEmpty()){
            return ls.get(0);
        }

        return null;
    }

    public List<PurSubPoVendor> getBySubPoIdxAndVendorIdxs(Long subPoIdx,List<Long> vendorIdxs){
        if(StringUtils.isZero(subPoIdx) || vendorIdxs == null || vendorIdxs.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        Wrapper<PurSubPoVendor> wrapper = new EntityWrapper<>();
        wrapper.eq("status", EStatus.E_VALID.getCode());
        wrapper.eq("sub_po_idx",subPoIdx);
        wrapper.in("vendor_idx",vendorIdxs);

        List<PurSubPoVendor> ls = baseDao.selectList(wrapper);

        return ls;
    }

    /**
     *  接收供应商商品的修改
     * @param userIdx 用户Idx
     * @param purPo  采购订单
     * @param purSubPo 采购子单
     * @param purSubPoVendor 采购子单供应商信息
     * @param param 修改信息
     */
    @Transactional
    public void revVendorItemChg(Long userIdx,PurPo purPo, PurSubPo purSubPo, PurSubPoVendor purSubPoVendor, ParamRevVendorItemChg param){

        /*
            changeSubPoVendor(status:2)->curSubPoVendor(status:1)->historyRecord(status:0)
         */

        this.deleteByPidx(purSubPoVendor.getIdx());

        PurSubPoVendor changeSubPoVendor = new PurSubPoVendor();
        BeanUtils.copyProperties(purSubPoVendor,changeSubPoVendor);
        BeanUtils.copyProperties(param,changeSubPoVendor);
        changeSubPoVendor.setIdx(null);
        changeSubPoVendor.setUpdateByMemberIdx(userIdx);
        changeSubPoVendor.setStatus(EStatus.E_NOT_ACTIVE.getCode());
        changeSubPoVendor.setPidx(purSubPoVendor.getIdx());
        this.insert(changeSubPoVendor);

        purSubPoVendor.setBizStatus(EPurSubPoVendorBizStatus.E_VENDOR_CHANGED.getCode());
        purSubPoVendor.setUpdateByMemberIdx(userIdx);
        this.updateById(purSubPoVendor);


        purSubPo.setBizStatus(EPurSubPoBizStatus.E_VENDOR_CHANGED.getCode());
        purSubPo.setUpdateByMemberIdx(userIdx);
        purSubPoService.updateById(purSubPo);

    }
    /**
     *  应用供应商商品的修改
     * @param userIdx 用户Idx
     * @param purSubPoVendor 采购子单供应商信息
     */
    @Transactional
    public void applyVendorItemChg(Long userIdx,PurSubPoVendor purSubPoVendor){
        /*
            changeSubPoVendor(status:1)->curSubPoVendor(status:0)->historyRecord(status:0)
         */
        PurSubPoVendor changeRecord = getChangeRecord(purSubPoVendor.getIdx());
        changeRecord.setBizStatus(EPurSubPoVendorBizStatus.E_WAIT_CONFIRME.getCode());
        changeRecord.setStatus(EStatus.E_VALID.getCode());
        changeRecord.setUpdateByMemberIdx(userIdx);
        this.updateById(changeRecord);

        purSubPoVendor.setStatus(EStatus.E_INVALID.getCode());
        purSubPoVendor.setUpdateByMemberIdx(userIdx);
        this.updateById(purSubPoVendor);

        PurSubPo purSubPo = purSubPoService.selectById(purSubPoVendor.getSubPoIdx());
        List<PurSubPoVendor> subPoVendorList = this.getBySubPoIdx(purSubPo.getIdx());

        boolean hasChanagedRecord = false;
        for(PurSubPoVendor tmpVo:subPoVendorList){
            if(tmpVo.getBizStatus() == EPurSubPoVendorBizStatus.E_VENDOR_CHANGED.getCode()){
                hasChanagedRecord = true;
                break;
            }
        }
        if(hasChanagedRecord){
            purSubPo.setBizStatus(EPurSubPoBizStatus.E_VENDOR_CHANGED.getCode());
        }else{
            purSubPo.setBizStatus(EPurSubPoBizStatus.E_WAIT_CONFIRME.getCode());
        }
        purSubPoService.updateById(purSubPo);

    }
    /**
     *  接收供应商商品的确认
     * @param userIdx 用户Idx
     */
    @Transactional
    public void revVendorItemConfirm(Long userIdx, PurPo purPo, ParamRevVendorItemConfirm param){

        List<Long> itemIdxs = param.getItemIdxList().stream().map(vo->Long.valueOf(vo)).collect(Collectors.toList());
        List<PurSubPo> subPoList = this.purSubPoService.listByPoIdxAndItemIdxs(purPo.getIdx(),itemIdxs);
        for(PurSubPo subPo:subPoList) {
            PurSubPoVendor subPoVendor = this.getBySubPoIdxAndVendorIdx(subPo.getIdx(),param.getVendorIdx());
            if(subPoVendor != null){
                confirmSubPoVendor(userIdx,purPo,subPo,subPoVendor);
            }
        }

    }
    /**
     *  确认子单供应商
     * @param userIdx 用户Idx
     */
    public void confirmSubPoVendor(Long userIdx,PurPo purPo,PurSubPo purSubPo,PurSubPoVendor purSubPoVendor){

        purSubPoVendor.setBizStatus(EPurSubPoVendorBizStatus.E_VENDOR_CONFIRMED.getCode());
        purSubPoVendor.setUpdateByMemberIdx(userIdx);
        this.updateById(purSubPoVendor);


        List<PurSubPoVendor> subPoVendorList = this.getBySubPoIdx(purSubPo.getIdx());
        if(subPoVendorList != null && !subPoVendorList.isEmpty()){
            boolean  allVendorConfirmedFlag = true;  // 子单下面的所有供应商是否都已经确认
            for(PurSubPoVendor tmpVo:subPoVendorList){
                if(tmpVo.getBizStatus() != EPurSubPoVendorBizStatus.E_VENDOR_CONFIRMED.getCode()) {
                    allVendorConfirmedFlag = false;
                    break;
                }
            }
            //  如果子单下面的所有供应商都已经确认,将子单状态改为供应商已确认
            if(allVendorConfirmedFlag) {
                purSubPo.setBizStatus(EPurSubPoBizStatus.E_VENDOR_CONFIRMED.getCode());
                purSubPo.setUpdateByMemberIdx(userIdx);
                this.purSubPoService.updateById(purSubPo);

                List<PurSubPo> subPoList = this.purSubPoService.getByPoIdx(purPo.getIdx());
                if(subPoList != null && !subPoList.isEmpty()){
                    boolean  allSubPoConfirmedFlag = true;  // 订单下面所有子单是否都已经确认
                    for(PurSubPo tmpVo1:subPoList){
                        if(tmpVo1.getBizStatus() != EPurSubPoBizStatus.E_VENDOR_CONFIRMED.getCode()) {
                            allSubPoConfirmedFlag = false;
                            break;
                        }
                    }
                    //  如果订单下面所有子单都已经确认,将子单状态改为已确认
                    if(allSubPoConfirmedFlag){
                        purPo.setBizStatus(EPurPoBizStatus.E_CONFIRMED.getCode());
                        purPo.setUpdateByMemberIdx(userIdx);
                        purPoService.updateById(purPo);
                    }
                }

            } // end of if(allVendorConfirmedFlag)
        }// end of if(subPoVendorList != null && !subPoVendorList.isEmpty())


    }
}
