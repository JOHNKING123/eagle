package cc.zhengcq.eagle.vendor.model;

import java.math.BigDecimal;

import cc.zhengcq.eagle.core.db.json.CurrencyDeserializer;
import cc.zhengcq.eagle.core.db.json.CurrencySerializer;
import cc.zhengcq.eagle.core.db.json.CurrencyTotalDeserializer;
import cc.zhengcq.eagle.core.db.json.IdWorkerDeserializer;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import cc.zhengcq.eagle.core.db.base.BaseModel;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 采购子单
 * </p>
 *
 * @author msyt
 * @since 2019-07-16
 */
@TableName("tb_vendor_po_line")
@Data
public class VendorPoLine extends BaseModel {

	/**
	 * 供应商采购单Id
	 */
	@TableField(value="po_idx")
	@ApiModelProperty("供应商采购单Id")
	@JsonSerialize(using= ToStringSerializer.class)
	@JsonDeserialize(using= IdWorkerDeserializer.class)
	private Long poIdx;
	/**
	 * 商品Id
	 */
	@TableField(value="item_idx")
	@ApiModelProperty("商品Id")
	@JsonSerialize(using= ToStringSerializer.class)
	@JsonDeserialize(using= IdWorkerDeserializer.class)
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
	 * 描述
	 */
	@TableField(value="item_desc")
	@ApiModelProperty("描述")
	private String itemDesc;
	/**
	 * 品牌
	 */
	@TableField(value="item_brand")
	@ApiModelProperty("品牌")
	private String itemBrand;
	/**
	 * 条码
	 */
	@TableField(value="item_barcode")
	@ApiModelProperty("条码")
	private String itemBarcode;
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
	 * 采购数量
	 */
	@TableField(value="qty_of_purchase")
	@ApiModelProperty("采购数量")
	private Integer qtyOfPurchase;
	/**
	 * 采购价
	 */
	@TableField(value="pur_price")
	@ApiModelProperty("采购价")
	@JsonSerialize(using= CurrencySerializer.class)
	@JsonDeserialize(using= CurrencyDeserializer.class)
	private Integer purPrice;
	@ApiModelProperty("采购金额")
	@JsonSerialize(using= CurrencySerializer.class)
	@JsonDeserialize(using= CurrencyTotalDeserializer.class)
	private Long	amt;
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
	 * 供货价
	 */
	@TableField(value="supply_price")
	@ApiModelProperty("供货价")
	@JsonSerialize(using= CurrencySerializer.class)
	@JsonDeserialize(using= CurrencyDeserializer.class)
	private Integer supplyPrice;
	/**
	 * 
	 */
	@TableField(value="biz_status")
	@ApiModelProperty("")
	private Integer bizStatus;

}
