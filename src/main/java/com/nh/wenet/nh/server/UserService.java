package com.nh.wenet.nh.server;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface UserService {
    List<Map<String,Object>> selectAll();
    Map<String,Object> selectinfoByUserName( String userName);

}
