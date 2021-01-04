package cc.zhengcq.eagle.pur.controller;

import cc.zhengcq.eagle.core.common.entity.JsonResult;
import cc.zhengcq.eagle.core.common.enums.ErrorCode;
import cc.zhengcq.eagle.core.common.enums.pur.EPurPoBizStatus;
import cc.zhengcq.eagle.core.common.utils.StringUtils;
import cc.zhengcq.eagle.core.db.entity.Page;
import cc.zhengcq.eagle.core.db.entity.PageParam;
import cc.zhengcq.eagle.core.server.base.BaseController;
import cc.zhengcq.eagle.pur.model.PurPo;
import cc.zhengcq.eagle.pur.model.PurSubPo;
import cc.zhengcq.eagle.pur.param.ParamListPurPo;
import cc.zhengcq.eagle.pur.service.PurPoService;
import cc.zhengcq.eagle.pur.service.PurSubPoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  采购单控制器
 *  提供关于采购单信息的相关服务
 * @author    zhengcq
 * @date 	    2019-07-16
 * @version   v1.0.0
 * @since     2019-07-16
 */
@Api(tags = "采购单控制器")
@RestController
@RequestMapping("/api/eg-pur/po")
public class PurPoController extends BaseController {

    @Autowired
    private PurPoService    purPoService;

    @Autowired
    private PurSubPoService purSubPoService;


    /**
     * 生成采购订单信息
     * @param userIdx  操作用户Id
     * @param purPo 采购订单信息
     * @return  JsonResult<String>  采购订单Id
     * @since v1.0
     */
    @ApiOperation("生成采购订单信息")
    @PostMapping("/generate-pur-po")
    @Transactional
    public JsonResult  savePurPo(@RequestParam("userIdx")Long userIdx,
                                 @RequestBody PurPo purPo){

        if(!StringUtils.isZero(purPo.getIdx())) {
            return JsonResult.failed(ErrorCode.E_INVALID_PARAMS);
        }
        // 保存采购订单信息
        purPoService.savePo(userIdx,purPo);

        //推送采购单到供应商端
        purPoService.pushPurPo(userIdx,purPo.getIdx());

        return JsonResult.ok(purPo.getIdx_());
    }

    /**
     * 推送采购订单信息
     * @param userIdx  操作用户Id
     * @param poIdx 采购订单Id
     * @return  JsonResult true/false
     * @since v1.0
     */
    @ApiOperation("推送采购订单信息")
    @PostMapping("/push-pur-po")
    public JsonResult  pushPurPo(@RequestParam("userIdx")Long userIdx,
                                 @RequestParam("poIdx")Long poIdx){
        PurPo purPo = this.purPoService.selectById(poIdx);
        if(purPo == null){
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }
        if(purPo.getBizStatus() != EPurPoBizStatus.E_WAIT_PUSH.getCode()){
            return JsonResult.failed(ErrorCode.E_INVALID_STATUS);
        }

        purPoService.pushPurPo(userIdx,poIdx);

        return JsonResult.ok(true);
    }

    /**
     * 获取采购订单信息
     * @param poIdx 采购订单Id
     * @return  JsonResult<PurPo>  采购订单详情
     * @since v1.0
     */
    @ApiOperation("获取采购订单信息")
    @PostMapping("/detail-by-id")
    public JsonResult<PurPo>  detailById(@RequestParam("poIdx")Long poIdx){

        PurPo purPo = purPoService.selectById(poIdx);

        if(purPo == null){
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        List<PurSubPo> subPoList = purSubPoService.getByPoIdx(poIdx);

        purPo.setSubPoList(subPoList);
        return JsonResult.ok(purPo);
    }

    /**
     * 删除采购订单信息
     * @param userIdx 用户ID
     * @param poIdx 采购订单Id
     * @return  JsonResult<String>  采购订单Id
     * @since v1.0
     */
    @ApiOperation("删除采购订单信息")
    @PostMapping("/delete-pur-po")
    public JsonResult  deletePurPo(@RequestParam("userIdx")Long userIdx,
                                   @RequestParam("poIdx")Long poIdx) {

        PurPo purPo = this.purPoService.selectById(poIdx);
        if(purPo == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }
        if(purPo.getBizStatus() != EPurPoBizStatus.E_WAIT_PUSH.getCode()){
            return JsonResult.failed(ErrorCode.E_INVALID_STATUS);
        }

        this.purPoService.deletePurPo(poIdx);

        return JsonResult.ok(poIdx.toString());
    }

    /**
     * 提交审核
     * @param userIdx 用户ID
     * @param poIdx 采购订单Id
     * @return  JsonResult<String>  true/false
     * @since v1.0
     */
    @ApiOperation("提交审核")
    @PostMapping("/commit-review")
    public JsonResult  commitReview(@RequestParam("userIdx")Long userIdx,
                                    @RequestParam("poIdx")Long poIdx){

        PurPo purPo = purPoService.selectById(poIdx);
        if(purPo == null){
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }
        if(purPo.getBizStatus() != EPurPoBizStatus.E_CONFIRMED.getCode()) {
            return JsonResult.failed(ErrorCode.E_INVALID_STATUS);
        }

        this.purPoService.commitReview(userIdx,purPo);
        return JsonResult.ok(true);
    }

    /**
     * 分页获取采购单信息
     * @param pageParam  分页查询参数
     * @return  JsonResult<Page<PurPo>>
     * @since v1.0
     */
    @ApiOperation("分页获取采购单信息")
    @PostMapping("/list-by-filter-for-page")
    public JsonResult<Page<PurPo>>  listByFilterForPage(@RequestBody PageParam<ParamListPurPo> pageParam){

        Page<PurPo> page = this.purPoService.listByFilterForPage(pageParam.getPage(),pageParam.getParams());

        return JsonResult.ok(page);
    }

}
