package com.nh.wenet.nh.controller;

import com.alibaba.fastjson.JSON;
import com.nh.wenet.nh.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class AccountController {


    @Autowired
    public UserService userService;

    @RequestMapping("/getinfo")
    public String getData() {
        List<Map<String, Object>> userList = userService.selectAll();
        return JSON.toJSONString(userList);
    }
}
