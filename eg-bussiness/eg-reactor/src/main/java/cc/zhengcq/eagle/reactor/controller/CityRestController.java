package cc.zhengcq.eagle.reactor.controller;

import cc.zhengcq.eagle.core.mq.config.KafkaConfig;
import cc.zhengcq.eagle.reactor.api.ICityService;
import cc.zhengcq.eagle.reactor.model.City;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @ClassName: CityRestController
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/22
 */
@RestController
@RequestMapping(value = "/api/eg-reactor/city")
public class CityRestController {

    /**
     * 日志对象
     */
    private static final Logger LOGGER   = LoggerFactory.getLogger(CityRestController.class);

    @Autowired
    private ICityService cityService;

    @Autowired
    private KafkaConfig kafkaConfig;

    @Autowired
    private RabbitProperties rabbitProperties;



    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Mono<City> findOneCity(@PathVariable("id") Long id) {
        return Mono.create(cityMonoSink -> cityMonoSink.success(cityService.findCityById(id)));
    }

    @RequestMapping(value = "/findAllCity", method = RequestMethod.GET)
    public Flux<City> findAllCity() {
        LOGGER.info(String.format("topic:%s", kafkaConfig.getMyTopic()));
        LOGGER.info(String.format("rabbit port:%s", rabbitProperties.getPort()));
        rabbitTemplate.convertAndSend("amq.fanout", "", "123123");
        return Flux.create(cityFluxSink -> {
            cityService.findAllCity().forEach(city -> {
                cityFluxSink.next(city);
            });
            cityFluxSink.complete();
        });
    }

    @RequestMapping(value = "/testJump", method = RequestMethod.GET)
    public Mono<String> testJump(HttpServletRequest req, HttpServletResponse res) {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.rocketMQTemplate.convertAndSend("queue_log_messages", localDateTime.toString());
        return Mono.create(monoSink -> monoSink.success(localDateTime.toString()));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Mono<Long> createCity(@RequestBody City city) {
        return Mono.create(cityMonoSink -> cityMonoSink.success(cityService.saveCity(city)));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Mono<Long> modifyCity(@RequestBody City city) {
        return Mono.create(cityMonoSink -> cityMonoSink.success(cityService.updateCity(city)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Mono<Long> modifyCity(@PathVariable("id") Long id) {
        return Mono.create(cityMonoSink -> cityMonoSink.success(cityService.deleteCity(id)));
    }
}
