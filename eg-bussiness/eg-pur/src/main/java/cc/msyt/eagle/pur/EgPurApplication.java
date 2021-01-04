package cc.msyt.eagle.pur;

import cc.msyt.eagle.core.db.annotation.MyBatisDao;
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
@EnableFeignClients(basePackages = {"cc.msyt.eagle.pur"})
@ComponentScan({"cc.msyt.eagle"})
@MapperScan(value = {"cc.msyt.eagle.pur"}, annotationClass = MyBatisDao.class)
public class EgPurApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgPurApplication.class, args);
    }

}
