package cc.zhengcq.eagle.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@ComponentScan({"cc.zhengcq.eagle.core"})
public class EgGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgGatewayApplication.class, args);
    }

}
