package com.skilly1.house.autoconfig;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({HttpClient.class})
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {

	private final HttpClientProperties properties;

	public HttpClientAutoConfiguration(HttpClientProperties properties){
		this.properties = properties;
	}

	/**
	 * httpclient bean 的定义
	 * 有三种方式都可以让HttpClientAutoConfiguration这个自动配置生效
	 * 1.通过将自动配置所在package成为注解了@SpringBootApplication启动类的子package
	 * 2.通过定义META-INF/spring.factories文件，里面添加EnableAutoConfiguration与自动配置的映射关系
	 * 3.通过在启动类中添加注解EnableHttpClient,EnableHttpClient要@Import(HttpClientAutoConfiguration.class)
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(HttpClient.class)
	public HttpClient httpClient(){
		//构建requestConfig
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(properties.getConnectTimeOut())//设置连接超时时间，默认1秒
				.setSocketTimeout(properties.getSocketTimeOut()).build();//设置读超时时间，默认10秒
		HttpClient client = HttpClientBuilder.create()
				.setDefaultRequestConfig(requestConfig) //设置requestConfig
				.setUserAgent(properties.getAgent())//设置User-Agent
				.setMaxConnPerRoute(properties.getMaxConnPerRoute())//设置一个远端IP最大的连接数
				.setMaxConnTotal(properties.getMaxConnTotaol())//设置总的连接数
//				 .setConnectionReuseStrategy(new NoConnectionReuseStrategy())//不重用连接，默认是重用，建议保持默认开启连接池，节省建立连接开销
				.build();
		return client;
	}
}
