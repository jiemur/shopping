package com.wzn.service;

import com.wzn.common.ServerResponse;
import com.wzn.pojo.Shopping;

public interface IAddressService {


    /*添加收货地址*/
    public ServerResponse add(Integer userId, Shopping shopping);
    /*删除地址*/
    ServerResponse del(Integer userId,Integer shoppingId);
    /*更新地址*/
    ServerResponse update(Shopping shopping);
    /*查看*/
    ServerResponse select(Integer shoppingId);
    /*分页查询*/
    ServerResponse list(Integer pageNum,Integer pageSize);
}
