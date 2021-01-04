package cc.msyt.eagle.pur.client;

import cc.msyt.eagle.base.api.BaseVendorFallback;
import cc.msyt.eagle.base.api.IBaseVendorService;
import cc.msyt.eagle.core.server.config.OAuth2FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "EG-BASE",fallbackFactory = BaseVendorFallback.class,configuration = OAuth2FeignConfig.class)
public interface BaseVendorClient extends IBaseVendorService {
}
