package com.nh.wenet.nh.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AccountMapper {

    List<Map<String,Object>> selectAll();

    Map<String,Object> selectinfoByUserName(@Param("userName") String userName);
}
