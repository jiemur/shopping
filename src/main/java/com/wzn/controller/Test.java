package com.wzn.controller;

import com.wzn.dao.UserInfoMapper;
import com.wzn.pojo.UserInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Test {
    @Resource
    UserInfoMapper userInfoMapper;
    @RequestMapping(value = "/user/{userid}")
    public UserInfo getUser(@PathVariable Integer userid){
    return userInfoMapper.selectByPrimaryKey(userid);
    }
}
