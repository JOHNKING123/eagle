package cc.msyt.eagle.base.controller;

import cc.msyt.eagle.base.model.BaseVendor;
import cc.msyt.eagle.base.model.BaseVendorContact;
import cc.msyt.eagle.base.param.ParamListVendorContact;
import cc.msyt.eagle.base.service.BaseVendorContactService;
import cc.msyt.eagle.base.service.BaseVendorService;
import cc.msyt.eagle.core.common.entity.BatchOperateResult;
import cc.msyt.eagle.core.common.entity.BatchOperateResultDetail;
import cc.msyt.eagle.core.common.entity.JsonResult;
import cc.msyt.eagle.core.common.entity.ServiceCode;
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
 *  供应商联系人信息控制器
 *  提供关于供应商联系人信息的相关服务
 * @author    zhengcq
 * @date 	    2019-07-12
 * @version   v1.0.0
 * @since     2019-07-12
 */
@Api(tags = "供应商联系人信息控制器")
@RestController
@RequestMapping("/api/eg-base/vendor-contact")
public class BaseVendorContactController extends BaseController {

    @Autowired
    private BaseVendorContactService baseVendorContactService;
    @Autowired
    private BaseVendorService baseVendorService;

    @ApiOperation("保存供应商联系人")
    @PostMapping("/save-vendor-contact")
    public JsonResult saveVendorContact(@RequestParam("userIdx")Long userIdx,
                                 @RequestBody BaseVendorContact vendorContact){

        if(StringUtils.isZero(vendorContact.getVendorIdx())){
            return JsonResult.failed(ErrorCode.E_INVALID_PARAMS);
        }
        // 判断供应商是否存在
        BaseVendor baseVendor = baseVendorService.selectById(vendorContact.getVendorIdx());
        if(baseVendor == null) {
            return JsonResult.failed(ErrorCode.E_NOT_EXIST_RECORD);
        }

        baseVendorContactService.saveVendorContact(userIdx,vendorContact);

        return JsonResult.ok(vendorContact.getIdx_());
    }


    @ApiOperation("批量保存联系人")
    @PostMapping("/batch-save-vendor-contact")
    public JsonResult<BatchOperateResult> batchSaveVendorContact(@RequestParam("userIdx")Long userIdx,
                                                          @RequestBody List<BaseVendorContact> vendorContactList){
        BatchOperateResult batchOperateResult = new BatchOperateResult();
        batchOperateResult.setTotalCount(vendorContactList.size());
        List<BatchOperateResultDetail> detailList = new LinkedList<>();
        int errorCount = 0;
        for(BaseVendorContact vendorContact:vendorContactList){
            BatchOperateResultDetail resultDetail = new BatchOperateResultDetail();
            JsonResult rs = saveVendorContact(userIdx,vendorContact);
            if(rs.isSucceeded()) {
                resultDetail.setResult(ServiceCode.OK);
                resultDetail.setMessage("操作成功");
            }else {
                resultDetail.setResult(rs.getErrcode());
                resultDetail.setMessage(rs.getMessage());
                resultDetail.setCode(vendorContact.getContactName());
                errorCount++;
            }

            detailList.add(resultDetail);
        }
        batchOperateResult.setDetailList(detailList);
        batchOperateResult.setErrorCount(errorCount);
        return JsonResult.ok(batchOperateResult);
    }



    /**
     * 分页获取供应商联系人信息
     * @param pageParam  分页查询参数
     * @return  JsonResult<Page<BaseVendorContact>>
     * @since v1.0
     */
    @ApiOperation("分页获取供应商联系人信息")
    @PostMapping("/list-by-filter-for-page")
    public JsonResult<Page<BaseVendorContact>>  listByFilterForPage(@RequestBody PageParam<ParamListVendorContact> pageParam) {

        Page<BaseVendorContact> page = this.baseVendorContactService.listByFilterForPage(pageParam.getPage(),pageParam.getParams());

        return JsonResult.ok(page);
    }

}
