package com.skilly.house.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author skilly
 */
@RequestMapping("error")
@Controller
@ControllerAdvice
public class ErrorController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);
	
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@RequestMapping("404")
	public String error404(HttpServletRequest request,ModelMap modelMap){
		LOGGER.error(request.getRequestURL()+" encounter " + 404);
		return "/error/404";
	}

	
	@ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping("400")
    public String error400(HttpServletRequest request,ModelMap modelMap){
        LOGGER.error(request.getRequestURL()+" encounter " + 400);
        return "/error/404";
    }
	
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @RequestMapping("403")
    public String error403(HttpServletRequest request, ModelMap modelMap) {
      LOGGER.error(request.getRequestURL() + " encounter " + 403);
      return "/error/404";
    }

	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public String error500(HttpServletRequest request, Exception e){
        LOGGER.error(request.getRequestURL()+" encounter 500");
        LOGGER.error(e.getMessage(),e);
        return "/error/500";
    }
    

}
