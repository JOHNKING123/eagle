package cc.msyt.eagle.core.common.enums;

import cc.msyt.eagle.core.common.baseEnums.BaseEnums;

public enum  ErrorCode  implements BaseEnums {

    //10001 -  19999  作为通用错误类型
    E_INVALID_PARAMS(10001,"请求参数错误"),
    E_NOT_EXIST_RECORD(10002,"记录不存在"),
    E_EXISTED_RECORD(10003,"记录已存在"),
    E_INVALID_STATUS(10004,"状态异常")
    ;


    ErrorCode(int code,String value){
        this.code = code;
        this.value = value;
    }
    private int code;

    private String value;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDisplayName() {
        return value;
    }
}
