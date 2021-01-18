package cc.zhengcq.eagle.base.mqListener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TestListener
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/17
 */
@Component
public class TestListener {

    @RabbitListener(queuesToDeclare = @Queue(value = "topic.zcqtest"))
    public void onMessage(String data) {
        System.out.println("consumerExistsQueue: " + data);
    }

//    @RabbitListener(queues = "fanous.zcqtest")
//    public void onFanousMessage(String data) {
//        System.out.println("fanous.zcqtest: " + data);
//    }
//    @RabbitListener(queues = "fanous.zcqtest1")
//    public void onFanous1Message(String data) {
//        System.out.println("fanous.zcqtest1: " + data);
//    }
//    @RabbitListener(queues = "fanous.zcqtest2")
//    public void onFanous2Message(String data, Message message, org.springframework.messaging.Message message1, Channel channel) {
//        System.out.println("fanous.zcqtest2: " + data + " message:" + message.getBody() + ", msg1:" + message1.getPayload() + ",ch:" + channel);
//    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(),
            exchange = @Exchange(value = "amq.fanout", type = ExchangeTypes.FANOUT)), concurrency = "4")
    public void onFanousRandomMessage(String data) {
        System.out.println("onFanousRandomMessage: " + data + ":thread:" + Thread.currentThread().getName());
    }
}
