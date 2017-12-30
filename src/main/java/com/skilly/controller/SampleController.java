package com.skilly.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class SampleController {

    @RequestMapping("/test")
    @ResponseBody
    String home() {
        return "Hello World!";
    }


    @RequestMapping("/posts/{id}")
    public String post(@PathVariable("id") int id) {
        return String.format("post %d", id);
    }

    @RequestMapping("hello/{name}")
    public String hello (@PathVariable("name") String name, Model model)
    {
        model.addAttribute(model);
        return "hello";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }

    //分析启动时的信息：
//    第 9 行，启动SampleController。
//    第10行，查找active profile，无，设为default。
//    第11行，刷新上下文。
//    第12行，初始化tomcat，设置端口8080，设置访问方式为http。
//    第13行，启动tomcat服务。
//    第14行，启动Servlet引擎。
//    第15行，Spring内嵌的WebApplicationContext 初始化开始。
//    第16行，Spring内嵌的WebApplicationContext 初始化完成。
//    第17行，映射servlet，将 dispatcherServlet 映射到 [/] 。
//    第18行，映射filter，将 characterEncodingFilter 映射到 [/*] 。
//    第19行，映射filter，将 hiddenHttpMethodFilter 映射到 [/*] 。
//    第20行，映射filter，将 httpPutFormContentFilter 映射到 [/*] 。
//    第21行，映射filter，将 requestContextFilter 映射到 [/*] 。
//    第22行，查找 @ControllerAdvice。
//    第23行，映射路径 "{[/]}" 到 cn.larry.spring.controller.SampleController.home()。
//    第24行，映射路径 "{[/error]}" 到 org.springframework.boot.autoconfigure.web.BasicErrorController.error(javax.servlet.http.HttpServletRequest)。
//    第25行，映射路径 "{[/error],produces=[text/html]}" 到 org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)。
//    第26行，略。 第27行，略。 第28行，略。 第29行，略。
//    第30行，tomcat启动完毕。
//    第31行，SampleController启动耗费的时间。
//    第32行，初始化 dispatcherServlet 。
//    第33行，dispatcherServlet 的初始化已启动。
//    第34行，dispatcherServlet 的初始化已完成。
//    第35行，收到shutdown关闭请求。
//    第36行，关闭AnnotationConfigEmbeddedWebApplicationContext。
//    第37行，略。
}