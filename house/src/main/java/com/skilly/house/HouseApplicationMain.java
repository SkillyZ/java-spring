package com.skilly.house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync//注解的意思是可以异步执行，就是开启多线程的意思。可以标注在方法、类上。
public class HouseApplicationMain {

	public static void main(String[] args) {
		SpringApplication.run(HouseApplicationMain.class, args);
	}
}
