package cc.zhengcq.eagle.pur.model;

import java.math.BigDecimal;

import cc.zhengcq.eagle.core.db.json.CurrencyDeserializer;
import cc.zhengcq.eagle.core.db.json.CurrencySerializer;
import cc.zhengcq.eagle.core.db.json.CurrencyTotalDeserializer;
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
 * 采购子单供应商
 * </p>
 *
 * @author msyt
 * @since 2019-07-16
 */
@TableName("tb_pur_sub_po_vendor")
@Data
public class PurSubPoVendor extends BaseModel {

	/**
	 * 父idx
	 */
	@ApiModelProperty("父idx")
	@JsonSerialize(using= ToStringSerializer.class)
	@JsonDeserialize(using= IdWorkerDeserializer.class)
	private Long pidx;
	/**
	 * 采购单Id
	 */
	@TableField(value="po_idx")
	@ApiModelProperty("采购单Id")
	@JsonSerialize(using= ToStringSerializer.class)
	@JsonDeserialize(using= IdWorkerDeserializer.class)
	private Long poIdx;
	/**
	 * 子单idx
	 */
	@TableField(value="sub_po_idx")
	@ApiModelProperty("子单idx")
	@JsonSerialize(using= ToStringSerializer.class)
	@JsonDeserialize(using= IdWorkerDeserializer.class)
	private Long subPoIdx;
	/**
	 * 供应商idx
	 */
	@TableField(value="vendor_idx")
	@ApiModelProperty("供应商idx")
	@JsonSerialize(using= ToStringSerializer.class)
	@JsonDeserialize(using= IdWorkerDeserializer.class)
	private Long vendorIdx;
	/**
	 * 供货价
	 */
	@TableField(value="supply_price")
	@ApiModelProperty("供货价")
	@JsonSerialize(using= CurrencySerializer.class)
	@JsonDeserialize(using= CurrencyDeserializer.class)
	private Integer supplyPrice;
	/**
	 * 币种
	 */
	@ApiModelProperty("币种")
	private String currency;
	/**
	 * 可供库存
	 */
	@TableField(value="available_stock")
	@ApiModelProperty("可供库存")
	private Integer availableStock;
	/**
	 * 运期(天)
	 */
	@TableField(value="delivery_period")
	@ApiModelProperty("运期(天)")
	private Integer deliveryPeriod;
	/**
	 * 货运方式
	 */
	@TableField(value="ship_method")
	@ApiModelProperty("货运方式")
	private String shipMethod;
	/**
	 * 汇率
	 */
	@TableField(value="exchange_rate")
	@ApiModelProperty("汇率")
	private BigDecimal exchangeRate;
	/**
	 * 采购价
	 */
	@TableField(value="pur_price")
	@ApiModelProperty("采购价")
	@JsonSerialize(using= CurrencySerializer.class)
	@JsonDeserialize(using= CurrencyDeserializer.class)
	private Integer purPrice;
	/**
	 * 采购数量
	 */
	@TableField(value="qty_of_pur")
	@ApiModelProperty("采购数量")
	private Integer qtyOfPur;
	/**
	 * 联系人Id
	 */
	@TableField(value="contact_idx")
	@ApiModelProperty("联系人Id")
	@JsonSerialize(using= ToStringSerializer.class)
	@JsonDeserialize(using= IdWorkerDeserializer.class)
	private Long contactIdx;
	/**
	 * 联系人名称
	 */
	@TableField(value="contact_name")
	@ApiModelProperty("联系人名称")
	private String contactName;
	/**
	 * 联系人手机
	 */
	@TableField(value="contact_phone")
	@ApiModelProperty("联系人手机")
	private String contactPhone;
	/**
	 * 联系人邮箱
	 */
	@TableField(value="contact_email")
	@ApiModelProperty("联系人邮箱")
	private String contactEmail;

	@ApiModelProperty("金额(RMB)")
	@JsonSerialize(using= CurrencySerializer.class)
	@JsonDeserialize(using= CurrencyTotalDeserializer.class)
	private Long amt;
	/**
	 * 
	 */
	@TableField(value="biz_status")
	@ApiModelProperty("10 待确认 20:供应商有修改 30:商品部已修改 40:供应商已确认")
	private Integer bizStatus;

}
