package com.wzn.controller.backend;

import com.wzn.common.Const;
import com.wzn.common.ServerResponse;
import com.wzn.pojo.Product;
import com.wzn.pojo.UserInfo;
import com.wzn.service.IProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/product")
public class ProductManageController {
    @Resource
    IProductService productService;

    /*新增或者更新商品*/
    @RequestMapping(value = "/save.do")
    public ServerResponse saveOrUpdate(HttpSession session, Product product) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode()) {
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(), Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }

        //返回结果
        return productService.saveOrUpdate(product);
    }

    /*产品的上下架
     * @param productId 商品id
     * @param status 商品状态
     * */
    @RequestMapping(value = "/set_sale_status.do")
    public ServerResponse set_sale_status(HttpSession session, Integer productId, Integer status) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode()) {
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(), Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }

        //返回结果
        return productService.set_sale_status(productId, status);
    }

    /*查看商品详情*/
    @RequestMapping(value = "/detail.do")
    public ServerResponse detail(HttpSession session, Integer productId) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode()) {
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(), Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }

        //返回结果
        return productService.detail(productId);
    }

    /*查看商品列表*/
    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode()) {
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(), Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        //返回结果
        return productService.list(pageNum, pageSize);
    }
    /*产品搜索*/
    @RequestMapping(value = "/search.do")
    public ServerResponse search(HttpSession session,
                               @RequestParam(value = "productId", required = false) Integer productId,
                               @RequestParam(value = "productName", required = false) String productName,
                                 @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize

    ) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NEED_LOGIN.getCode(), Const.ResponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode()) {
            return ServerResponse.responseErroe(Const.ResponseCodeEnum.NO_PRIVILEGE.getCode(), Const.ResponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        //返回结果
        return productService.search(productId,productName,pageNum, pageSize);
    }




}

