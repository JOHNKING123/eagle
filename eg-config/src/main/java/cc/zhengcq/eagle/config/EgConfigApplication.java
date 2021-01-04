package cc.zhengcq.eagle.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class EgConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgConfigApplication.class, args);
    }

}
