package com.mooc.house.hsrv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mooc.house.hsrv.model.City;

@Mapper
public interface CityMapper {
  
  public List<City> selectCitys(City city);

}
