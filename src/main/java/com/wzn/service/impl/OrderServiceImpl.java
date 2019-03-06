package com.wzn.service.impl;

import com.google.common.collect.Lists;
import com.wzn.common.Const;
import com.wzn.common.ServerResponse;
import com.wzn.dao.*;
import com.wzn.pojo.*;
import com.wzn.service.IOrderService;
import com.wzn.utils.BigDecimalUtils;
import com.wzn.utils.DateUtils;
import com.wzn.vo.OrderItemVO;
import com.wzn.vo.OrderVO;
import com.wzn.vo.ShoppingVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements IOrderService {
    @Resource
    CartMapper cartMapper;
    @Resource
    ProductMapper productMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderItemMapper orderItemMapper;
    @Resource
    ShoppingMapper shoppingMapper;
    @Override
    public ServerResponse create(Integer userId, Integer shoppingId) {
        //参数非空校验
        if(shoppingId==null){
            return ServerResponse.responseErroe("地址参数不能为空");
        }


        //根据userId购物车中已选中的商品--->List<Cart>
           List<Cart> cartList= cartMapper.findCartListByUserIdAndCheck(userId);
        //List<Cart>---->List<OrderItem>
            ServerResponse serverResponse=getCartOrderItem(userId,cartList);
            if(!serverResponse.isSuccess()){
                return serverResponse;
            }
        //创建订单order并将其保存到数据库
        BigDecimal orderTotalPrice=new BigDecimal("0");
            List<OrderItem> orderItemList= (List<OrderItem>) serverResponse.getData();
            if (orderItemList==null||orderItemList.size()==0){
                return ServerResponse.responseErroe("购物车为空");
            }
            orderTotalPrice=getOrderPrice(orderItemList);
            Order order=createOrder(userId,shoppingId,orderTotalPrice);
            if(order==null){
                return ServerResponse.responseErroe("订单创建失败");
            }
        //List<OrderItem>保存到数据库
            for(OrderItem orderItem:orderItemList){
                orderItem.setOrderNo(order.getOrderNo());
            }
            //批量插入
        orderItemMapper.insertBatch(orderItemList);
        //扣商品的库存
        reduceProductStock(orderItemList);
        //清空购物车中已下单的商品
        cleanCart(cartList);
        //前台返回OrderVO
       OrderVO orderVO= assembleOrderVO(order,orderItemList,shoppingId);

        return ServerResponse.responseSuccess(orderVO);
    }

    private OrderVO assembleOrderVO(Order order,List<OrderItem> orderItemList,Integer shoppingId){
        OrderVO orderVO=new OrderVO();
        List<OrderItemVO> orderItemVOList=Lists.newArrayList();
        for (OrderItem orderItem:orderItemList
             ) {
            OrderItemVO orderItemVO=assembleOrderItemVO(orderItem);
            orderItemVOList.add(orderItemVO);

        }
        orderVO.setOrderItemVOList(orderItemVOList);
        Shopping shopping=shoppingMapper.selectByPrimaryKey(shoppingId);
        if(shopping!=null){
            orderVO.setShippingId(shoppingId);
            ShoppingVO shoppingVO=assebleShoppingVO(shopping);
            orderVO.setShippingVo(shoppingVO);
            orderVO.setReceiverName(shopping.getReceiverName());

        }
        orderVO.setStatus(order.getStatus());
        Const.OrderStatusEnum orderStatusEnum =Const.OrderStatusEnum.codeOf(order.getStatus());
        if(orderStatusEnum!=null){
           orderVO.setStatusDesc(orderStatusEnum.getDesc());
       }

        orderVO.setPostage(0);
        orderVO.setPayment(order.getPayment());
        orderVO.setPaymentType(order.getPaymentType());
       Const.PaymentEnum paymentEnum= Const.PaymentEnum.codeOf(order.getPaymentType());
        if(paymentEnum!=null){
            orderVO.setPaymentTypeDesc(paymentEnum.getDesc());
        }
        orderVO.setOrderNo(order.getOrderNo());
        return orderVO;
    }

    private ShoppingVO assebleShoppingVO(Shopping shopping){
        ShoppingVO shoppingVO=new ShoppingVO();

        if(shopping!=null){
            shoppingVO.setReceiverAddress(shopping.getReceiverAddress());
            shoppingVO.setReceiverCity(shopping.getReceiverCity());
            shoppingVO.setReceiverDistrict(shopping.getReceiverDistrict());
            shoppingVO.setReceiverMobile(shopping.getReceiverMobile());
            shoppingVO.setReceiverName(shopping.getReceiverName());
            shoppingVO.setReceiverPhone(shopping.getReceiverPhone());
            shoppingVO.setReceiverProvince(shopping.getReceiverProvince());
            shoppingVO.setReceiverZip(shopping.getReceiverZip());
        }
        return shoppingVO;
    }


    private OrderItemVO assembleOrderItemVO(OrderItem orderItem){
        OrderItemVO orderItemVO=new OrderItemVO();
        if(orderItem!=null){
            orderItemVO.setQuantity(orderItem.getQuantity());
            orderItemVO.setCreateTime(DateUtils.dateToStr(orderItem.getCreateTime()));
            orderItemVO.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
            orderItemVO.setOrderNo(orderItem.getOrderNo());
            orderItemVO.setProductId(orderItem.getProductId());
            orderItemVO.setProductImage(orderItem.getProductImage());
            orderItemVO.setProductName(orderItem.getProductName());
            orderItemVO.setTotalPrice(orderItem.getTotalPrice());


        }
        return orderItemVO;
    }


    /*清空购物车中已选择的商品*/
    private void cleanCart(List<Cart> cartList){
        if(cartList!=null&&cartList.size()>0){
            cartMapper.batchDelete(cartList);
        }

    }

    /*扣库存*/
    private void reduceProductStock(List<OrderItem> orderItemList){
        if(orderItemList!=null){

            for(OrderItem orderItem:orderItemList){
               Integer productId= orderItem.getProductId();
               Integer quantity=orderItem.getQuantity();
              Product product= productMapper.selectByPrimaryKey(productId);
              product.setStock(product.getStock()-quantity);
              productMapper.updateByPrimaryKey(product);
            }
        }
    }

    /*计算订单总价格*/
    private BigDecimal getOrderPrice(List<OrderItem> orderItemList){
        BigDecimal bigDecimal=new BigDecimal(0);
        for (OrderItem orderItem:orderItemList){
          bigDecimal=  BigDecimalUtils.add(bigDecimal.doubleValue(),orderItem.getTotalPrice().doubleValue());
        }
        return bigDecimal;
    }

    /*创建订单*/
    private Order createOrder(Integer userId, Integer shoppingId, BigDecimal orderTotalPrice){
        Order order=new Order();
        order.setOrderNo(generateOrderNO());
        order.setUserId(userId);
        order.setShippingId(shoppingId);
        order.setStatus(Const.OrderStatusEnum.ORDER_UN_PAY.getCode());
        //订单金额
        order.setPayment(orderTotalPrice);
        order.setPostage(0);
        order.setPaymentType(Const.PaymentEnum.ONLINE.getCode());

        //保存订单
        int result = orderMapper.insert(order);
        if(result>0){
            return order;
        }
        return order;
    }

    /*生成订单编号*/
    private Long generateOrderNO(){
        return System.currentTimeMillis()+new Random().nextInt(100);

    }

    private ServerResponse getCartOrderItem(Integer userId,List<Cart> cartList){
        if(cartList==null||cartList.size()==0){
            return ServerResponse.responseErroe("购物车为空");
        }
        List<OrderItem> orderItemList= Lists.newArrayList();
        for(Cart cart:cartList){
            OrderItem orderItem=new OrderItem();
            orderItem.setUserId(userId);
           Product product= productMapper.selectByPrimaryKey(cart.getProductId());
            if(product==null){
                return ServerResponse.responseErroe("id为"+cart.getProductId()+"商品不存在");

            }
            if(product.getStatus()!= Const.ProductStatusEnum.PRODUCT_ONLINE.getCode()){//商品下架
                return ServerResponse.responseErroe("id为"+product.getId()+"的商品已经下架");
            }
            if(product.getStock()<cart.getQuantity()){//库存不足
                return ServerResponse.responseErroe("id为"+product.getId()+"的商品库存不足");
            }
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setProductId(product.getId());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setProductName(product.getName());
            orderItem.setTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(),cart.getQuantity().doubleValue()));

            orderItemList.add(orderItem);
        }

        return ServerResponse.responseSuccess(orderItemList);
    }
}
