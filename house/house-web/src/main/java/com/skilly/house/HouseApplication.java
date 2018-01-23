package com.skilly.house;

import com.skilly1.house.autoconfig.EnableHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
//@EnableHttpClient
@EnableAsync//注解的意思是可以异步执行，就是开启多线程的意思。可以标注在方法、类上。
@EnableSwagger2 //Swagger2构建强大的RESTful API文档
//@ComponentScan(value = "com.skilly.house")
//@MapperScan(value = "com.skilly.house.biz.mapper")
public class HouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseApplication.class, args);
	}
}
