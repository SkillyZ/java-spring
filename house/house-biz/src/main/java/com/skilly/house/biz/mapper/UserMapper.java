package com.skilly.house.biz.mapper;

import com.skilly.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by ${1254109699@qq.com} on 2018/1/19.
 */
@Mapper
public interface UserMapper {

    public List<User> selectUsers();

    public int insert(User account);

    public int delete(String email);

    public int update(User updateUser);

    public List<User> selectUsersByQuery(User user);
}

