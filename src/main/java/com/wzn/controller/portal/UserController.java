package com.wzn.controller.portal;

import com.wzn.common.Const;
import com.wzn.common.ServerResponse;
import com.wzn.pojo.UserInfo;
import com.wzn.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    UserServiceImpl userService;
/*
* 登录
* */
//required表示username可传可不传defaultValue默认值
    @RequestMapping(value = "/login.do")
    public ServerResponse login(HttpSession session,@RequestParam(value = "username") String username,
                                @RequestParam(value = "password") String password){
       ServerResponse serverResponse=userService.login(username,password);
            if(serverResponse.isSuccess()){
                UserInfo userInfo=(UserInfo) serverResponse.getData();
                    session.setAttribute(Const.CURRENTUSER,userInfo);
            }

        return serverResponse;
    }

/*注册*/
    @RequestMapping(value = "/register.do")
    public ServerResponse register(UserInfo userInfo){
        ServerResponse serverResponse=userService.register(userInfo);
        return serverResponse;
    }

    /*根据用户名查询用户的密保问题*/
    @RequestMapping(value = "/forget_get_question.do")
    public ServerResponse forget_get_question(String username){
        ServerResponse serverResponse=userService.forget_get_question(username);
        return serverResponse;
    }
    /*提交问题答案*/
    @RequestMapping(value = "/forget_check_answer.do")
    public ServerResponse forget_check_answer(String username,String question,String answer){
        ServerResponse serverResponse=userService.forget_check_answer(username,question,answer);
        return serverResponse;
    }

    /*忘记密码的重置密码*/
    @RequestMapping(value = "/forget_reset_password.do")
    public ServerResponse forget_reset_password(String username,String passwordNew,String forgetToken){
        ServerResponse serverResponse=userService.forget_reset_password(username,passwordNew,forgetToken);
        return serverResponse;
    }

    /*检查用户名或者邮箱是否有效*/
    @RequestMapping("/check_valid.do")
    public ServerResponse check_valid(String str,String type){
        ServerResponse serverResponse=userService.check_valid(str,type);
        return serverResponse;
    }
    /*获取用户的登录信息*/
    @RequestMapping("/get_user_info.do")
    public ServerResponse get_user_info(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("用户未登录");
        }
        userInfo.setPassword("");
        userInfo.setQuestion("");
        userInfo.setAnswer("");
        userInfo.setRole(null);
        return ServerResponse.responseSuccess(userInfo);
    }

    /*登录状态下重置密码*/
    @RequestMapping("/reset_password.do")
    public ServerResponse reset_password(HttpSession session,String passwordOld,String passwordNew){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("用户未登录");
        }
        return userService.reset_password(userInfo.getUsername(),passwordOld,passwordNew);
    }

    /*登录状态下更新个人信息*/
    @RequestMapping("/update_information.do")
    public ServerResponse update_information(HttpSession session,UserInfo user){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("用户未登录");
        }
        user.setId(userInfo.getId());
        ServerResponse serverResponse=userService.update_information(user);
        if(serverResponse.isSuccess()){
            /*更新session中的用户信息*/
           UserInfo userInfo1= userService.findUserInfoByUserId(userInfo.getId());
            session.setAttribute(Const.CURRENTUSER,userInfo1);
        }
        return userService.update_information(user);
    }
    /*获取详细信息 */
    @RequestMapping("/get_information.do")
    public ServerResponse get_information(HttpSession session){
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("用户未登录");
        }
        userInfo.setPassword("");
        return ServerResponse.responseSuccess(userInfo);
    }
    /*退出登录*/
    @RequestMapping("/logout.do")
    public ServerResponse logout(HttpSession session){
       session.removeAttribute(Const.CURRENTUSER);
        return ServerResponse.responseSuccess();
    }

}
