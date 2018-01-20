package com.skilly.house.controller;

import java.util.List;

import com.skilly.house.common.model.User;
import com.skilly.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
  

  @Autowired
  private UserService userService;
  
  @RequestMapping("hello")
  public String  hello(ModelMap modelMap){
    List<User> users = userService.getUsers();
    User one = users.get(0);
    modelMap.put("user", one);
    return "hello";
  }
  
  @RequestMapping("index")
  public String index(){
     return "homepage/index";
  }

}
