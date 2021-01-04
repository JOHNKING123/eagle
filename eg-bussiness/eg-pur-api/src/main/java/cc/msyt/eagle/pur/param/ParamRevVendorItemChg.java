package cc.msyt.eagle.pur.param;

import cc.msyt.eagle.core.db.json.CurrencyDeserializer;
import cc.msyt.eagle.core.db.json.CurrencySerializer;
import cc.msyt.eagle.core.db.json.IdWorkerDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParamRevVendorItemChg {

    @ApiModelProperty("原采购单Idx")
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long purPoIdx;
    @ApiModelProperty("供应商idx")
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long vendorIdx;
    @ApiModelProperty("商品Id")
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long itemIdx;
    private Integer availableStock;
    @ApiModelProperty("运期(天)")
    private Integer deliveryPeriod;
    @ApiModelProperty("货运方式")
    private String shipMethod;
    @ApiModelProperty("供货价")
    @JsonSerialize(using= CurrencySerializer.class)
    @JsonDeserialize(using= CurrencyDeserializer.class)
    private Integer supplyPrice;
}
