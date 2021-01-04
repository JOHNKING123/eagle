package cc.zhengcq.eagle.pur;

import cc.zhengcq.eagle.core.db.annotation.MyBatisDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients(basePackages = {"cc.zhengcq.eagle.pur"})
@ComponentScan({"cc.zhengcq.eagle"})
@MapperScan(value = {"cc.zhengcq.eagle.pur"}, annotationClass = MyBatisDao.class)
public class EgPurApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgPurApplication.class, args);
    }

}
