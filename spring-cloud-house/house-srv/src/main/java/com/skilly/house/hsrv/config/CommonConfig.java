package com.mooc.house.hsrv.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;

@Configuration
public class CommonConfig {

  private static final Integer HTTPCLIENT_CONNECTTIMEOUT = 1000;
  private static final Integer HTTPCLIENT_SOCKETTIMEOUT  = 30000;
  private static final String HTTPCLIENT_AGENT           = "mooc-agent";

  @Bean
  HttpClient httpClient() {
    Integer connectTimeOut = HTTPCLIENT_CONNECTTIMEOUT;
    Integer socketTimeOut  = HTTPCLIENT_SOCKETTIMEOUT;
    String agentStr        = HTTPCLIENT_AGENT;
    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeOut)
        .setSocketTimeout(socketTimeOut).build();

    HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
        .setUserAgent(agentStr).setMaxConnPerRoute(10).setMaxConnTotal(50).disableAutomaticRetries()
        .setConnectionReuseStrategy(new NoConnectionReuseStrategy()).build();
    return client;
  }

  @Bean
  @LoadBalanced
  RestTemplate lbRestTemplate() {
    return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient()));
  }

  @Bean
  RestTemplate plainRestTemplate() {
    return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient()));
  }
  
  @ConfigurationProperties(prefix="spring.druid")
  @Bean(initMethod="init",destroyMethod="close")
  public DataSource dataSource(Filter statFilter) throws SQLException{
      DruidDataSource dataSource = new DruidDataSource();
      dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
      return dataSource;
  }
  
  @Bean
  public Filter statFilter(){
      StatFilter filter = new StatFilter();
      filter.setSlowSqlMillis(5000);
      filter.setLogSlowSql(true);
      filter.setMergeSql(true);
      return filter;
  }
  
  
  

}
