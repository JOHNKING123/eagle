package cc.zhengcq.eagle.pur.param;

import cc.zhengcq.eagle.core.db.json.IdWorkerDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ParamPushSubPoVendor {

    @ApiModelProperty("采购子单ID")
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long subPoIdx;
    @ApiModelProperty("供应商ID列表")
    private List<String>  vendorIdxs;
}
