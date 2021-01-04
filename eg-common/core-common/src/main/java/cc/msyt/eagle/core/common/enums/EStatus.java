package cc.msyt.eagle.core.common.enums;

import cc.msyt.eagle.core.common.baseEnums.BaseEnums;

public enum EStatus implements BaseEnums {
    E_INVALID(0,"作废/软删除"),
    E_VALID(1,"可用"),
    E_NOT_ACTIVE(2,"未激活")
    ;
    EStatus(int code,String value){
       this.code = code;
       this.value = value;
    }
    private int code;

    private String value;
    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDisplayName() {
        return this.value;
    }


}
