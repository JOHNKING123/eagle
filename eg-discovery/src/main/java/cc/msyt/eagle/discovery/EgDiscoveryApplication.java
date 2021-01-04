package cc.msyt.eagle.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EgDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgDiscoveryApplication.class, args);
    }

}
