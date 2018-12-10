package com.skilly.feign;

import org.springframework.stereotype.Component;

/**
 * Created by 1254109699@qq.com on 2018/1/9.
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry " + name;
    }
}