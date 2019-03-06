package com.wzn.controller.portal;

import com.wzn.common.Const;
import com.wzn.common.ServerResponse;
import com.wzn.pojo.UserInfo;
import com.wzn.service.ICartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    ICartService iCartService;
    /*购物车中添加商品*/
@RequestMapping(value = "/add.do")
    public ServerResponse add(HttpSession session,Integer productId, Integer count){
    UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
    if(userInfo==null){
        return ServerResponse.responseErroe("没有权限");
    }


        return iCartService.add(userInfo.getId(),productId,count);
    }


    @RequestMapping(value = "/list.do")
    public ServerResponse add(HttpSession session){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("需要登录");
        }


        return iCartService.list(userInfo.getId());
    }


    @RequestMapping(value = "/update.do")
    public ServerResponse update(HttpSession session,Integer productId,Integer count){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("需要登录");
        }


        return iCartService.update(userInfo.getId(),productId,count);
    }

    /*移除购物车中某些产品*/
    @RequestMapping(value = "/delete_product.do")
    public ServerResponse delete_product(HttpSession session,String productIds){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("需要登录");
        }


        return iCartService.delete_product(userInfo.getId(),productIds);
    }
    /*购物车中选中某个商品*/
    @RequestMapping(value = "/select.do")
    public ServerResponse select(HttpSession session,Integer productId){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("需要登录");
        }


        return iCartService.select(userInfo.getId(),productId,Const.CartCheckdEnum.PRODUCT_CHECKED.getCode());
    }


    /*购物车中取消选中某个商品*/
    @RequestMapping(value = "/un_select.do")
    public ServerResponse un_select(HttpSession session,Integer productId){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("需要登录");
        }
        return iCartService.select(userInfo.getId(),productId,Const.CartCheckdEnum.PRODUCT_NOCHECKED.getCode());
    }

    /*全选*/
    @RequestMapping(value = "/select_all.do")
    public ServerResponse select_all(HttpSession session){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("需要登录");
        }


        return iCartService.select(userInfo.getId(),null,Const.CartCheckdEnum.PRODUCT_CHECKED.getCode());
    }



    /*取消全选*/
    @RequestMapping(value = "/un_select_all.do")
    public ServerResponse un_select_all(HttpSession session){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("需要登录");
        }


        return iCartService.select(userInfo.getId(),null,Const.CartCheckdEnum.PRODUCT_NOCHECKED.getCode());
    }

    /*查询购物车中产品的数量
    *
    * */
    @RequestMapping(value = "/get_cart_product_count.do")
    public ServerResponse get_cart_product_count(HttpSession session){
        UserInfo userInfo= (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            return ServerResponse.responseErroe("需要登录");
        }


        return iCartService.get_cart_product_count(userInfo.getId());
    }





}
