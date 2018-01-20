package com.skilly.house.controller;

import com.skilly.house.common.model.User;
import com.skilly.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${1254109699@qq.com} on 2018/1/19.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("user")
    public List<User> getUsers() {

        return userService.getUsers();
    }


}
