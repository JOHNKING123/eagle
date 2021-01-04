package cc.msyt.eagle.core.common.enums.pur;

import cc.msyt.eagle.core.common.baseEnums.BaseEnums;

public enum EPurPoBizStatus implements BaseEnums {

    E_WAIT_PUSH(10,"待推送"),
    E_PUSHED(20,"已推送"),
    E_WAIT_CONFIRM(30,"待确认"),
    E_CONFIRMED(40,"已确认"),
    E_WAIT_REVIEW(50,"待审核"),
    E_FINISHED(90,"已完成"),
    ;


    EPurPoBizStatus(int code,String value){
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
