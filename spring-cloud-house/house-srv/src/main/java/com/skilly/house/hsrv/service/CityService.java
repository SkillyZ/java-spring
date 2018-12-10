package com.skilly.house.hsrv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilly.house.hsrv.mapper.CityMapper;
import com.skilly.house.hsrv.model.City;

@Service
public class CityService {

    @Autowired
    private CityMapper cityMapper;

    public List<City> getAllCitys() {
        City query = new City();
        return cityMapper.selectCitys(query);
    }

}
