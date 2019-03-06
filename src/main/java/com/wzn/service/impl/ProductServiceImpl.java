package com.wzn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wzn.common.Const;
import com.wzn.common.ServerResponse;
import com.wzn.dao.CategoryMapper;
import com.wzn.dao.ProductMapper;
import com.wzn.pojo.Category;
import com.wzn.pojo.Product;
import com.wzn.service.ICategoryService;
import com.wzn.service.IProductService;
import com.wzn.utils.DateUtils;
import com.wzn.vo.ProductDetailVO;
import com.wzn.vo.ProductListVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements IProductService {
    @Resource
    ProductMapper productMapper;
    @Resource
    CategoryMapper categoryMapper;
    @Resource
    ICategoryService categoryService;
    @Override
    public ServerResponse saveOrUpdate(Product product) {
        //参数非空校验
        if(product==null){
            return ServerResponse.responseErroe("参数为空");
        }

        //设置商品的主图 sub_images---->1.jpg 2.jpg 3.jpg
            String subImages=product.getSubImages();
        if(subImages!=null&&!subImages.equals("")){
            String[] subImageArr=subImages.split(",");
            if(subImageArr.length>0){
                product.setMainImage(subImageArr[0]);
            }
        }
        //商品save or update
        if(product.getId()==null){//添加
           int result= productMapper.insert(product);
           if(result>0){
               return ServerResponse.responseSuccess();
           }else {
               return ServerResponse.responseErroe("添加失败");
           }
        }else{
            //更新
          int result=  productMapper.updateByPrimaryKey(product);
            if(result>0){
                return ServerResponse.responseSuccess();
            }else {
                return ServerResponse.responseErroe("更新失败");
            }
        }
        //返回结果


    }

    @Override
    public ServerResponse set_sale_status(Integer productId, Integer status) {
        //参数校验
        if(productId==null){
            return ServerResponse.responseErroe("商品id不能为空");
        }
        if(status==null){
            return ServerResponse.responseErroe("商品状态不能为空");
        }
        //更新商品的状态
        Product product=new Product();
        product.setId(productId);
        product.setStatus(status);
       int result= productMapper.updateProductKeySelective(product);
        //返回结果
        if(result>0){
            return ServerResponse.responseSuccess();
        }else {
            return ServerResponse.responseErroe("更新失败");
        }

    }

    @Override
    public ServerResponse detail( Integer productId) {
        //参数校验
        if(productId==null){
            return ServerResponse.responseErroe("商品id不能为空");
        }
        //查询商品
      Product product=  productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.responseErroe("商品信息不能为空");
        }
        //product-->productDetailVO
        ProductDetailVO productDetailVO=assembleProductDetailVO(product);
        //返回结果
return ServerResponse.responseSuccess(productDetailVO);

    }

    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {
       //查询商品数据
        PageHelper.startPage(pageNum,pageSize);//一定要在查询语句之前

      List<Product> productList= productMapper.selectAll();
      List<ProductListVO> productListVOList= Lists.newArrayList();
      if(productList!=null&&productList.size()>0){
          for(Product product:productList){
                ProductListVO productListVO=assembleProductListVO(product);
                productListVOList.add(productListVO);
          }

      }
        PageInfo pageInfo=new PageInfo(productListVOList);
      return ServerResponse.responseSuccess(pageInfo);


    }

    @Override
    public ServerResponse search(Integer productId, String productName, Integer pageNum, Integer pageSize) {
        //select * from product where productId ? and productName like %name%
        PageHelper.startPage(pageNum,pageSize);
         if(productName!=null&&!productName.equals("")){
             productName="%"+productName+"%";
         }

        List<Product> productList= productMapper.findProductByProductIdAndProductName(productId,productName);
         List<ProductListVO> productListVOList=Lists.newArrayList();
        if(productList!=null&&productList.size()>0){
            for(Product product:productList){
                ProductListVO productListVO=assembleProductListVO(product);
                productListVOList.add(productListVO);
            }

        }
        PageInfo pageInfo=new PageInfo(productListVOList);
         return ServerResponse.responseSuccess(pageInfo);
    }
/*前台接口-商品详细*/
    @Override
    public ServerResponse detail_portal(Integer productId) {
        //参数非空校验
        if(productId==null){
            return ServerResponse.responseErroe("商品id不能为空");
        }
        //查询商品
        Product product=  productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.responseErroe("商品不存在");
        }
        //校验商品的状态
        if(product.getStatus()!= Const.ProductStatusEnum.PRODUCT_ONLINE.getCode()){
            return ServerResponse.responseErroe("商品已下架或者删除");
        }
        //获取productDetailVO
      ProductDetailVO productDetailVO=  assembleProductDetailVO(product);

        //返回结果


        return ServerResponse.responseSuccess();
    }

    @Override
    public ServerResponse list_portal (Integer categoryId, String keyword, Integer pageNum, Integer pageSize,String orderBy) {
        Set<Integer> integerSet= Sets.newHashSet();
            //参数的校验 categoryId和keyword不能同时为空
            if(categoryId==null&&(keyword==null||keyword.equals(""))){
                return ServerResponse.responseErroe("参数错误");
            }
            //根据categoryId查询
            if(categoryId!=null){
                Category c =  categoryMapper.selectByPrimaryKey(categoryId);
                if(c==null&&(keyword==null||keyword.equals(""))){
                    //说明没有商品数据
                    PageHelper.startPage(pageNum,pageSize);
                    List<ProductListVO> productListVOList=Lists.newArrayList();
                    PageInfo pageInfo=new PageInfo(productListVOList);
                    return ServerResponse.responseSuccess(pageInfo);
                }

                ServerResponse serverResponse= categoryService.get_deep_category(categoryId);

                if(serverResponse.isSuccess()){
                    integerSet= (Set<Integer>) serverResponse.getData();

                }

            }
        //根据keyword
        if(keyword!=null&&!keyword.equals("")){
            keyword="%"+keyword+"%";
        }
        if(orderBy.equals("")){
            PageHelper.startPage(pageNum,pageSize);
        }else{
            String[] orderByArr=orderBy.split("_");
            if(orderByArr.length>1){
                PageHelper.startPage(pageNum,pageSize,orderByArr[0]+" "+orderByArr[1]);
            }else {
                PageHelper.startPage(pageNum,pageSize);
            }
        }

//List<Product>-->List<ProductListVO>
        List<Product> productList=productMapper.searchProduct(integerSet,keyword);
        List<ProductListVO> productListVOList=Lists.newArrayList();
        if(productList!=null&&productList.size()>0){
            for (Product product:productList
                 ) {
                ProductListVO productListVO=assembleProductListVO(product);
                productListVOList.add(productListVO);
            }
        }

            //分页
        PageInfo pageInfo=new PageInfo();
        pageInfo.setList(productListVOList);
            //返回
        return ServerResponse.responseSuccess(pageInfo);


    }


    private ProductListVO assembleProductListVO(Product product){
        ProductListVO productListVO=new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setMainImage(product.getMainImage());
        productListVO.setName(product.getName());
        product.setPrice(product.getPrice());
        productListVO.setStatus(product.getStatus());
        productListVO.setSubtitle(product.getSubtitle());

        return productListVO;

    }






    private ProductDetailVO assembleProductDetailVO(Product product){


        ProductDetailVO productDetailVO=new ProductDetailVO();
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setCreateTime(DateUtils.dateToStr(product.getCreateTime()));
        productDetailVO.setDetail(product.getDetail());
        /*productDetailVO.setImageHost("http://localhost:8080");*/
        productDetailVO.setName(product.getName());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setId(product.getId());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setSubImages(product.getSubImages());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setUpdateTime(DateUtils.dateToStr(product.getUpdateTime()));
       Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
       if(category!=null){
           productDetailVO.setParentCategoryId(category.getParentId());
       }else {
           productDetailVO.setParentCategoryId(0);
       }
        //productDetailVO.setParentCategoryId();

        return productDetailVO;
    }
}
