package cc.zhengcq.eagle.reactor.api;

/**
 * @ClassName: ITestFuncInteface
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/2/28
 */
@FunctionalInterface
public interface ITestFuncInteface<T> {

    T testGet();
}
