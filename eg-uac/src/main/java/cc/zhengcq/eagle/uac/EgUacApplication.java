package cc.zhengcq.eagle.uac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
@EnableAuthorizationServer
@SpringBootApplication
public class EgUacApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgUacApplication.class, args);
    }

}
