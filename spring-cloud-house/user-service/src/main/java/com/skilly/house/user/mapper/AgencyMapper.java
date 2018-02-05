package com.skilly.house.user.mapper;

import java.util.List;

import com.skilly.house.user.common.PageParams;
import com.skilly.house.user.model.Agency;
import com.skilly.house.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AgencyMapper {

    List<Agency> select(Agency agency);

    int insert(Agency agency);

    List<User> selectAgent(@Param("user") User user, @Param("pageParams") PageParams pageParams);

    Long selectAgentCount(@Param("user") User user);
}
