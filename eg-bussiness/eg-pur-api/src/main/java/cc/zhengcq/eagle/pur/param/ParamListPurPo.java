package cc.zhengcq.eagle.pur.param;

import cc.zhengcq.eagle.core.db.json.IdWorkerDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ParamListPurPo {

    @ApiModelProperty("采购订单ID")
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long poIdx;

    @ApiModelProperty("开始时间 格式:2019-07-01 12:30:00")
    private String startTime;
    @ApiModelProperty("结束时间 格式:2019-07-01 12:30:00")
    private String endTime;

    @ApiModelProperty("状态")
    private Integer bizStatus;
}
