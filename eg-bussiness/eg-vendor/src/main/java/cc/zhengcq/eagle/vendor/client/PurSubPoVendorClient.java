package cc.zhengcq.eagle.vendor.client;

import cc.zhengcq.eagle.core.server.config.OAuth2FeignConfig;
import cc.zhengcq.eagle.pur.api.IPurSubPoVendorService;
import cc.zhengcq.eagle.pur.api.PurSubPoVendorFallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "EG-PUR",fallbackFactory = PurSubPoVendorFallback.class,configuration = OAuth2FeignConfig.class)
public interface PurSubPoVendorClient extends IPurSubPoVendorService {
}
