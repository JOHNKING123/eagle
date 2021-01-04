package cc.msyt.eagle.base.api;

import cc.msyt.eagle.base.model.BaseVendor;
import cc.msyt.eagle.core.common.entity.JsonResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class BaseVendorFallback implements FallbackFactory<IBaseVendorService> {
    @Override
    public IBaseVendorService create(Throwable throwable) {
        return new IBaseVendorService() {
            @Override
            public JsonResult<BaseVendor> getById(Long vendorId) {
                return JsonResult.failed("服务请求失败");
            }
        };
    }
}
