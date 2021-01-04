package cc.msyt.eagle.core.common.baseEnums;

/**
 * Created by zhengcq 2019/07/11
 */
public interface BaseEnums {
    int getCode();
    String getDisplayName();

    default int code(){
        return this.getCode();
    }

    default String name(){
        return this.getDisplayName();
    }
}
