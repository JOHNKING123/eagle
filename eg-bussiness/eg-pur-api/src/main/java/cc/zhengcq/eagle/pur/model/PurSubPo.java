package cc.zhengcq.eagle.pur.model;

import cc.zhengcq.eagle.core.db.json.CurrencyDeserializer;
import cc.zhengcq.eagle.core.db.json.CurrencySerializer;
import cc.zhengcq.eagle.core.db.json.CurrencyTotalDeserializer;
import cc.zhengcq.eagle.core.db.json.IdWorkerDeserializer;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import cc.zhengcq.eagle.core.db.base.BaseModel;

import java.util.List;


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
@TableName("tb_pur_sub_po")
@Data
public class PurSubPo extends BaseModel {

	/**
	 * 采购单Id
	 */
	@TableField(value="po_idx")
	@ApiModelProperty("采购单Id")
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
	 * 需求采购数
	 */
	@TableField(value="qty_of_require")
	@ApiModelProperty("需求采购数")
	private Integer qtyOfRequire;
	/**
	 * 距采购需求数量
	 */
	@TableField(value="qty_of_need")
	@ApiModelProperty("距采购需求数量")
	private Integer qtyOfNeed;
	/**
	 * 采购数量
	 */
	@TableField(value="qty_of_purchase")
	@ApiModelProperty("采购数量")
	private Integer qtyOfPurchase;
	/**
	 * 供应商数量
	 */
	@TableField(value="num_of_vendor")
	@ApiModelProperty("供应商数量")
	private Integer numOfVendor;
	/**
	 * 平均采购价
	 */
	@TableField(value="avg_pur_price")
	@ApiModelProperty("平均采购价")
	@JsonSerialize(using= CurrencySerializer.class)
	@JsonDeserialize(using= CurrencyDeserializer.class)
	private Integer avgPurPrice;
	/**
	 * 总运期
	 */
	@TableField(value="delivery_period")
	@ApiModelProperty("总运期")
	private Integer deliveryPeriod;
	@ApiModelProperty("子单金额总和(RMB)")
	@JsonSerialize(using= CurrencySerializer.class)
	@JsonDeserialize(using= CurrencyTotalDeserializer.class)
	private Long	subAmt;
	/**
	 * 
	 */
	@TableField(value="biz_status")
	@ApiModelProperty("10 待确认 20:供应商有修改 30:商品部已修改 40:供应商已确认")
	private Integer bizStatus;

	@TableField(exist = false)
	@ApiModelProperty("供应商列表")
	private List<PurSubPoVendor> subPoVendorList;

}
