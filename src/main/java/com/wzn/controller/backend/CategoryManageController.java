package com.wzn.controller.backend;

import com.wzn.common.Const;
import com.wzn.common.ServerResponse;
import com.wzn.pojo.UserInfo;
import com.wzn.service.ICategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/category")
public class CategoryManageController {
    @Resource
    ICategoryService service;
    /*获取品类的子节点(平级)*/
    @RequestMapping(value = "/get_category.do")
    public ServerResponse get_category(HttpSession session,Integer categoryId){
       UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return service.get_category(categoryId);
    }
    /*增加节点*/
    @RequestMapping(value = "/add_category.do")
    public ServerResponse add_category(HttpSession session,
                                       @RequestParam(required = false,defaultValue = "0") Integer parentId,
                                       String categoryName){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return service.add_category(parentId,categoryName);
    }

    /*修改节点的名字*/

    @RequestMapping(value = "/set_category_name.do")
    public ServerResponse set_category_name(HttpSession session,
                                        Integer categoryId,
                                       String categoryName){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return service.set_category_name(categoryId,categoryName);
    }

    /*获取当前分类id及递归子节点categoryId*/

    @RequestMapping(value = "/get_deep_category.do")
    public ServerResponse get_deep_category(HttpSession session,
                                            Integer categoryId){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return service.get_deep_category(categoryId);
    }
}
