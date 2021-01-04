package cc.msyt.eagle.base.param;

import cc.msyt.eagle.core.db.json.IdWorkerDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ParamListVendorItem {

    @ApiModelProperty("供应商Id")
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long vendorIdx;

    @ApiModelProperty("商品Id")
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long itemIdx;
    @ApiModelProperty("商品货号")
    private String itemCode;
    @ApiModelProperty("商品名称")
    private String itemName;
    @ApiModelProperty("商品品牌")
    private String itemBrand;
    @ApiModelProperty("商品品类")
    private String itemCategory;


}
