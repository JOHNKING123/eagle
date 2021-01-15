package cc.zhengcq.eagle.base;

import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @ClassName: TestDemo
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/11
 */
public class TestDemo {

    @Test
    public void test1() {
        LinkedBlockingQueue queue = new LinkedBlockingQueue(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);
//        queue.add(4);
        System.out.println(queue.size());
        System.out.println(Optional.ofNullable(1).get());
    }
}
