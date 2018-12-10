package com.skilly.house.user.mapper;

import java.util.List;

import com.skilly.house.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;


@Mapper
public interface UserMapper {

    User selectById(Long id);

    List<User> select(User user);

    int update(User user);

    int insert(User account);

    int delete(String email);

    User selectByEmail(String email);

}
