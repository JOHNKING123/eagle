package cc.zhengcq.eagle.reactor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: City
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class City implements Serializable {

    static {
        System.out.println("City static code");
        instance  = new City();
    }
    public static class CityStaticClass {
        public static City instance = new City();
        static {
            System.out.println("CityStaticClass");
        }
    }

    public static  String TEST_STR = "hello";

    public static City instance;



    public static int test1() {
      return 1;
    }

    public static City getInstance() {
        System.out.println("getInstance");
        return instance;
    }

    public static City getInstance1() {
       return CityStaticClass.instance;
    }

    /**
     * name
     */
    private String name;

    /**
     * nick
     */
    private transient String nick;
}
