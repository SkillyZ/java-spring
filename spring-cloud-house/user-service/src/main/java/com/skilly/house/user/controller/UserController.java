package com.skilly.house.user.controller;

import com.skilly.house.user.common.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.SoftReference;

/**
 * Created by ${1254109699@qq.com} on 2018/1/30.
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @RequestMapping("getusername")
    public RestResponse<String> getusername(Long id) {
        logger.info("Incoming Request");
        return RestResponse.success("test-username");
    }
}
