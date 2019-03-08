package com.skilly.ribbon;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;


/**
 * 配置类实现负载均衡策略
 * 使用RibbonClient，为特定name的Ribbon Client自定义配置.
 * 使用@RibbonClient的configuration属性，指定Ribbon的配置类.
 */
//@Configuration
//@RibbonClient(name = "springboot-eureka-clent", configuration = MyConfiguration.class)
public class ServiceProviderConfig {

}