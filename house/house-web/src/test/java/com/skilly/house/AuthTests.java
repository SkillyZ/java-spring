package com.skilly.house;

import com.skilly.house.biz.service.UserService;
import com.skilly.house.common.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ${1254109699@qq.com} on 2018/1/24.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthTests {

    @Autowired
    private UserService userService;

    @Test
    public void testAuth() {
        User user = userService.auth("1254109699@qq.com", "123456");
        assert user != null;
        System.out.println(user.getAboutme());
    }

}