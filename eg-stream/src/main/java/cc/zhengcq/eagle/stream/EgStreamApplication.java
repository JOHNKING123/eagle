package cc.zhengcq.eagle.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: EgStreamApplication
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/28
 */
@EnableDiscoveryClient
@SpringBootApplication
public class EgStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgStreamApplication.class, args);
    }
}
