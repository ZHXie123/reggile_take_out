package com.xzh.reggie.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzh.reggie.dto.DishDto;
import com.xzh.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);
}
