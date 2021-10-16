package org.spring.springboot.interceptor;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.redis.core.StringRedisTemplate;


public class LoginInterceptor implements HandlerInterceptor{
    @Autowired
    private StringRedisTemplate redisTemplate;

    private Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    //ControllerController逻辑执行之前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            log.info("Interceptor Controller："+ handlerMethod.getBean().getClass().getName());
            log.info("Interceptor Method："+handlerMethod.getMethod().getName());
        }

        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        Cookie loginCookie  = null;
        Cookie SessionCookie  = null;
        if (null == cookies || cookies.length == 0) {
            response401(response);
            return false;
        }
        for(Cookie c:cookies){
            if(c.getName().equals("loginUserId"))
                loginCookie = (Cookie)c;
            if(c.getName().equals("Session"))
                SessionCookie = (Cookie)c;
        }
        if (loginCookie != null)
        {
            try
            {
                //验证当前请求的session是否是已登录的session
                String loginSessionId = redisTemplate.opsForValue().get("loginUser:" + loginCookie.getValue());
                if (loginSessionId != null && loginSessionId.equals(SessionCookie.getValue()))
                {
                    return true;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        response401(response);
        return false;
    }

    private void response401(HttpServletResponse response)
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        try
        {
            response.getWriter().print(JSON.toJSONString(new ResponseData("401", "", "用户未登录！")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //Controller逻辑执行完毕但是视图解析器还为进行解析之前
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("postHandle....");
    }

    //Controller逻辑和视图解析器执行完毕
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("afterCompletion....");
    }
}
