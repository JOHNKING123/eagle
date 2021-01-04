package cc.zhengcq.eagle.base.controller;

import cc.zhengcq.eagle.base.api.IBaseVendorService;
import cc.zhengcq.eagle.base.model.BaseVendor;
import cc.zhengcq.eagle.base.param.ParamListVendor;
import cc.zhengcq.eagle.base.service.BaseVendorService;
import cc.zhengcq.eagle.core.common.entity.JsonResult;
import cc.zhengcq.eagle.core.db.entity.Page;
import cc.zhengcq.eagle.core.db.entity.PageParam;
import cc.zhengcq.eagle.core.server.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  供应商控制器
 *  提供关于供应商信息的相关服务
 * @author    zhengcq
 * @date 	    2019-07-12
 * @version   v1.0.0
 * @since     2019-07-12
 */
@Api(tags = "供应商控制器")
@RestController
@RequestMapping("/api/eg-base/vendor")
public class BaseVendorController extends BaseController implements IBaseVendorService {

    @Autowired
    private BaseVendorService baseVendorService;

    /**
     * 保存供应商信息
     * @param userIdx  操作用户Id
     * @param baseVendor 供应商信息
     * @return  JsonResult<String>  供应商Id
     * @since v1.0
     */
    @ApiOperation("保存供应商信息")
    @PostMapping("/save-vendor")
    public JsonResult  saveVendor(@RequestParam("userIdx")Long userIdx,
                                  @RequestBody BaseVendor baseVendor) {

        baseVendorService.saveVendor(userIdx,baseVendor);

        return JsonResult.ok(baseVendor.getIdx_());
    }


    /**
     * 根据Id获取供应商信息
     * @param vendorId  供应商ID
     * @return  JsonResult<BaseVendor>
     * @since v1.0
     */
    @ApiOperation("根据Id获取供应商信息")
    @GetMapping("/get-by-id")
    public JsonResult<BaseVendor>  getById(@RequestParam("vendorId")Long vendorId) {

        BaseVendor baseVendor = baseVendorService.selectById(vendorId);

        return JsonResult.ok(baseVendor);
    }

    /**
     * 分页获取供应商信息
     * @param pageParam  分页查询参数
     * @return  JsonResult<Page<BaseVendor>>
     * @since v1.0
     */
    @ApiOperation("分页获取供应商信息")
    @PostMapping("/list-by-filter-for-page")
    public JsonResult<Page<BaseVendor>>  listByFilterForPage(@RequestBody PageParam<ParamListVendor> pageParam){

        Page<BaseVendor> page = this.baseVendorService.listByFilterForPage(pageParam.getPage(),pageParam.getParams());

        return JsonResult.ok(page);
    }

    /**
     * 根据条件获取供应商列表
     * @param param  查询参数
     * @return  JsonResult<List<BaseVendor>>
     * @since v1.0
     */
    @ApiOperation("根据条件获取供应商列表")
    @PostMapping("/list-by-filter")
    public JsonResult<List<BaseVendor>>  listByFilter(@RequestBody ParamListVendor param){

        List<BaseVendor> ls = this.baseVendorService.listByFilter(param);

        return JsonResult.ok(ls);
    }

}
