package cc.zhengcq.eagle.reactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName: EgReactorApplication
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/22
 */
@SpringBootApplication
//@EnableEurekaClient
//@EnableCircuitBreaker
//@EnableFeignClients(basePackages = {"cc.zhengcq.eagle.pur"})
//@ComponentScan({"cc.zhengcq.eagle"})
//@MapperScan(value = {"cc.zhengcq.eagle.pur"}, annotationClass = MyBatisDao.class)
public class EgReactorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgReactorApplication.class, args);
    }
}
