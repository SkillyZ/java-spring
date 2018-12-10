package com.skilly.house.api.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * Created by ${1254109699@qq.com} on 2018/1/31.
 */
public class NewRuleConfig {

    @Autowired
    private IClientConfig ribbonClientConfig;

    @Bean
    public IPing ribbonPing(IClientConfig config) {
        return new PingUrl(false, "/health");
    }

    @Bean
    public IRule ribbonRule(IClientConfig config) {
//		return new RandomRule();
        return new AvailabilityFilteringRule();
    }

}
