package com.wzn.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.wzn.common.ServerResponse;
import com.wzn.dao.ShoppingMapper;
import com.wzn.pojo.Shopping;
import com.wzn.service.IAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements IAddressService {
    @Resource
    ShoppingMapper shoppingMapper;
    @Override
    public ServerResponse add(Integer userId, Shopping shopping) {
        //参数非空判断
        if(shopping==null){
                return ServerResponse.responseErroe("参数错误");
        }
        //添加
        shopping.setUserId(userId);
        shoppingMapper.insert(shopping);
        //返回结果
        Map<String,Integer> map= Maps.newHashMap();
        map.put("shoppingId",shopping.getId());

        return ServerResponse.responseSuccess(map);
    }

    @Override
    public ServerResponse del(Integer userId, Integer shoppingId) {
        // 参数非空校验
        if(shoppingId==null){
            return ServerResponse.responseErroe("参数错误");
        }
        //删除
       int result= shoppingMapper.deleteByUserIdShoppingId(userId,shoppingId);
        // 返回结果
        if(result>0) {
            return ServerResponse.responseSuccess();
        }
            return ServerResponse.responseErroe("删除失败");




    }

    @Override
    public ServerResponse update(Shopping shopping) {
        //非空校验
        if(shopping==null){
            return ServerResponse.responseErroe("参数错误");
        }
        //更新
      int result=  shoppingMapper.updateBySelectiveKey(shopping);
        //返回结果
        if(result>0) {
            return ServerResponse.responseSuccess();
        }
        return ServerResponse.responseErroe("更新失败");




    }

    @Override
    public ServerResponse select(Integer shoppingId) {
        //参数非空校验

        //查询
      Shopping shopping=  shoppingMapper.selectByPrimaryKey(shoppingId);
        //

        return ServerResponse.responseSuccess(shopping);
    }

    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
       List<Shopping> shoppingList= shoppingMapper.selectAll();
        PageInfo pageInfo=new PageInfo(shoppingList);


        return ServerResponse.responseSuccess(pageInfo);
    }
}
