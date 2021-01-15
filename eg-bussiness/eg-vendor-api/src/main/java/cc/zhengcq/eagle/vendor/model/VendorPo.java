package cc.zhengcq.eagle.vendor.model;

import cc.zhengcq.eagle.core.db.json.CurrencySerializer;
import cc.zhengcq.eagle.core.db.json.CurrencyTotalDeserializer;
import cc.zhengcq.eagle.core.db.json.IdWorkerDeserializer;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cc.zhengcq.eagle.core.db.base.BaseModel;

import java.util.List;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 供应商采购单
 * </p>
 *
 * @author msyt
 * @since 2019-07-16
 */
@TableName("tb_vendor_po")
@Data
public class VendorPo extends BaseModel {

	/**
	 * 原采购单Idx
	 */
	@TableField(value="pur_po_idx")
	@ApiModelProperty("原采购单Idx")
	@JsonSerialize(using= ToStringSerializer.class)
	@JsonDeserialize(using= IdWorkerDeserializer.class)
	private Long purPoIdx;
	/**
	 * 原采购单编号
	 */
	@TableField(value="pur_po_no")
	@ApiModelProperty("原采购单编号")
	private String purPoNo;
	/**
	 * 订单编号
	 */
	@TableField(value="order_no")
	@ApiModelProperty("订单编号")
	private String orderNo;
	/**
	 * 供应商idx
	 */
	@TableField(value="vendor_idx")
	@ApiModelProperty("供应商idx")
	@JsonSerialize(using= ToStringSerializer.class)
	@JsonDeserialize(using= IdWorkerDeserializer.class)
	private Long vendorIdx;
	/**
	 * 采购总数
	 */
	@TableField(value="num_of_pur_item")
	@ApiModelProperty("采购总数")
	private Integer numOfPurItem;
	/**
	 * 总金额(RMB)
	 */
	@TableField(value="total_amt")
	@ApiModelProperty("总金额(RMB)")
	@JsonSerialize(using= CurrencySerializer.class)
	@JsonDeserialize(using= CurrencyTotalDeserializer.class)
	private Long totalAmt;
	/**
	 *  
	 */
	@TableField(value="biz_status")
	@ApiModelProperty(" 10 待确认  40:已确认")
	private Integer bizStatus;

	@TableField(exist = false)
	@ApiModelProperty("订单明细列表")
	private List<VendorPoLine> poLineList;
}
