package com.skilly.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Created by 1254109699@qq.com on 2018/1/1.
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.skilly.controller.GirlController.*(..))")
    public void log()
    {
    }

    @Before("log()")
    public void doBefore()
    {
        logger.info("111111");
    }

    @After("log()")
    public void doAfter()
    {
        logger.info("2222222");
    }
}
