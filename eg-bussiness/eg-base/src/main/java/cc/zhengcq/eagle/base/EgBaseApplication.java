package cc.zhengcq.eagle.base;

import cc.zhengcq.eagle.core.db.annotation.MyBatisDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@ComponentScan({"cc.zhengcq.eagle"})
@MapperScan(value = {"cc.zhengcq.eagle.base"}, annotationClass = MyBatisDao.class)
public class EgBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgBaseApplication.class, args);
    }

}
