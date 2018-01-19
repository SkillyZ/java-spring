package com.skilly.house.mapper;

import com.skilly.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by ${1254109699@qq.com} on 2018/1/19.
 */
@Mapper
public interface UserMapper {

    public List<User> selectUsers();
}
