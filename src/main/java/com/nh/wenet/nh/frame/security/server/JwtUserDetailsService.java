package com.nh.wenet.nh.frame.security.server;

import com.nh.wenet.nh.frame.security.vo.UserInfo;
import com.nh.wenet.nh.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserInfo loadUserByUsername(String userName) throws UsernameNotFoundException {
        Map<String, Object> userMap = userService.selectinfoByUserName(userName);

        if (userMap == null) {
            return null;
        }

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));

        UserInfo userInfo = new UserInfo(userMap.get("id") + "", userMap.get("user_name")+"", userMap.get("user_name") + "", new Date(), authorityList);

        return userInfo;
    }


}
