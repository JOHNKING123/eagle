package cc.zhengcq.eagle.base;

import cc.zhengcq.eagle.base.model.TestRequest;
import com.google.gson.Gson;
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

    @Test
    public void test2() {
        Gson gson = new Gson();
        String jsonStr =  "{\"status\":200,\"msg\":\"OK\",\"data\":{\"searchWord\":\"@蒜头50738的店\",\"moduleList\":[{\"moduleId\":10560,\"moduleType\":10,\"moduleName\":\"轮播图橱窗\",\"title\":\"\",\"subTitle\":\"\",\"contentList\":[{\"officeContentFlag\":0,\"contentType\":80,\"pageType\":0,\"contentImage\":\"https://garliclub-dev.oss-accelerate.aliyuncs.com/20210127/20210127145121927_631.jpg\",\"title\":\"\",\"subTitle\":\"\",\"contentUrl\":\"\",\"itemId\":0,\"itemName\":\"\",\"categoryId\":0,\"categroyName\":\"\",\"brandId\":0,\"brandCode\":\"\",\"activityId\":0}],\"brandList\":[],\"itemList\":[],\"hasNext\":false}],\"itemModule\":{\"moduleId\":10716,\"moduleType\":30,\"moduleName\":\"火爆热销\",\"title\":\"商品橱窗\",\"subTitle\":\"商品橱窗\",\"contentList\":[],\"brandList\":[],\"itemList\":[],\"hasNext\":false}}}";
        TestRequest testRequest = gson.fromJson(jsonStr, TestRequest.class);
        System.out.println(testRequest);
    }
}
