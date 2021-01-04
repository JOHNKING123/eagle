package cc.zhengcq.eagle.pur.controller;

import cc.zhengcq.eagle.core.common.entity.JsonResult;
import cc.zhengcq.eagle.core.common.enums.ErrorCode;
import cc.zhengcq.eagle.core.common.utils.StringUtils;
import cc.zhengcq.eagle.core.server.base.BaseController;
import cc.zhengcq.eagle.pur.model.PurPo;
import cc.zhengcq.eagle.pur.model.PurSubPo;
import cc.zhengcq.eagle.pur.model.PurSubPoVendor;
import cc.zhengcq.eagle.pur.param.ParamPushSubPoVendor;
import cc.zhengcq.eagle.pur.service.PurPoService;
import cc.zhengcq.eagle.pur.service.PurSubPoService;
import cc.zhengcq.eagle.pur.service.PurSubPoVendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  采购子单控制器
 *  提供关于采购子单信息的相关服务
 * @author    zhengcq
 * @date 	    2019-07-16
 * @version   v1.0.0
 * @since     2019-07-16
 */
@Api(tags = "采购子单控制器")
@RestController
@RequestMapping("/api/eg-pur/sub-po")
public class PurSubPoController extends BaseController {

    @Autowired
    private PurSubPoService purSubPoService;

    @Autowired
    private PurPoService purPoService;

    @Autowired
    private PurSubPoVendorService purSubPoVendorService;

    /**
     * 保存采购子单信息
     * @param userIdx  操作用户Id
     * @param purSubPo 采购子单信息
     * @return  JsonResult<String>  采购子单Id
     * @since v1.0
     */
    @ApiOperation("保存采购子单信息")
    @PostMapping("/save-pur-sub-po")
    public JsonResult  savePurSubPo(@RequestParam("userIdx")Long userIdx,
                                    @RequestBody PurSubPo purSubPo){


        PurPo purPo = purPoService.selectById(purSubPo.getPoIdx());
        if(purPo == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }
        // 保存采购子弹
        purSubPoService.savePurSubPo(userIdx,purSubPo);
        //  重新计算采购单金额数量等信息
        purPoService.recalPo(userIdx,purPo);

        return JsonResult.ok(purSubPo.getIdx_());
    }

    /**
     *  重新推送供应商信息
     * @param userIdx  操作用户Id
     * @param param 推送参数
     * @return  JsonResult true/false
     * @since v1.0
     */
    @ApiOperation("重新推送供应商信息")
    @PostMapping("/push-sub-po-vendor")
    public JsonResult  pushSubPoVendor(@RequestParam("userIdx")Long userIdx,
                                       @RequestBody ParamPushSubPoVendor param){
        if(StringUtils.isZero(userIdx) || StringUtils.isZero(param.getSubPoIdx())
            || param.getVendorIdxs() == null || param.getVendorIdxs().isEmpty()){
            return JsonResult.failed(ErrorCode.E_INVALID_PARAMS);
        }
        PurSubPo purSubPo = this.purSubPoService.selectById(param.getSubPoIdx());
        if(purSubPo == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }
        this.purSubPoService.pushSubPoVendor(userIdx,purSubPo,param);
        return JsonResult.ok(true);
    }

    /**
     * 删除采购子单信息
     * @param userIdx 用户ID
     * @param subPoIdx 采购子单Id
     * @return  JsonResult<String>  采购子单Id
     * @since v1.0
     */
    @ApiOperation("删除采购子单信息")
    @PostMapping("/delete-pur-sub-po")
    public JsonResult  deletePurSubPo(@RequestParam("userIdx")Long userIdx,
                                   @RequestParam("poIdx")Long subPoIdx) {

        PurSubPo purSubPo = this.purSubPoService.selectById(subPoIdx);
        if(purSubPo == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }
        PurPo purPo = this.purPoService.selectById(purSubPo.getPoIdx());
        if(purPo == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        this.purSubPoService.deletePurSubPo(subPoIdx);

        //  重新计算采购单金额数量等信息
        purPoService.recalPo(userIdx,purPo);

        return JsonResult.ok(subPoIdx.toString());
    }



    /**
     * 获取采购子单信息
     * @param subPoIdx 采购子单Id
     * @return  JsonResult<PurSubPo>  采购子单详情
     * @since v1.0
     */
    @ApiOperation("获取采购订单信息")
    @GetMapping("detail-by-idx")
    public JsonResult<PurSubPo> detailByIdx(@RequestParam("subPoIdx")Long subPoIdx){

        PurSubPo purSubPo = purSubPoService.selectById(subPoIdx);
        if(purSubPo == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        List<PurSubPoVendor> subPoVendorList = purSubPoVendorService.getBySubPoIdx(subPoIdx);
        purSubPo.setSubPoVendorList(subPoVendorList);

        return JsonResult.ok(purSubPo);
    }
}
