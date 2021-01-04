package cc.zhengcq.eagle.pur.client;

import cc.zhengcq.eagle.base.api.BaseVendorFallback;
import cc.zhengcq.eagle.base.api.IBaseVendorService;
import cc.zhengcq.eagle.core.server.config.OAuth2FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "EG-BASE",fallbackFactory = BaseVendorFallback.class,configuration = OAuth2FeignConfig.class)
public interface BaseVendorClient extends IBaseVendorService {
}
