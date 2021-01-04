package cc.msyt.eagle.vendor.api;

import cc.msyt.eagle.core.common.entity.JsonResult;
import cc.msyt.eagle.vendor.model.VendorPo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IVendorPoService {
    String ROOT_PATH = "/api/eg-vendor/po";

    @PostMapping(value = ROOT_PATH+"/reveive-vendor-po")
    JsonResult reveiveVendorPo(@RequestParam("userIdx")Long userIdx,
                                      @RequestBody VendorPo vendorPo);


    @PostMapping(value = ROOT_PATH+"/batch-reveive-vendor-po")
    JsonResult   batchReveiveVendorPo(@RequestParam("userIdx")Long userIdx,
                                             @RequestBody List<VendorPo> vendorPoList);
}
