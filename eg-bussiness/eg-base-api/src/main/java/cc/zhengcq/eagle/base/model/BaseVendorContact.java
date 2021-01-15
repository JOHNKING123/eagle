package cc.zhengcq.eagle.base.model;

import cc.zhengcq.eagle.core.db.json.IdWorkerDeserializer;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cc.zhengcq.eagle.core.db.base.BaseModel;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 供应商联系人
 * </p>
 *
 * @author msyt
 * @since 2019-07-15
 */
@TableName("tb_base_vendor_contact")
@Data
public class BaseVendorContact extends BaseModel {

	/**
	 * 供应商Id
	 */
	@TableField(value="vendor_idx")
	@ApiModelProperty("供应商Idx")
	@JsonSerialize(using= ToStringSerializer.class)
	@JsonDeserialize(using= IdWorkerDeserializer.class)
	private Long vendorIdx;
	/**
	 * 联系人名称
	 */
	@TableField(value="contact_name")
	@ApiModelProperty("联系人名称")
	private String contactName;
	/**
	 * 手机
	 */
	@TableField(value="mobile_phone")
	@ApiModelProperty("手机")
	private String mobilePhone;
	/**
	 * 邮箱
	 */
	@ApiModelProperty("邮箱")
	private String email;

}
