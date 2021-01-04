package cc.zhengcq.eagle.vendor.controller;

import cc.zhengcq.eagle.core.common.entity.JsonResult;
import cc.zhengcq.eagle.core.common.enums.ErrorCode;
import cc.zhengcq.eagle.core.common.utils.StringUtils;
import cc.zhengcq.eagle.core.server.base.BaseController;
import cc.zhengcq.eagle.vendor.model.VendorPo;
import cc.zhengcq.eagle.vendor.model.VendorPoLine;
import cc.zhengcq.eagle.vendor.service.VendorPoLineService;
import cc.zhengcq.eagle.vendor.service.VendorPoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  供应商采购单明细控制器
 *  提供关于供应商采购单明细信息的相关服务
 * @author    zhengcq
 * @date 	    2019-07-16
 * @version   v1.0.0
 * @since     2019-07-16
 */
@Api(tags = "供应商采购单明细控制器")
@RestController
@RequestMapping("/api/eg-vendor/po-line")
public class VendorPoLineController extends BaseController {

    @Autowired
    private VendorPoLineService vendorPoLineService;
    @Autowired
    private VendorPoService vendorPoService;

    /**
     * 更新采购单明细
     * @param userIdx  操作用户Id
     * @param vendorPoLine 采购单明细
     * @return  JsonResult  true/false
     * @since v1.0
     */
    @ApiOperation("更新采购单明细")
    @PostMapping("/update-vendor-po-line")
    public JsonResult updateVendorPoLine(@RequestParam("userIdx")Long userIdx,
                                         @RequestBody VendorPoLine vendorPoLine){
        if(StringUtils.isZero(userIdx) || StringUtils.isZero(vendorPoLine.getPoIdx())
            || StringUtils.isZero(vendorPoLine.getIdx())){
            return JsonResult.failed(ErrorCode.E_INVALID_PARAMS);
        }

        VendorPoLine vendorPoLineDb = this.vendorPoLineService.selectById(vendorPoLine.getIdx());
        if(vendorPoLineDb == null){
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        vendorPoLine.setUpdateByMemberIdx(userIdx);
        this.vendorPoLineService.updateById(vendorPoLine);

        return JsonResult.ok(true);
    }

    /**
     * 推送订单明细修改到采购端
     * @param userIdx  操作用户Id
     * @param poLineIdx 采购单明细Idx
     * @return  JsonResult  true/false
     * @since v1.0
     */
    @ApiOperation("推送订单明细修改到采购端")
    @PostMapping("/push-line-chg-2-pur")
    public JsonResult  pushLineChg2Pur(@RequestParam("userIdx")Long userIdx,
                                       @RequestParam("poLineIdx")Long poLineIdx){
        if(StringUtils.isZero(userIdx) || StringUtils.isZero(poLineIdx)){
            return JsonResult.failed(ErrorCode.E_INVALID_PARAMS);
        }

        VendorPoLine vendorPoLine = this.vendorPoLineService.selectById(poLineIdx);
        if(vendorPoLine == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        VendorPo vendorPo = vendorPoService.selectById(vendorPoLine.getPoIdx());
        if(vendorPo == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        return this.vendorPoLineService.pushLineChg2Pur(userIdx,vendorPo,vendorPoLine);
    }
}
