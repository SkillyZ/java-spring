package com.skilly.house.biz.service;

import com.google.common.collect.Lists;
import com.skilly.house.common.model.City;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ${1254109699@qq.com} on 2018/1/29.
 */
@Service
public class CityService {

    public List<City> getAllCitys(){
        City city = new City();
        city.setCityCode("110000");
        city.setCityName("北京");
        city.setId(1);
        return Lists.newArrayList(city);
    }
}
