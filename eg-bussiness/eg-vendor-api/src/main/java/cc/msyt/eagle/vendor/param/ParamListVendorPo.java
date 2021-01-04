package cc.msyt.eagle.vendor.param;

import cc.msyt.eagle.core.db.json.IdWorkerDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ParamListVendorPo {

    @ApiModelProperty("供应商采购订单ID")
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long poIdx;
    @ApiModelProperty("采购端采购单ID")
    private Long purPoIdx;

    @ApiModelProperty("开始时间 格式:2019-07-01 12:30:00")
    private String startTime;
    @ApiModelProperty("结束时间 格式:2019-07-01 12:30:00")
    private String endTime;

    @ApiModelProperty("状态")
    private Integer bizStatus;
}
