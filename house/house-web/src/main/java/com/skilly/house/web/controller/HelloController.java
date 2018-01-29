package com.skilly.house.web.controller;

import com.skilly.house.biz.service.UserService;
import com.skilly.house.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HelloController {


    @Autowired
    private UserService userService;

    @RequestMapping("hello")
    public String hello(ModelMap modelMap) {
        List<User> users = userService.getUsers();
        User one = users.get(0);
        modelMap.put("user", one);
        return "hello";
    }
}
