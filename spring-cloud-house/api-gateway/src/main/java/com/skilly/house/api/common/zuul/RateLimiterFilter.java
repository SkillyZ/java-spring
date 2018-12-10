package com.skilly.house.api.common.zuul;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.ZuulFilterResult;
import com.skilly.house.api.common.exception.RateLimiterException;

import java.util.concurrent.TimeUnit;

/**
 * Created by ${skilly} on 2018/3/7.
 * 限流令牌桶算法
 */
public class RateLimiterFilter extends ZuulFilter {

    private static final RateLimiter RATE_LIMTER = RateLimiter.create(100, 1, TimeUnit.DAYS);

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        if (!RATE_LIMTER.tryAcquire()) {
            throw new RateLimiterException();
        }
        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -100;
    }
}
