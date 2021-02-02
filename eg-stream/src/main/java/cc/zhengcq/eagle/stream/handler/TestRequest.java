package cc.zhengcq.eagle.stream.handler;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: TestRequest
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/28
 */
@Data
@ToString
public class TestRequest implements Serializable {

    /**
     * path
     */
    private String path;
}
