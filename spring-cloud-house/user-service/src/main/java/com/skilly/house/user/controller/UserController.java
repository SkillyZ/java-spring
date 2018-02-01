package com.skilly.house.user.controller;

import com.skilly.house.user.common.RestResponse;
import com.skilly.house.user.exception.IllegalParamsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.SoftReference;

/**
 * Created by ${1254109699@qq.com} on 2018/1/30.
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

//    @Value("${server.port}")
//    private Integer port;

    @RequestMapping("getusername")
    public RestResponse<String> getusername(Long id) {
        logger.info("Incoming Request and my server port is");
        if (id == null) {
            throw new IllegalParamsException(IllegalParamsException.Type.WRONG_PAGE_NUM, "错误分页");
        }
        return RestResponse.success("test-username" );
    }
}
