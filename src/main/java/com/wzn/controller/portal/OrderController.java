package com.wzn.controller.portal;

import com.wzn.common.Const;
import com.wzn.common.ServerResponse;
import com.wzn.pojo.UserInfo;
import com.wzn.service.IOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/order")
public class OrderController {
    @Resource
    IOrderService iOrderService;
    /*创建订单*/
    @RequestMapping("/create.do")
    public ServerResponse createOrder(HttpSession session, Integer shoppingId){
      UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
      if(userInfo==null){
          return ServerResponse.responseErroe("需要登录");

      }
         return iOrderService.create(userInfo.getId(),shoppingId);
    }
}
