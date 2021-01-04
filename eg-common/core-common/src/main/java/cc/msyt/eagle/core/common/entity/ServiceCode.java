package cc.msyt.eagle.core.common.entity;

/**
 * Created by zhengcq  2019/07/11PropertiesLoader
 * 错误码修改
 * 0    成功
 * 非0  失败 ( 默认失败错误码为1 )
 */
public interface ServiceCode {
    static final int OK = 0;
    static final int FAILED = 1;
}
