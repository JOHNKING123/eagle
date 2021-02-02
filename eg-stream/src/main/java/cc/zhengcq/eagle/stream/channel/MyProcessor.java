package cc.zhengcq.eagle.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @ClassName: MyProcessor
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/28
 */
public interface MyProcessor {
    /** myInput **/
    String INPUT = "myInput";

    @Input
    SubscribableChannel myInput();

}