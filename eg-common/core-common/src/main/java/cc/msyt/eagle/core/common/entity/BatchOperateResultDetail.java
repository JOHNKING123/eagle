package cc.msyt.eagle.core.common.entity;

import lombok.Data;

@Data
public class BatchOperateResultDetail {
    //  结果  0:正常  其他为错误码
    private Integer  result;

    //  业务编码
    private String code;

    //  信息
    private String message;


}
