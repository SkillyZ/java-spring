package com.skilly.house.api.common.zuul;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.skilly.house.api.common.ResultMsg;
import com.skilly.house.api.common.exception.RateLimiterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ${skilly} on 2018/3/7.
 */
@Component
public class ErrorFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(ErrorFilter.class);


    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().getThrowable() != null;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        Throwable throwable = ctx.getThrowable();
        logger.error("Exception" + throwable.getMessage() + "was thrown in filters:", throwable);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResultMsg resultMsg = ResultMsg.errorMsg("系统错误");
        //getCause（)返回此异常的原因（尝试加载类时发生错误引发的异常；否则返回 null）
        if (throwable instanceof RateLimiterException || throwable.getCause() instanceof RateLimiterException) {
            resultMsg.setErrorMsg("系统繁忙");
        }
        try {
            response.getWriter().write(JSON.toJSONString(resultMsg));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }
}
