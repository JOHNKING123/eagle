package cc.msyt.eagle.core.common.enums.vendor;

import cc.msyt.eagle.core.common.baseEnums.BaseEnums;

public enum EVendorPoBizStatus implements BaseEnums {

    E_WAIT_CONFIRME(10,"待确认"),

    E_VENDOR_CONFIRMED(40,"已确认"),
    ;


    EVendorPoBizStatus(int code, String value){
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
