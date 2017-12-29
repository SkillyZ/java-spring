package com.skilly.mapper;

import com.skilly.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PersonMapper {
    @Select("SELECT * FROM PERSON WHERE id = #{id}")
    Person getById(@Param("id") int id);
}
