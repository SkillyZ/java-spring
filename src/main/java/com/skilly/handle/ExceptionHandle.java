package com.skilly.handle;

import com.skilly.entity.Result;
import com.skilly.excecption.GirlException;
import com.skilly.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 1254109699@qq.com on 2018/1/1.
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger= LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result handleGirlAge(Exception e) {
        if(e instanceof GirlException) {
            GirlException girlException= (GirlException)e;
            return ResultUtil.error(girlException.getCode(), girlException.getMessage());
        } else {
            logger.error("<系统异常> {}" ,e);
            return ResultUtil.error(-1, "未知错误");
        }


    }
}