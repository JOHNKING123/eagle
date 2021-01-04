package cc.msyt.eagle.base.model;

import java.math.BigDecimal;

import cc.msyt.eagle.core.db.json.CurrencyDeserializer;
import cc.msyt.eagle.core.db.json.CurrencySerializer;
import cc.msyt.eagle.core.db.json.IdWorkerDeserializer;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import cc.msyt.eagle.core.db.base.BaseModel;
import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 供应商商品表
 * </p>
 *
 * @author msyt
 * @since 2019-07-15
 */
@TableName("tb_base_vendor_item")
@Data
public class BaseVendorItem extends BaseModel {

	/**
	 * 供应商Id
	 */
	@TableField(value="vendor_idx")
	@ApiModelProperty("供应商Id")
	@JsonSerialize(using= ToStringSerializer.class)
	@JsonDeserialize(using= IdWorkerDeserializer.class)
	private Long vendorIdx;
	/**
	 * 商品Id
	 */
	@TableField(value="item_idx")
	@ApiModelProperty("商品Id")
	private Long itemIdx;
	/**
	 * 商品货号
	 */
	@TableField(value="item_code")
	@ApiModelProperty("商品货号")
	private String itemCode;
	/**
	 * 商品名称
	 */
	@TableField(value="item_name")
	@ApiModelProperty("商品名称")
	private String itemName;
	/**
	 * 品牌
	 */
	@TableField(value="item_brand")
	@ApiModelProperty("品牌")
	private String itemBrand;
	/**
	 * 描述
	 */
	@TableField(value="item_desc")
	@ApiModelProperty("描述")
	private String itemDesc;
	/**
	 * 规格
	 */
	@TableField(value="item_spec")
	@ApiModelProperty("规格")
	private String itemSpec;
	/**
	 * 原产地
	 */
	@TableField(value="item_origin")
	@ApiModelProperty("原产地")
	private String itemOrigin;
	/**
	 * 条码
	 */
	@TableField(value="item_barcode")
	@ApiModelProperty("条码")
	private String itemBarcode;
	/**
	 * 箱规
	 */
	@TableField(value="number_of_pieces")
	@ApiModelProperty("箱规")
	private Integer numberOfPieces;
	/**
	 * 类目
	 */
	@TableField(value="item_category")
	@ApiModelProperty("类目")
	private String itemCategory;
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


	@ApiModelProperty("货运方式")
	private String shipMethod;
	@ApiModelProperty("失效日期")
	private Date expirationDate;
	@ApiModelProperty("保质期(天)")
	private Integer	shelfPeriod;
}
