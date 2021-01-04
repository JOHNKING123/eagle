package cc.zhengcq.eagle.pur.client;

import cc.zhengcq.eagle.core.server.config.OAuth2FeignConfig;
import cc.zhengcq.eagle.vendor.api.IVendorPoService;
import cc.zhengcq.eagle.vendor.api.VendorPoFallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "EG-VENDOR",fallbackFactory = VendorPoFallback.class,configuration = OAuth2FeignConfig.class)
public interface VenderPoClient  extends IVendorPoService {
}
