package cc.msyt.eagle.base.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import cc.msyt.eagle.core.db.base.BaseModel;
import java.io.Serializable;
import java.util.List;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 供应商
 * </p>
 *
 * @author msyt
 * @since 2019-07-15
 */
@TableName("tb_base_vendor")
@Data
public class BaseVendor extends BaseModel {

	/**
	 * 供应商名称
	 */
	@TableField(value="vendor_name")
	@ApiModelProperty("供应商名称")
	private String vendorName;
	/**
	 * 国家
	 */
	@ApiModelProperty("国家")
	private String country;
	/**
	 * 城市
	 */
	@ApiModelProperty("城市")
	private String city;
	/**
	 * 地址
	 */
	@ApiModelProperty("地址")
	private String address;

	@ApiModelProperty("联系人列表")
	@TableField(exist = false)
	private List<BaseVendorContact> vendorContactList;



}
