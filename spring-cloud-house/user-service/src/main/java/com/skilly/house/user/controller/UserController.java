package com.skilly.house.user.controller;

import com.skilly.house.user.common.RestResponse;
import com.skilly.house.user.exception.IllegalParamsException;
import com.skilly.house.user.model.User;
import com.skilly.house.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * Created by ${1254109699@qq.com} on 2018/1/30.
 */
@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /*
    @Autowired
    private StringRedisTemplate redisTemplate;

//    @Value("${server.port}")
//    private Integer port;

    @RequestMapping("getusername")
    public RestResponse<String> getusername(Long id) {
        logger.info("Incoming Request and my server port is");
        if (id == null) {
            throw new IllegalParamsException(IllegalParamsException.Type.WRONG_PAGE_NUM, "错误分页");
        }
        redisTemplate.opsForValue().set("key1", "val1");
        logger.info(redisTemplate.opsForValue().get("key1"));
        return RestResponse.success("test-username" );
    }*/

    //------------------------------ 查询----------------------

    @RequestMapping("getById")
    public RestResponse<User> getUserById(Long id) {
        User user = userService.getUserById(id);
        return RestResponse.success(user);
    }

    @RequestMapping("getList")
    public RestResponse<List<User>> getUserList(@RequestBody User user) {
        List<User> users = userService.getUserByQuery(user);
        return RestResponse.success(users);
    }


    //----------------------注册----------------------------------
    @RequestMapping("add")
    public RestResponse<User> add(@RequestBody User user) {
        userService.addAccount(user, user.getEnableUrl());
        return RestResponse.success();
    }

    /**
     * 主要激活key的验证
     */
    @RequestMapping("enable")
    public RestResponse<Object> enable(String key) {
        userService.enable(key);
        return RestResponse.success();
    }
}
