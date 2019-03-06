package com.wzn.service.impl;

import com.wzn.common.Const;
import com.wzn.common.ServerResponse;
import com.wzn.dao.UserInfoMapper;
import com.wzn.pojo.UserInfo;
import com.wzn.service.IUserService;

import com.wzn.utils.TokenCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
@Resource
    UserInfoMapper userInfoMapper;
    @Override
    public ServerResponse login(String username, String password) {

        /*
        *  1 用户的非空校验
           2检查用户名是否存在
           3根据用户名和密码查询用户信息
           4返回结果
        * */


        if(username==null||username.equals("")){
            return ServerResponse.responseErroe("用户名不能为空");
        }
        if(password==null||password.equals("")){
            return ServerResponse.responseErroe("密码不能为空");
        }

        int result= userInfoMapper.checkUsername(username);
        if(result==0){
            return ServerResponse.responseErroe("用户名不存在");
        }
        UserInfo userInfo= userInfoMapper.getUserInfoByUsernameAndPass(username,password);
        if(userInfo==null){
            return ServerResponse.responseErroe("密码错误");
        }else{
            userInfo.setPassword("");
            return ServerResponse.responseSuccess(userInfo);
        }

    }

    @Override
    public ServerResponse register(UserInfo userInfo) {

        //1 参数非空校验
        if(userInfo==null){
            return ServerResponse.responseErroe("参数不能为空");
        }
        //2 校验用户名
        int result=userInfoMapper.checkUsername(userInfo.getUsername());
        if(result>0){
            return ServerResponse.responseErroe("用户名已存在");
        }
        //3 校验邮箱
       int email= userInfoMapper.checkEmail(userInfo.getEmail());
            if(email>0){
            return ServerResponse.responseErroe("邮箱已被注册");
            }
        //4 注册
        userInfo.setPassword(userInfo.getPassword());
        userInfo.setRole(Const.RoleEnum.ROLE_CUSTOMER.getCode());
        int count= userInfoMapper.insert(userInfo);
            if(count>0){
                return ServerResponse.responseSuccess("注册成功");
            }
        //5 返回结果
        return ServerResponse.responseErroe("注册失败");
    }

    @Override
    public ServerResponse forget_get_question(String username) {
        //参数非空校验
            if(username==null||username.equals("")){
              return   ServerResponse.responseErroe("用户名不能为空");
            }
        //校验用户名
           int result=userInfoMapper.checkUsername(username);
            if (result==0){
              return   ServerResponse.responseErroe("用户名不存在,请重新输入");

            }

        //查找密保问题
         String question= userInfoMapper.getQuestionByUsername(username);
            if(question==null||question.equals("")){
            return ServerResponse.responseErroe("密保问题为空");
            }
        return ServerResponse.responseSuccess(question );
    }

    @Override
    public ServerResponse forget_check_answer( String username, String question, String answer) {
        // 参数校验
        if(username==null||username.equals("")){
            return   ServerResponse.responseErroe("用户名不能为空");
        }
        if(question==null||question.equals("")){
            return   ServerResponse.responseErroe("问题不能为空");
        }
        if(answer==null||answer.equals("")){
            return   ServerResponse.responseErroe("答案不能为空");
        }
        //根据参数查询
          int result=  userInfoMapper.selectByUsernameAndQuestionAndAnswer(username,question,answer);
            if(result==0){
                return ServerResponse.responseErroe("答案错误");
            }
        //服务端要生成一个token保存并将token返回客户端
            String forgetToken= UUID.randomUUID().toString();
        //guava缓存
        TokenCache.set(username,forgetToken);

        return ServerResponse.responseSuccess(forgetToken);
    }

    @Override
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken) {
        //参数校验
        if(username==null||username.equals("")){
            return   ServerResponse.responseErroe("用户名不能为空");
        }
        if(passwordNew==null||passwordNew.equals("")){
            return   ServerResponse.responseErroe("密码不能为空");
        }
        if(forgetToken==null||forgetToken.equals("")){
            return   ServerResponse.responseErroe("token不能为空");
        }
        //token的校验
       String token= TokenCache.get(username);
        if(token==null){
            return ServerResponse.responseErroe("token过期了");
        }
        if(!token.equals(forgetToken)){
            return ServerResponse.responseErroe("无效的token");

        }
        //修改密码
       int result= userInfoMapper.upadateUserPassword(username,passwordNew);
        if(result>0){
            return ServerResponse.responseSuccess();
        }
        return ServerResponse.responseErroe("修改失败");
    }

    @Override
    public ServerResponse check_valid(String str, String type) {
        // 参数非空校验
        if(str==null||str.equals("")){
            return   ServerResponse.responseErroe("用户名或者邮箱不能为空");
        }
        if(type==null||type.equals("")){
            return   ServerResponse.responseErroe("参数的校验类型不能为空");
        }
        //type:username -->校验用户名,--> email 校验邮箱
            if(type.equals("username")){
               int result= userInfoMapper.checkUsername(str);
               if(result>0){
                   return ServerResponse.responseErroe("用户已存在");
               }else{
                   return ServerResponse.responseSuccess();

               }
            }
            if(type.equals("email")){
                 int result=   userInfoMapper.checkEmail(str);
                 if(result>0){
                     return ServerResponse.responseErroe("邮箱已存在");
                 }else{
                     return ServerResponse.responseSuccess();
                 }
            }
            return ServerResponse.responseErroe("参数错误");
        //返回结果

    }

    @Override
    public ServerResponse reset_password(String username,String passwordOld, String passwordNew) {
        //非空校验
        if(passwordOld==null||passwordOld.equals("")){
            return   ServerResponse.responseErroe("旧密码不能为空");
        }
        if(passwordNew==null||passwordNew.equals("")){
            return   ServerResponse.responseErroe("新密码不能为空");
        }
        //根据username和passwordOld
         UserInfo userInfo=userInfoMapper.getUserInfoByUsernameAndPass(username,passwordOld);
        if(userInfo==null){
            return   ServerResponse.responseErroe("旧密码错误");
        }
        //修改密码
        userInfo.setPassword(passwordNew);
          int result=  userInfoMapper.updateByPrimaryKey(userInfo);
            if(result>0){
                return ServerResponse.responseSuccess();
            }

        return   ServerResponse.responseErroe("修改密码失败");
    }

    @Override
    public ServerResponse update_information(UserInfo user) {
        //参数校验
        if(user==null){
            return ServerResponse.responseErroe("参数不能为空");
        }
        //更新用户信息
      int result=  userInfoMapper.updateUserBySelectActive(user);
        if(result>0){
            return ServerResponse.responseSuccess();
        }
        return ServerResponse.responseErroe("更新个人信息失败");
    }

    @Override
    public UserInfo findUserInfoByUserId(Integer userId) {
        userInfoMapper.selectByPrimaryKey(userId);

        return null;
    }


}
