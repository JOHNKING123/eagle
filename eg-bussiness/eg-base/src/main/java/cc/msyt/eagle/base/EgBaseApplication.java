package cc.msyt.eagle.base;

import cc.msyt.eagle.core.db.annotation.MyBatisDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@ComponentScan({"cc.msyt.eagle"})
@MapperScan(value = {"cc.msyt.eagle.base"}, annotationClass = MyBatisDao.class)
public class EgBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgBaseApplication.class, args);
    }

}
