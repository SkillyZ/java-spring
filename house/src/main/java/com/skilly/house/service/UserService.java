package com.skilly.house.service;

import com.skilly.house.common.model.User;
import com.skilly.house.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ${1254109699@qq.com} on 2018/1/19.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getUsers()
    {
        return userMapper.selectUsers();
    }
}
