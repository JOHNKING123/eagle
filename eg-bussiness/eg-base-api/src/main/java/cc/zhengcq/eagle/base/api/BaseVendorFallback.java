package cc.zhengcq.eagle.base.api;

import cc.zhengcq.eagle.base.model.BaseVendor;
import cc.zhengcq.eagle.core.common.entity.JsonResult;
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
