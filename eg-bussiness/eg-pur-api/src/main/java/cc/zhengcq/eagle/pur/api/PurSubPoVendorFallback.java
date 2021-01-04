package cc.zhengcq.eagle.pur.api;

import cc.zhengcq.eagle.core.common.entity.JsonResult;
import cc.zhengcq.eagle.pur.param.ParamRevVendorItemChg;
import cc.zhengcq.eagle.pur.param.ParamRevVendorItemConfirm;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class PurSubPoVendorFallback implements FallbackFactory<IPurSubPoVendorService> {
    @Override
    public IPurSubPoVendorService create(Throwable throwable) {
        return new IPurSubPoVendorService(){

            @Override
            public JsonResult revVendorItemChg(Long userIdx, ParamRevVendorItemChg param) {
                return JsonResult.failed("服务请求失败");
            }

            @Override
            public JsonResult revVendorItemConfirm(Long userIdx, ParamRevVendorItemConfirm param) {
                return JsonResult.failed("服务请求失败");
            }
        };
    }
}
