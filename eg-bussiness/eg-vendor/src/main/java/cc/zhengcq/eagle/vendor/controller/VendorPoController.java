package cc.zhengcq.eagle.vendor.controller;

import cc.zhengcq.eagle.core.common.entity.JsonResult;
import cc.zhengcq.eagle.core.common.enums.ErrorCode;
import cc.zhengcq.eagle.core.common.utils.JSonUtils;
import cc.zhengcq.eagle.core.db.entity.Page;
import cc.zhengcq.eagle.core.db.entity.PageParam;
import cc.zhengcq.eagle.core.server.base.BaseController;
import cc.zhengcq.eagle.vendor.api.IVendorPoService;
import cc.zhengcq.eagle.vendor.model.VendorPo;
import cc.zhengcq.eagle.vendor.model.VendorPoLine;
import cc.zhengcq.eagle.vendor.param.ParamListVendorPo;
import cc.zhengcq.eagle.vendor.service.VendorPoLineService;
import cc.zhengcq.eagle.vendor.service.VendorPoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *  供应商采购单控制器
 *  提供关于供应商采购单信息的相关服务
 * @author    zhengcq
 * @date 	    2019-07-16
 * @version   v1.0.0
 * @since     2019-07-16
 */
@Api(tags = "供应商采购单控制器")
@RestController
@RequestMapping("/api/eg-vendor/po")
public class VendorPoController  extends BaseController implements IVendorPoService {


    @Autowired
    private VendorPoService vendorPoService;

    @Autowired
    private VendorPoLineService vendorPoLineService;

    /**
     * 接收平台发来的采购单
     * @param userIdx  操作用户Id
     * @param vendorPo 采购订单信息
     * @return  JsonResult<String>  采购订单Id
     * @since v1.0
     */
    @ApiOperation("接收平台发来的采购单")
    @PostMapping("/reveive-vendor-po")
    public JsonResult reveiveVendorPo(@RequestParam("userIdx")Long userIdx,
                                      @RequestBody VendorPo vendorPo){

        this.vendorPoService.reveiveVendorPo(userIdx,vendorPo);
        return JsonResult.ok(vendorPo.getIdx_());
    }

    /**
     * 批量接收平台发来的采购单
     * @param userIdx  操作用户Id
     * @param vendorPoList 采购订单信息列表
     * @return  JsonResult<String>  采购订单Id
     * @since v1.0
     */
    @ApiOperation("接收平台发来的采购单")
    @PostMapping("/batch-reveive-vendor-po")
    public JsonResult   batchReveiveVendorPo(@RequestParam("userIdx")Long userIdx,
                                             @RequestBody List<VendorPo> vendorPoList){
        logger.warn("batchReveiveVendorPo-start vendorPoList:{}", JSonUtils.toJson(vendorPoList));
        for(VendorPo vendorPo:vendorPoList){

            reveiveVendorPo(userIdx,vendorPo);
        }

        return JsonResult.ok(true);
    }

    /**
     * 确认供应商采购单
     * @param userIdx  操作用户Id
     * @param poIdx 供应商采购单Idx
     * @return  JsonResult<String>  true/false
     * @since v1.0
     */
    @ApiOperation("确认供应商采购单")
    @PostMapping("/confirm-vendor-po")
    public JsonResult confirmVendorPo(@RequestParam("userIdx")Long userIdx,
                                      @RequestParam("poIdx")Long poIdx){

        VendorPo vendorPo = this.vendorPoService.selectById(poIdx);
        if(vendorPo == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        this.vendorPoService.confirmVendorPo(userIdx,vendorPo);

        return JsonResult.ok(true);
    }



    /**
     * 分页获取供应商采购单信息
     * @param pageParam  分页查询参数
     * @return  JsonResult<Page<VendorPo>>
     * @since v1.0
     */
    @ApiOperation("分页获取供应商采购单信息")
    @PostMapping("/list-by-filter-for-page")
    public JsonResult<Page<VendorPo>>  listByFilterForPage(@RequestBody PageParam<ParamListVendorPo> pageParam){

        Page<VendorPo> page = this.vendorPoService.listByFilterForPage(pageParam.getPage(),pageParam.getParams());

        return JsonResult.ok(page);
    }

    /**
     * 获取采购单详情
     * @param poIdx  供应商采购单Idx
     * @return  JsonResult<VendorPo>
     * @since v1.0
     */
    @ApiOperation("获取采购单详情")
    @PostMapping("/detail-by-idx")
    public JsonResult<VendorPo> detailByIdx(@RequestParam("poIdx")Long poIdx){

        VendorPo vendorPo = this.vendorPoService.selectById(poIdx);
        if(vendorPo == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        List<VendorPoLine> poLineList =  this.vendorPoLineService.getByPoIdx(poIdx);
        vendorPo.setPoLineList(poLineList);

        return JsonResult.ok(vendorPo);
    }

}
