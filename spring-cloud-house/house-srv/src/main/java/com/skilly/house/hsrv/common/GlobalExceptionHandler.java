package com.mooc.house.hsrv.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mooc.house.hsrv.exception.Exception2CodeMap;
import com.mooc.house.hsrv.exception.WithTypeException;

@ControllerAdvice
public class GlobalExceptionHandler {
  
  private static final Logger LOGGER  = LoggerFactory.getLogger(GlobalExceptionHandler.class);
  
  
  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(value = Throwable.class)
  @ResponseBody
  public RestResponse<Object> handler(HttpServletRequest req, Throwable throwable) throws Exception {
      LOGGER.error(throwable.getMessage(), throwable);
      LOGGER.error(req.getRequestURL().toString() + " encounter exception or error");
      Object target = throwable;
      if (throwable instanceof WithTypeException) {
        Object type = getType(throwable); 
        if (type != null) {
          target = type;
        }
      }
      RestCode code =  Exception2CodeMap.getCode(target);
      RestResponse<Object> response = new RestResponse<>(code.code,code.msg);
      return response;
  }
  
  private Object getType(Throwable throwable){
    try {
       return FieldUtils.readDeclaredField(throwable, "type", true);
    } catch (Exception e) {
       //ignore
       return null;
    }
  }
  

}
