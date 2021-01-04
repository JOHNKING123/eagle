package cc.msyt.eagle.base.api;

import cc.msyt.eagle.base.model.BaseVendor;
import cc.msyt.eagle.core.common.entity.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface IBaseVendorService {
    String ROOT_PATH = "/api/eg-base/vendor";
    @GetMapping(value = ROOT_PATH+"/get-by-id")
    JsonResult<BaseVendor> getById(@RequestParam("vendorId")Long vendorId);


}
