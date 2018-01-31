package com.skilly.house.api.service;

import com.skilly.house.api.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ${1254109699@qq.com} on 2018/1/30.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public String getusername(Long id) {
        return userDao.getusername(id);
    }
}
