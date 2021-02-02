package cc.zhengcq.eagle;

import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import rx.Observable;
import rx.internal.util.InternalObservableUtils;

import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }



    @Test
    public void timeWindowTest() throws Exception{
        Observable<Integer> source = Observable.interval(50, TimeUnit.MILLISECONDS).map(i -> RandomUtils.nextInt(2));
        source.window(1, TimeUnit.SECONDS).subscribe(window -> {
            int[] metrics = new int[2];
            window.subscribe(i -> metrics[i]++,
                    InternalObservableUtils.ERROR_NOT_IMPLEMENTED,
                    () -> System.out.println("窗口Metrics:" + JSON.toJSONString(metrics)));
        });
        TimeUnit.SECONDS.sleep(3);
    }
}
