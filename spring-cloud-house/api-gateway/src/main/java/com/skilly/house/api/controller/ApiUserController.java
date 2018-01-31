package com.skilly.house.api.controller;

import com.netflix.discovery.converters.Auto;
import com.skilly.house.api.common.RestResponse;
import com.skilly.house.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.XMLFormatter;

/**
 * Created by ${1254109699@qq.com} on 2018/1/30.
 */
@RestController
public class ApiUserController {

    @Autowired
    private UserService userService;

    @RequestMapping("test/getusername")
    public RestResponse<String> getusername(Long id) {
        return RestResponse.success(userService.getusername(id));
    }
}
