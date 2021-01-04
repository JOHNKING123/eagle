package cc.msyt.eagle.pur.client;

import cc.msyt.eagle.core.server.config.OAuth2FeignConfig;
import cc.msyt.eagle.vendor.api.IVendorPoService;
import cc.msyt.eagle.vendor.api.VendorPoFallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "EG-VENDOR",fallbackFactory = VendorPoFallback.class,configuration = OAuth2FeignConfig.class)
public interface VenderPoClient  extends IVendorPoService {
}
