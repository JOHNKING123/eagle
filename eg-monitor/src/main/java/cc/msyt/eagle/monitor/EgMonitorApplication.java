package cc.msyt.eagle.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import zipkin2.server.internal.EnableZipkinServer;


/**
 *  hystrix dashboard 和turbin以及zipkin
 *  http://localhost:8889/hystrix
 *  http://localhost:8889/turbine.stream
 *  http://localhost:8889/zipkin/
 *  监控服务
 * @author    zhengcq
 * @date 	    2019-07-12
 * @version   v1.0.0
 * @since     2019-07-12
 */
@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine
@EnableZipkinServer
public class EgMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgMonitorApplication.class, args);
    }

}
