package cc.zhengcq.eagle.base.mqListener;

import cc.zhengcq.eagle.base.model.BaseVendor;
import cc.zhengcq.eagle.core.common.utils.JSonUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * @ClassName: TestKafkaListener
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/18
 */
@Component
public class TestKafkaListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * topics：需要监听的Topic，可监听多个
     * id：消费者的id，当GroupId没有被配置的时候，默认id为GroupId
     * groupId：消费组ID 不使用group的话，启动10个consumer消费一个topic，这10个consumer都能得到topic的所有数据，
     * 相当于这个topic中的任一条消息被消费10次。使用group的话，
     *          连接时带上groupid，topic的消息会分发到10个consumer上，每条消息只被消费1次
     * topicPartitions：可配置更加详细的监听信息，必须监听某个Topic中的指定分区，或者从offset为200的偏移量开始监听
     * idIsGroup：id是否为GroupId
     * @param record record
     */
    @KafkaListener(topics = {"${kafka.topic.my-topic}"}, groupId = "group1")
    public void consumeMessage(ConsumerRecord<String, String> record) {
        try {
//            BaseVendor vendor = JSonUtils.loadJson(record.value(), BaseVendor.class);
            logger.info("消费者消费topic:{} partition:{}的消息 -> {}", record.topic(), record.partition(), record.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = {"${kafka.topic.my-topic}"}, groupId = "group1")
    public void consumeMessage1(ConsumerRecord<String, String> record) {
        try {
//            BaseVendor vendor = JSonUtils.loadJson(record.value(), BaseVendor.class);
            logger.info("消费者消费topic:{} consumeMessage1 partition:{}的消息 -> {}", record.topic(), record.partition(), record.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = {"${kafka.topic.my-topic}"}, groupId = "group2")
    public void consumeMessage2(ConsumerRecord<String, String> record) {
        try {
//            BaseVendor vendor = JSonUtils.loadJson(record.value(), BaseVendor.class);
            logger.info("消费者消费topic:{} consumeMessage2 partition:{}的消息 -> {}", record.topic(), record.partition(), record.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = {"${kafka.topic.my-topic}"}, groupId = "group6", containerFactory = "batchFactory")
    public void consumeMessage6(List<ConsumerRecord<String, String>> recordList) {
        try {
//            BaseVendor vendor = JSonUtils.loadJson(record.value(), BaseVendor.class);
            logger.info("consumeMessage6,size:{}", recordList.size());
            for (ConsumerRecord<String, String> record : recordList) {
                logger.info("消费者消费topic:{} consumeMessage6 partition:{}的消息 -> {}", record.topic(), record.partition(), record.value());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
