package com.xzh.reggie.serivce.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzh.reggie.dto.DishDto;
import com.xzh.reggie.entity.Dish;
import com.xzh.reggie.entity.DishFlavor;
import com.xzh.reggie.mapper.DishMapper;
import com.xzh.reggie.serivce.DishFlavorService;
import com.xzh.reggie.serivce.DishService;
import com.xzh.reggie.serivce.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {


    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     *
     * @param dishDto
     */
    @Transient
    public void saveWithFlavor(DishDto dishDto){

        this.save(dishDto);

        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

    }

    /**
     * 根据Id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
//        根据ID获取dish的一般属性
        Dish dish = this.getById(id);

//        声明一个空的dishDto
        DishDto dishDto = new DishDto();

//        复制普通属性到dishDto
        BeanUtils.copyProperties(dish,dishDto);

//        准备查询条件
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(DishFlavor::getDishId,id);

        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
//将口味也添加入dishDto中
        dishDto.setFlavors(flavors);
//        返回dishDto
        return dishDto;
    }

    /**
     * 更新菜品基本信息表和口味表
     * @param dishDto
     */
    @Transient
    public void updateWithFlavor(DishDto dishDto) {
//        更新基本信息
        this.updateById(dishDto);

//        删除原本的flavor信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);

//        插入新的flavor信息
        List<DishFlavor> flavors = dishDto.getFlavors();
//        获取菜品Id
        Long dishId = dishDto.getId();

//        因为flavor中只有name和value，没有dishId这样的数据插入数据库
//        没有意义，所以添加一个维度dish_id
        flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

    }
}
