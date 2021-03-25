package cc.zhengcq.eagle.reactor.service;

import cc.zhengcq.eagle.reactor.api.ICityService;
import cc.zhengcq.eagle.reactor.model.City;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @ClassName: CityService
 * @Description: todo
 * @Company: 广州市两棵树网络科技有限公司
 * @Author: zhengcq
 * @Date: 2021/1/22
 */
@Service
public class CityService implements ICityService {
    @Override
    public List<City> findAllCity() {
        List<City> ls = new LinkedList<>();
        IntStream.range(0, 10).forEach(num -> ls.add(new City("city" + num, "c")));

        return ls;
    }

    @Override
    public City findCityById(Long id) {
        return null;
    }

    @Override
    public Long saveCity(City city) {
        return null;
    }

    @Override
    public Long updateCity(City city) {
        return null;
    }

    @Override
    public Long deleteCity(Long id) {
        return null;
    }
}
