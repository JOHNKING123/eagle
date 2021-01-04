package cc.msyt.eagle.vendor.client;

import cc.msyt.eagle.core.server.config.OAuth2FeignConfig;
import cc.msyt.eagle.pur.api.IPurSubPoVendorService;
import cc.msyt.eagle.pur.api.PurSubPoVendorFallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "EG-PUR",fallbackFactory = PurSubPoVendorFallback.class,configuration = OAuth2FeignConfig.class)
public interface PurSubPoVendorClient extends IPurSubPoVendorService {
}
