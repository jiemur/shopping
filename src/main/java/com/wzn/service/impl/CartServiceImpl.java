package com.wzn.service.impl;

import com.google.common.collect.Lists;
import com.wzn.common.Const;
import com.wzn.common.ServerResponse;
import com.wzn.dao.CartMapper;
import com.wzn.dao.ProductMapper;
import com.wzn.pojo.Cart;
import com.wzn.pojo.Product;
import com.wzn.service.ICartService;
import com.wzn.utils.BigDecimalUtils;
import com.wzn.vo.CartProductVO;
import com.wzn.vo.CartVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    @Resource
    CartMapper cartMapper;
    @Resource
    ProductMapper productMapper;
    @Override
    public ServerResponse add(Integer userId,Integer productId, Integer count) {
        //参数非空校验
        if(productId==null||count==null){
            return ServerResponse.responseErroe("用户不能为空");
        }
       Product product= productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.responseErroe("添加的商品不存在");
        }
        //根据productId和userId查询购物信息
       Cart cart= cartMapper.selectCartByUseridAndProductId(userId,productId);
        if(cart==null){
            //添加
            Cart cart1=new Cart();
            cart1.setProductId(productId);
            cart1.setUserId(userId);
            cart1.setQuantity(count);
            cart1.setChecked(Const.CartCheckdEnum.PRODUCT_CHECKED.getCode());
            cartMapper.insert(cart1);

        }else{
            //更新
            Cart cart1=new Cart();
            cart1.setId(cart.getId());
            cart1.setProductId(productId);
            cart1.setUserId(userId);
            cart1.setChecked(cart.getChecked());
            cart1.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart1);

        }
        CartVO cartVO=getCartVOLimit(userId);

        return ServerResponse.responseSuccess(cartVO);
    }

    @Override
    public ServerResponse list(Integer userId) {
      CartVO cartVO=  getCartVOLimit(userId);
      return ServerResponse.responseSuccess(cartVO);



    }

    @Override
    public ServerResponse update(Integer userId, Integer productId, Integer count) {
        //参数非空判断
        if(productId==null||count==null){
            return ServerResponse.responseErroe("参数不能为空");
        }

        //查询购物车中的商品
        Cart cart= cartMapper.selectCartByUseridAndProductId(userId,productId);
        if(cart!=null){
            //更新数量
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }


        //返回结果



        return ServerResponse.responseSuccess(getCartVOLimit(userId));
    }

    @Override
    public ServerResponse delete_product(Integer userId, String productIds) {
        //参数的非空校验
        if(productIds==null||productIds.equals("")){
            return ServerResponse.responseErroe("参数不能为空");
        }
        //productIds---->List<Integer>
        List<Integer> productIdList=Lists.newArrayList();
       String[] productIdsArr= productIds.split(",");
        if(productIds!=null&&productIdsArr.length>0){
            for(String productIdstr:productIdsArr){
                Integer prpductId=Integer.parseInt(productIdstr);
            }
        }
        //调用dao
cartMapper.deleteByUserIdAndProductIds(userId,productIdList);
        //返回结果
return ServerResponse.responseSuccess(getCartVOLimit(userId));

    }

    @Override
    public ServerResponse select(Integer userId, Integer productId,Integer check) {
        //参数非空校验
       /* if(productId==null){
            return ServerResponse.responseErroe("参数不能为空");
        }*/
        //dao接口
        cartMapper.selectOrUnselectProduct(userId,productId,check);

        //返回结果

        return ServerResponse.responseSuccess(getCartVOLimit(userId));
    }

    /*@Override
    public ServerResponse un_select(Integer userId, Integer productId, Integer check) {
        if(productId==null){
            return ServerResponse.responseErroe("参数不能为空");
        }
        cartMapper.selectOrUnselectProduct(userId,productId,Const.CartCheckdEnum.PRODUCT_NOCHECKED.getCode());



        return ServerResponse.responseSuccess(getCartVOLimit(userId));
    }
*/
    @Override
    public ServerResponse get_cart_product_count(Integer userId) {
       int quantity= cartMapper.get_cart_product_count(userId);
        return ServerResponse.responseSuccess(quantity);
    }

    private CartVO getCartVOLimit(Integer userId){
        CartVO cartVO=new CartVO();
        //根据用户id查询购物信息---->List<Cart>
        List<Cart> cartList= cartMapper.selectCartByUserid(userId);
        //list<Cart>--->ListCartProduct<VO>
        List<CartProductVO> cartProductVOList= Lists.newArrayList();

        //购物车总价格
        BigDecimal carttotalprice=new BigDecimal("0");
        if (cartList!=null&&cartList.size()>0) {
            for (Cart cart:cartList
                 ) {
                CartProductVO cartProductVO = new CartProductVO();
                cartProductVO.setId(cart.getId());
                cartProductVO.setQuantity(cart.getQuantity());
                cartProductVO.setUserId(userId);
                cartProductVO.setProductChecked(cart.getChecked());
                //查询商品
                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
                if (product != null) {
                    cartProductVO.setProductId(cart.getProductId());
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductStock(product.getStock());
                    cartProductVO.setProductSubtitle(product.getSubtitle());

                    int stock = product.getStock();
                    int limitProductCount = 0;
                    if (stock > cart.getQuantity()) {
                        limitProductCount = cart.getQuantity();
                        cartProductVO.setLimitQuantity("LIMIT_NUM_SUCCESS");
                    } else {//商品库存不足
                        limitProductCount = stock;
                        //更新购物车中商品的数量
                        Cart cart1 = new Cart();
                        cart1.setId(cart.getId());
                        cart1.setQuantity(stock);
                        cart1.setProductId(cart.getProductId());
                        cart1.setChecked(cart1.getChecked());
                        cart1.setUserId(userId);
                        cartMapper.updateByPrimaryKey(cart1);
                        cartProductVO.setLimitQuantity("LIMIT_NUM_FILL");

                    }
                    cartProductVO.setQuantity(limitProductCount);
                    cartProductVO.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(), Double.valueOf(cartProductVO.getQuantity())));


                }
            if(cartProductVO.getProductChecked()==Const.CartCheckdEnum.PRODUCT_CHECKED.getCode()){//被选中,计算总价
                carttotalprice=  BigDecimalUtils.add(carttotalprice.doubleValue(),cartProductVO.getProductTotalPrice().doubleValue());

            }

               cartProductVOList.add(cartProductVO);


            }
        }
        cartVO.setCartProductVOList(cartProductVOList);
        //计算总价格
        cartVO.setCarttotalprice(carttotalprice);

        //判断购物车是否全选
      int count=  cartMapper.isCheckedAll(userId);
        if(count>0){
            cartVO.setIsallcheckd(false);
        }else {
            cartVO.setIsallcheckd(true);
        }
        //返回结果
        return cartVO;
    }


}
