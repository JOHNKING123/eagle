package cc.zhengcq.eagle.reactor;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @ClassName: TestReactor
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/22
 */
public class TestReactor {
    private static final Logger logger = LoggerFactory.getLogger(TestReactor.class);
    @Test
    public void disposable() throws InterruptedException {

        Flux<Long> longFlux = Flux.interval(Duration.ofMillis(1));
        //take方法准确获取订阅数据量
        Disposable disposable = longFlux.take(50).subscribe(x->logger.info("->{}",x));
        //不能停止正在推送数据中的Flux或Mono流
        Thread.sleep(100);
        //彻底停止正在推送数据中的Flux或Mono流
        disposable.dispose();
        logger.info("->Stop");
    }
}
