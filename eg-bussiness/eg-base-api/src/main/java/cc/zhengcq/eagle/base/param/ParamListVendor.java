package cc.zhengcq.eagle.base.param;

import cc.zhengcq.eagle.core.db.json.IdWorkerDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ParamListVendor {

    @ApiModelProperty("供应商Id")
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long vendorIdx;

    @ApiModelProperty("供应商名称")
    private String vendorName;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("城市")
    private String city;
    @ApiModelProperty("商品Id")
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long itemIdx;

    @ApiModelProperty("忽略供应商Id列表")
    private List<String> ignoreVendorIdxs;
}
