package cc.zhengcq.eagle.pur.param;

import cc.zhengcq.eagle.core.db.json.IdWorkerDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ParamRevVendorItemConfirm {

    @ApiModelProperty("采购单Idx")
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long poIdx;
    @ApiModelProperty("供应商Idx")
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long vendorIdx;

    @ApiModelProperty("商品Idx列表")
    private List<String> itemIdxList;
}
