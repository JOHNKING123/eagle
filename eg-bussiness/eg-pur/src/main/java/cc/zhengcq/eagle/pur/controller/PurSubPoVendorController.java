package cc.zhengcq.eagle.pur.controller;

import cc.zhengcq.eagle.core.common.entity.JsonResult;
import cc.zhengcq.eagle.core.common.enums.ErrorCode;
import cc.zhengcq.eagle.core.common.enums.pur.EPurSubPoVendorBizStatus;
import cc.zhengcq.eagle.core.common.utils.StringUtils;
import cc.zhengcq.eagle.core.server.base.BaseController;
import cc.zhengcq.eagle.pur.api.IPurSubPoVendorService;
import cc.zhengcq.eagle.pur.model.PurPo;
import cc.zhengcq.eagle.pur.model.PurSubPo;
import cc.zhengcq.eagle.pur.model.PurSubPoVendor;
import cc.zhengcq.eagle.pur.param.ParamRevVendorItemChg;
import cc.zhengcq.eagle.pur.param.ParamRevVendorItemConfirm;
import cc.zhengcq.eagle.pur.service.PurPoService;
import cc.zhengcq.eagle.pur.service.PurSubPoService;
import cc.zhengcq.eagle.pur.service.PurSubPoVendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  采购子单供应商控制器
 *  提供关于采购子单信息的相关服务
 * @author    zhengcq
 * @date 	    2019-07-16
 * @version   v1.0.0
 * @since     2019-07-16
 */
@Api(tags = "采购子单供应商控制器")
@RestController
@RequestMapping("/api/eg-pur/sub-po-vendor")
public class PurSubPoVendorController extends BaseController implements IPurSubPoVendorService {

    @Autowired
    private PurSubPoVendorService purSubPoVendorService;

    @Autowired
    private PurPoService purPoService;

    @Autowired
    private PurSubPoService purSubPoService;

    /**
     * 接收供应商对商品的修改
     * @param userIdx  操作用户Id
     * @param param 修改信息
     * @since v1.0
     */
    @ApiOperation("接收供应商对商品的修改")
    @PostMapping("/rev-vendor-item-chg")
    public JsonResult  revVendorItemChg(@RequestParam("userIdx")Long userIdx,
                                        @RequestBody ParamRevVendorItemChg param){


        if(StringUtils.isZero(param.getPurPoIdx())
            || StringUtils.isZero(param.getItemIdx())
            || StringUtils.isZero(param.getVendorIdx())){
            return JsonResult.failed(ErrorCode.E_INVALID_PARAMS);
        }

        PurPo purPo = purPoService.selectById(param.getPurPoIdx());
        if(purPo == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        PurSubPo purSubPo = purSubPoService.getByPoIdxAndItemIdx(purPo.getIdx(),param.getItemIdx());
        if(purSubPo == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        PurSubPoVendor purSubPoVendor = purSubPoVendorService.getBySubPoIdxAndVendorIdx(purSubPo.getIdx(),param.getVendorIdx());
        if(purSubPoVendor == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        // 接收供应商的修改
        this.purSubPoVendorService.revVendorItemChg(userIdx,purPo,purSubPo,purSubPoVendor,param);

        return JsonResult.ok(true);
    }
    /**
     * 应用供应商的修改
     * @param userIdx  操作用户Id
     * @param subPoVendorIdx 子单供应商记录Idx
     * @since v1.0
     */
    @ApiOperation("应用供应商的修改")
    @PostMapping("/apply-vendor-item-chg")
    public JsonResult  applyVendorItemChg(@RequestParam("userIdx")Long userIdx,
                                           @RequestParam("subPoVendorIdx")Long subPoVendorIdx){

        PurSubPoVendor purSubPoVendor = purSubPoVendorService.selectById(subPoVendorIdx);
        if(purSubPoVendor == null){
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        if(purSubPoVendor.getBizStatus() != EPurSubPoVendorBizStatus.E_VENDOR_CHANGED.getCode()){
            return JsonResult.failed(ErrorCode.E_INVALID_STATUS);
        }

        // 应用供应商的修改
        this.purSubPoVendorService.applyVendorItemChg(userIdx,purSubPoVendor);

        return JsonResult.ok(true);
    }
    /**
     * 接收供应商对商品的确认
     * @param userIdx  操作用户Id
     * @param param 确认信息
     * @since v1.0
     */
    @ApiOperation("接收供应商对商品的确认")
    @PostMapping("/rev-vendor-item-confirm")
    public JsonResult revVendorItemConfirm(@RequestParam("userIdx")Long userIdx,
                                           @RequestBody ParamRevVendorItemConfirm param){
        if(StringUtils.isZero(param.getPoIdx()) || StringUtils.isZero(param.getVendorIdx())
            || param.getItemIdxList() == null || param.getItemIdxList().isEmpty()){
            return JsonResult.failed(ErrorCode.E_INVALID_PARAMS);
        }
        PurPo purPo = this.purPoService.selectById(param.getPoIdx());
        if(purPo == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        this.purSubPoVendorService.revVendorItemConfirm(userIdx,purPo,param);

        return JsonResult.ok(true);
    }

    /**
     * 获取商品供应商的改动
     * @param subPoVendorIdx 子单供应商记录Idx
     * @return JsonResult<PurSubPoVendor>
     * @since v1.0
     */
    @ApiOperation("获取商品供应商的改动")
    @PostMapping("/get-change-record")
    public JsonResult<PurSubPoVendor>  getChangeRecord(@RequestParam("subPoVendorIdx")Long subPoVendorIdx){

        PurSubPoVendor changeRecord = this.purSubPoVendorService.getChangeRecord(subPoVendorIdx);

        return JsonResult.ok(changeRecord);
    }

}
