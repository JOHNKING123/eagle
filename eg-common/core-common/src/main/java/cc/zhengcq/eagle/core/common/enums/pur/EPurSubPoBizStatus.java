package cc.zhengcq.eagle.core.common.enums.pur;

import cc.zhengcq.eagle.core.common.baseEnums.BaseEnums;

public enum EPurSubPoBizStatus implements BaseEnums {

    E_WAIT_CONFIRME(10,"待确认"),
    E_VENDOR_CHANGED(20,"供应商有修改"),
    E_ITEM_PART_CHANGED(30,"商品部已修改"),

    E_VENDOR_CONFIRMED(40,"供应商已确认"),
    ;


    EPurSubPoBizStatus(int code, String value){
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
