package com.wzn.service;

import com.wzn.common.ServerResponse;
import com.wzn.pojo.Product;


public interface IProductService {
   ServerResponse saveOrUpdate(Product product);
   /*商品上下架*/
   ServerResponse set_sale_status( Integer productId,Integer status);
   /*查看商品详情*/
   ServerResponse detail(Integer productId);
   /*后台-商品列表,分页*/
   ServerResponse list(Integer pageNum,Integer pageSize);
   /*后台-搜索商品*/
    ServerResponse search(Integer productId,String productName,Integer pageNum,Integer pageSize);
    /**/
    ServerResponse detail_portal(Integer productId);

    /*前台商品搜索
    * @param categoryId
    * @param keyword
    * @param pageNum
    * @param pageSize
    * */
    ServerResponse list_portal( Integer categoryId, String keyword, Integer pageNum, Integer pageSize,String orderBy);
}
