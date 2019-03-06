package com.wzn.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/*
 * 购物车实体类CarVO
 * */
public class CartVO implements Serializable {
    //购物车信息集合
    private List<CartProductVO> cartProductVOList;
    //是否全选
    private boolean isallcheckd;
    //总价格
    private BigDecimal carttotalprice;

    public List<CartProductVO> getCartProductVOList() {
        return cartProductVOList;
    }

    public void setCartProductVOList(List<CartProductVO> cartProductVOList) {
        this.cartProductVOList = cartProductVOList;
    }

    public boolean isIsallcheckd() {
        return isallcheckd;
    }

    public void setIsallcheckd(boolean isallcheckd) {
        this.isallcheckd = isallcheckd;
    }

    public BigDecimal getCarttotalprice() {
        return carttotalprice;
    }

    public void setCarttotalprice(BigDecimal carttotalprice) {
        this.carttotalprice = carttotalprice;
    }
}
