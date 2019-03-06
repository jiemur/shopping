package com.wzn.service.impl;

import com.google.common.collect.Sets;
import com.wzn.common.ServerResponse;
import com.wzn.dao.CategoryMapper;
import com.wzn.pojo.Category;
import com.wzn.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Resource
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse get_category(Integer categoryId) {

        //非空校验
        if (categoryId == null) {
            return ServerResponse.responseErroe("参数不能为空");
        }
        //根据categoryId查询类别
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null) {
            return ServerResponse.responseErroe("查询类别不存在");
        }
        //查询子类别
        List<Category> categoryList = categoryMapper.findChildCategory(categoryId);
        //返回结果

        return ServerResponse.responseSuccess(categoryList);
    }

    @Override
    public ServerResponse add_category(Integer parentId, String categoryName) {
        //参数校验
        if (categoryName == null) {
            return ServerResponse.responseErroe("类别名称不能为空");
        }
        //添加节点
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(1);
        int result = categoryMapper.insert(category);
        //返回结果
        if (result > 0) {
            return ServerResponse.responseSuccess();

        }
        return ServerResponse.responseErroe("添加失败");


    }

    @Override
    public ServerResponse set_category_name(Integer categoryId, String categoryName) {
        //参数非空校验
        if (categoryName == null) {
            return ServerResponse.responseErroe("类别名称不能为空");
        }
        if (categoryId == null) {
            return ServerResponse.responseErroe("类别id不能为空");
        }
        //根据categoryId查询
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null) {
            return ServerResponse.responseErroe("要修改的类别不存在");
        }
        //修改
        category.setName(categoryName);
        int result = categoryMapper.updateByPrimaryKey(category);
        //返回结果
        if (result > 0) {
            return ServerResponse.responseSuccess();
        }
        return ServerResponse.responseErroe("修改失败");
    }

    @Override
    public ServerResponse get_deep_category(Integer categoryId) {
        //参数的非空校验
        if(categoryId==null){
            return ServerResponse.responseErroe("类别id不能为空");
        }


        //查询
        Set<Category> categorySet= Sets.newHashSet();
        categorySet= findAllChildCategory(categorySet,categoryId);

        Set<Integer> integerSet=Sets.newHashSet();

       Iterator<Category> categoryIterator= categorySet.iterator();
       while (categoryIterator.hasNext()){
           Category category=categoryIterator.next();
           integerSet.add(category.getId());
       }
        return ServerResponse.responseSuccess(integerSet);
    }

    private Set<Category> findAllChildCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);//id

        }
        //查找categoryId下的子节点(平级)
        List<Category> categoryList= categoryMapper.findChildCategory(categoryId);
        if(categoryList!=null&&categoryList.size()>0){
            for(Category category1:categoryList){
                findAllChildCategory(categorySet,category1.getId());
            }
        }
        return categorySet;
    }
}
