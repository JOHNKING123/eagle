package cc.msyt.eagle.base.controller;

import cc.msyt.eagle.base.model.BaseVendor;
import cc.msyt.eagle.base.model.BaseVendorItem;
import cc.msyt.eagle.base.param.ParamListVendorItem;
import cc.msyt.eagle.base.service.BaseVendorItemService;
import cc.msyt.eagle.base.service.BaseVendorService;
import cc.msyt.eagle.core.common.entity.BatchOperateResult;
import cc.msyt.eagle.core.common.entity.BatchOperateResultDetail;
import cc.msyt.eagle.core.common.entity.JsonResult;
import cc.msyt.eagle.core.common.entity.ServiceCode;
import cc.msyt.eagle.core.common.enums.EStatus;
import cc.msyt.eagle.core.common.enums.ErrorCode;
import cc.msyt.eagle.core.common.utils.StringUtils;
import cc.msyt.eagle.core.db.entity.Page;
import cc.msyt.eagle.core.db.entity.PageParam;
import cc.msyt.eagle.core.server.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 *  供应商商品信息控制器
 *  提供关于供应商商品信息的相关服务
 * @author    zhengcq
 * @date 	    2019-07-12
 * @version   v1.0.0
 * @since     2019-07-12
 */
@Api(tags = "供应商商品信息控制器")
@RestController
@RequestMapping("/api/eg-base/vendor-item")
public class BaseVendorItemController extends BaseController {

    @Autowired
    private BaseVendorItemService baseVendorItemService;
    @Autowired
    private BaseVendorService baseVendorService;

    @ApiOperation("保存供应商商品")
    @PostMapping("/save-vendor-item")
    public JsonResult saveVendorItem(@RequestParam("userIdx")Long userIdx,
                                        @RequestBody BaseVendorItem vendorItem){

        if(StringUtils.isZero(vendorItem.getVendorIdx())){
            return JsonResult.failed(ErrorCode.E_INVALID_PARAMS);
        }
        // 判断供应商是否存在
        BaseVendor baseVendor = baseVendorService.selectById(vendorItem.getVendorIdx());
        if(baseVendor == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }
        // 如果是新增 ，判断供应商是否已经有了这个商品
        if(StringUtils.isZero(vendorItem.getIdx())) {
            BaseVendorItem vendorItemDb = this.baseVendorItemService.getByVendorIdAndItemId(baseVendor.getIdx(),vendorItem.getItemIdx());
            if(vendorItemDb != null){
                return JsonResult.failed(ErrorCode.E_EXISTED_RECORD);
            }
        }

        baseVendorItemService.saveVendorItem(userIdx,vendorItem);

        return JsonResult.ok(vendorItem.getIdx_());
    }


    @ApiOperation("批量保存供应商商品")
    @PostMapping("/batch-save-vendor-item")
    public JsonResult<BatchOperateResult> batchSaveVendorContact(@RequestParam("userIdx")Long userIdx,
                                                                 @RequestBody List<BaseVendorItem> baseVendorItemList){
        BatchOperateResult batchOperateResult = new BatchOperateResult();
        batchOperateResult.setTotalCount(baseVendorItemList.size());
        List<BatchOperateResultDetail> detailList = new LinkedList<>();
        int errorCount = 0;
        for(BaseVendorItem vendorItem:baseVendorItemList){
            BatchOperateResultDetail resultDetail = new BatchOperateResultDetail();
            JsonResult rs = saveVendorItem(userIdx,vendorItem);
            if(rs.isSucceeded()) {
                resultDetail.setResult(ServiceCode.OK);
                resultDetail.setMessage("操作成功");
            }else {
                resultDetail.setResult(rs.getErrcode());
                resultDetail.setMessage(rs.getMessage());
                resultDetail.setCode(vendorItem.getItemCode());
                errorCount++;
            }

            detailList.add(resultDetail);
        }
        batchOperateResult.setDetailList(detailList);
        batchOperateResult.setErrorCount(errorCount);
        return JsonResult.ok(batchOperateResult);
    }


    /**
     * 分页获取供应商商品信息
     * @param pageParam  分页查询参数
     * @return  JsonResult<Page<BaseVendorItem>>
     * @since v1.0
     */
    @ApiOperation("分页获取供应商商品信息")
    @PostMapping("/list-by-filter-for-page")
    public JsonResult<Page<BaseVendorItem>>  listByFilterForPage(@RequestBody PageParam<ParamListVendorItem> pageParam) {

        Page<BaseVendorItem> page = this.baseVendorItemService.listByFilterForPage(pageParam.getPage(),pageParam.getParams());

        return JsonResult.ok(page);
    }


}
