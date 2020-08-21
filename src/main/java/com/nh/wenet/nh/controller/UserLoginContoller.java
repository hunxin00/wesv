package com.nh.wenet.nh.controller;

import com.alibaba.fastjson.JSON;
import com.nh.wenet.nh.frame.security.server.JwtUserDetailsService;
import com.nh.wenet.nh.frame.security.utils.JwtTokenUtil;
import com.nh.wenet.nh.frame.security.vo.UserInfo;
import com.nh.wenet.nh.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping
@RestController
public class UserLoginContoller {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public String login(String userName, HttpServletRequest request) {
        UserInfo userDetails = jwtUserDetailsService.loadUserByUsername(userName);
        if (userDetails == null) {
            return "用户名或密码错误";
        }
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> map = new HashMap<>();
        map.put("token", "Wenotid"+token);
        return JSON.toJSONString(map);
    }

    @PostMapping("/user/haha")
    public String haha() {
        List<Map<String, Object>> userList = userService.selectAll();
        return JSON.toJSONString(userList);
    }
}
