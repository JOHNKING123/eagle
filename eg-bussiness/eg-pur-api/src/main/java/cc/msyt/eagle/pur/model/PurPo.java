package cc.msyt.eagle.pur.model;

import java.math.BigDecimal;

import cc.msyt.eagle.core.db.json.CurrencyDeserializer;
import cc.msyt.eagle.core.db.json.CurrencySerializer;
import cc.msyt.eagle.core.db.json.CurrencyTotalDeserializer;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import cc.msyt.eagle.core.db.base.BaseModel;
import java.io.Serializable;
import java.util.List;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 采购单
 * </p>
 *
 * @author msyt
 * @since 2019-07-16
 */
@TableName("tb_pur_po")
@Data
public class PurPo extends BaseModel {

	/**
	 * 订单编号
	 */
	@TableField(value="order_no")
	@ApiModelProperty("订单编号")
	private String orderNo;
	/**
	 * 子单数
	 */
	@TableField(value="num_of_sub_order")
	@ApiModelProperty("子单数")
	private Integer numOfSubOrder;
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
	@ApiModelProperty(" 10:待推送 20:已推送 30:待确认 40:已确认 50:待审核 90:已完成")
	private Integer bizStatus;

	@TableField(exist = false)
	@ApiModelProperty("子单列表")
	private List<PurSubPo> subPoList;

}
