package cc.zhengcq.eagle.stream.handler;

import cc.zhengcq.eagle.stream.channel.MyProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TestHandler
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/28
 */
@Component
@EnableBinding(value = {Processor.class, MyProcessor.class})
public class TestHandler {

    @Autowired
    private Processor processor;

    /**
     * processor
     */
//    @Autowired
//    private MyProcessor processor;

    @StreamListener(Processor.INPUT)
//    @SendTo(Processor.OUTPUT)
    public void routeValues(Message<String> message) {
        System.out.println("val:" + message.getPayload());
        TestRequest testRequest = new TestRequest();
        testRequest.setPath("/path/test");
        processor.output().send(MessageBuilder.withPayload(testRequest).build());
    }

    @StreamListener(MyProcessor.INPUT)
    public void testMyInput(Message<TestRequest> message) {
        System.out.println("test myInput val:" + message.getPayload());
    }


    @StreamListener(MyProcessor.INPUT)
    public void testMyInput1(Message<TestRequest> message) {
        System.out.println("test myInput1 val:" + message.getPayload().getPath());
    }
//    private static final <T> Message<T> message(T val) {
//        return MessageBuilder.withPayload(val).build();
//    }
}
