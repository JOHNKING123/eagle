package cc.zhengcq.eagle.vendor;

import cc.zhengcq.eagle.core.db.annotation.MyBatisDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients(basePackages = {"cc.zhengcq.eagle.vendor"})
@ComponentScan({"cc.zhengcq.eagle"})
@MapperScan(value = {"cc.zhengcq.eagle.vendor"}, annotationClass = MyBatisDao.class)
public class EgVendorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgVendorApplication.class, args);
    }

}
