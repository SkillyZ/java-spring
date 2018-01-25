package com.skilly.house.biz.mapper;

import com.skilly.house.common.model.Agency;
import com.skilly.house.common.model.User;
import com.skilly.house.common.page.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ${1254109699@qq.com} on 2018/1/25.
 */
@Mapper
public interface AgencyMapper {

    List<Agency> select(Agency agency);

    int insert(Agency agency);

    List<User> selectAgent(@Param("user") User user, @Param("pageParams") PageParams pageParams);

    Long selectAgentCount(@Param("user") User user);
}
