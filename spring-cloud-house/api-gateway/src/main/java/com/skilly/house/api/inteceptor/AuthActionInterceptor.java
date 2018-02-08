/**
 *
 */
package com.skilly.house.api.inteceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skilly.house.api.common.UserContext;
import com.skilly.house.api.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 */
@Component
public class AuthActionInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws Exception {
        User user = UserContext.getUser();
        if (user == null) {
            String msg = URLEncoder.encode("请先登录", "utf-8");
            StringBuffer sb = req.getRequestURL();
            String target = URLEncoder.encode(sb.toString(), "utf-8");
            if ("GET".equalsIgnoreCase(req.getMethod())) {
                res.sendRedirect("/accounts/signin?errorMsg=" + msg + "&target=" + target);
            } else {
                res.sendRedirect("/accounts/signin?errorMsg=" + msg);
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
