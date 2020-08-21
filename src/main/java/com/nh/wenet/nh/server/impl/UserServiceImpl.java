package com.nh.wenet.nh.server.impl;

import com.nh.wenet.nh.mapper.AccountMapper;
import com.nh.wenet.nh.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountMapper accountMapper;


    @Override
    public List<Map<String, Object>> selectAll() {
        return accountMapper.selectAll();
    }

    @Override
    public Map<String, Object> selectinfoByUserName(String userName) {
        return accountMapper.selectinfoByUserName(userName);
    }
}
