package cc.zhengcq.eagle.core.common.entity;

import lombok.Data;

import java.util.List;

@Data
public class BatchOperateResult {

    // 总条数
    private Integer totalCount;
    //  错误数量
    private Integer  errorCount;


    private List<BatchOperateResultDetail> detailList;

}
