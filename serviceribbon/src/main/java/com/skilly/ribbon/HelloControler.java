package com.skilly.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 1254109699@qq.com on 2018/1/8.
 */
@RestController
public class HelloControler {

    @Autowired
    HelloService helloService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;


    @RequestMapping(value = "/hi")
    public String hi(@RequestParam String name) {
        return helloService.hiService(name);
    }

    @GetMapping(value = "/getUser")
    public String getUser(@RequestParam("id") String id) {
        this.loadBalancerClient.choose("CLIENT1");
        return restTemplate.getForEntity("http://CLIENT1/user/findById?id="+id, String.class).getBody();
    }


    @RequestMapping(value = "/hi")
    public String hi2(@RequestParam("id") String id){
        return  restTemplate.getForObject("http://CLIENT1/user/findById?id="+id,String.class);

    }


}