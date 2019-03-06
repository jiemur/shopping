package com.wzn.controller.portal;

import com.wzn.common.Const;
import com.wzn.common.ServerResponse;
import com.wzn.pojo.Shopping;
import com.wzn.pojo.UserInfo;
import com.wzn.service.impl.AddressServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/shopping")
public class AddressController {
    @Resource
    AddressServiceImpl addressService;
    /*添加地址*/

    @RequestMapping("/add.do")
    public ServerResponse add(HttpSession session, Shopping shopping){
      UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("需要登录");
        }
        return addressService.add(userInfo.getId(),shopping);
    }
    @RequestMapping("/del.do")
    public ServerResponse del(HttpSession session, Integer shoppingId){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("需要登录");
        }
        return addressService.del(userInfo.getId(),shoppingId);
    }
   /*登录状态更新地址*/
    @RequestMapping("/update.do")
    public ServerResponse update(HttpSession session, Shopping shopping){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("需要登录");
        }
        return addressService.update(shopping);
    }

    /*查看*/
    @RequestMapping("/select.do")
    public ServerResponse select(HttpSession session, Integer shoppingId){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("需要登录");
        }
        return addressService.select(shoppingId);
    }

    /*分页查看地址*/
    @RequestMapping("/list.do")
    public ServerResponse list(HttpSession session,
                              @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("需要登录");
        }
        return addressService.list(pageNum,pageSize);
    }
}
