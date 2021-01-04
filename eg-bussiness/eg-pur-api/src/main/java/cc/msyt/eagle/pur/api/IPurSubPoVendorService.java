package cc.msyt.eagle.pur.api;

import cc.msyt.eagle.core.common.entity.JsonResult;
import cc.msyt.eagle.pur.param.ParamRevVendorItemChg;
import cc.msyt.eagle.pur.param.ParamRevVendorItemConfirm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IPurSubPoVendorService {
    String ROOT_PATH = "/api/eg-pur/sub-po-vendor";

    @PostMapping(value = ROOT_PATH+"/rev-vendor-item-chg")
    JsonResult revVendorItemChg(@RequestParam("userIdx")Long userIdx,
                                       @RequestBody ParamRevVendorItemChg param);

    @PostMapping(value = ROOT_PATH+"/rev-vendor-item-confirm")
    JsonResult revVendorItemConfirm(@RequestParam("userIdx")Long userIdx,
                                           @RequestBody ParamRevVendorItemConfirm param);
}
