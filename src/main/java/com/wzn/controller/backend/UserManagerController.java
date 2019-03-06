package com.wzn.controller.backend;

import com.wzn.common.Const;
import com.wzn.common.ServerResponse;
import com.wzn.pojo.UserInfo;
import com.wzn.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/*后台用户的控制类*/
@RestController
@RequestMapping(value = "/manage/user")
public class UserManagerController {
    @Resource
    IUserService userService;
    @RequestMapping(value = "/login.do")
    public ServerResponse login(HttpSession session, @RequestParam(value = "username") String username,
                                @RequestParam(value = "password") String password){
        ServerResponse serverResponse=userService.login(username,password);
        if(serverResponse.isSuccess()){
            UserInfo userInfo=(UserInfo) serverResponse.getData();
                if(userInfo.getRole()==Const.RoleEnum.ROLE_CUSTOMER.getCode()){
                    return ServerResponse.responseErroe("没有权限登录");
                }

            session.setAttribute(Const.CURRENTUSER,userInfo);
        }

        return serverResponse;
    }

}
