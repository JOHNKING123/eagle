package cc.msyt.eagle.vendor.api;

import cc.msyt.eagle.core.common.entity.JsonResult;
import cc.msyt.eagle.vendor.model.VendorPo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VendorPoFallback implements FallbackFactory<IVendorPoService> {
    @Override
    public IVendorPoService create(Throwable throwable) {
        return new IVendorPoService() {
            @Override
            public JsonResult reveiveVendorPo(Long userIdx, VendorPo vendorPo) {
                return JsonResult.failed("服务请求失败");
            }

            @Override
            public JsonResult batchReveiveVendorPo(Long userIdx, List<VendorPo> vendorPoList) {
                return JsonResult.failed("服务请求失败");
            }
        };
    }
}
