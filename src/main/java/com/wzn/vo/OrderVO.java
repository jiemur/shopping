package com.wzn.vo;

import com.wzn.pojo.Shopping;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class OrderVO {
    private Long orderNo;
    private BigDecimal payment;
    private Integer paymentType;
    private String paymentTypeDesc;
    private Integer postage;
    private Integer status;
    private String statusDesc;
    private String paymentTime;
    private String sendTime;
    private String endTime;
    private String closeTime;
    private String createTime;

    private List<OrderItemVO> orderItemVOList;

    private String imageHost;
    private Integer shippingId;
    private String receiverName;
    private ShoppingVO shippingVo;
}
