package org.spring.springboot.controller;


import org.spring.springboot.domain.User;
import org.spring.springboot.utils.ResponseData;
import org.spring.springboot.utils.ResultEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private List<String> userList = new ArrayList(Arrays.asList("user1", "user2", "user3", "user4", "user5"));

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseData login(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {

        if (userList.contains(user.getUserId())){
            HttpSession session = request.getSession();
            session.setAttribute("loginUserId", user.getUserId());
            response.addCookie(new Cookie("loginUserId", user.getUserId()));
            redisTemplate.opsForValue().set("loginUser:" + user.getUserId(), session.getId());

            return new ResponseData(ResultEnums.SUCCESS.getCode(), user.getUserId(), ResultEnums.SUCCESS.getMsg());
        }
        return new ResponseData(ResultEnums.VERIFY_CODE_ERROR.getCode(), user.getUserId(), ResultEnums.VERIFY_CODE_ERROR.getMsg());
    }

    @RequestMapping(value = "/api/loginout", method = RequestMethod.POST)
    public ResponseData loginout(HttpServletRequest request, @RequestBody User user) {
        return new ResponseData(ResultEnums.SUCCESS.getCode(), user.getUserId(), ResultEnums.SUCCESS.getMsg());
    }
}