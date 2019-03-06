package com.wzn.service;

import com.wzn.common.ServerResponse;

public interface IOrderService {
    /*创建订单*/

    ServerResponse create(Integer userId,Integer shoppingId);
}
