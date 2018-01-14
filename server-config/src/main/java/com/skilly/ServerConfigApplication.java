package com.skilly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ServerConfigApplication {

//	启动类添加@EnableDiscoveryClient激活对配置中心的支持
	public static void main(String[] args) {
		SpringApplication.run(ServerConfigApplication.class, args);
	}
}

