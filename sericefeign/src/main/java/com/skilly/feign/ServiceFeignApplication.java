package com.skilly.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by 1254109699@qq.com on 2018/1/8.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceFeignApplication {

    //加上@EnableFeignClients注解开启Feign的功能：
    public static void main(String[] args) {
        SpringApplication.run(ServiceFeignApplication.class, args);
    }
}