package cc.msyt.eagle.base.param;

import cc.msyt.eagle.core.db.json.IdWorkerDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ParamListVendorContact {

    @ApiModelProperty("供应商Id")
    @JsonSerialize(using= ToStringSerializer.class)
    @JsonDeserialize(using= IdWorkerDeserializer.class)
    private Long vendorIdx;

    @ApiModelProperty("联系人名称")
    private String contactName;
    @ApiModelProperty("手机")
    private String mobilePhone;
    @ApiModelProperty("邮箱")
    private String email;

}
