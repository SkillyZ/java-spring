package com.skilly;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

//	注意点：1、DemoApplication类跟controller包等平行
//2、@controller注解返回指定页面，本文中即返回index页面，也就是templates文件夹下的index.html
//3、如果需要返回json字符串、xml等，需要在有@controller类下相关的方法上加上注解@responsebody
//4、@restcontroller注解的功能等同于@controller和@responsebody
//	有问题请留言，一起讨论。
//	5、springboot默认缓存templates下的文件，如果html页面修改后，看不到修改的效果，设置spring.thymeleaf.cache = false即可

	@Test
	public void contextLoads() {

	}

}
