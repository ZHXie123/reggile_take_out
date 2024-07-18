package com.xzh.reggie.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzh.reggie.common.CustomException;
import com.xzh.reggie.entity.Category;
import com.xzh.reggie.entity.Dish;
import com.xzh.reggie.entity.Setmeal;
import com.xzh.reggie.mapper.CategoryMapper;
import com.xzh.reggie.serivce.CategoryService;
import com.xzh.reggie.serivce.DishService;
import com.xzh.reggie.serivce.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatrgoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;



    //根据Id删除分类
    @Override
    public void remove(Long id){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();

        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);

        if(count1>0){
            //已经关联了菜品
            throw new CustomException("已经关联了菜品，不能删除");

        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();

        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);

        if(count2>0){
            //抛出异常
            throw new CustomException("当前分类关联了套餐，不能删除");
        }

        super.removeById(id);


    }

}
