package com.xzh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzh.reggie.common.Result;
import com.xzh.reggie.dto.DishDto;
import com.xzh.reggie.entity.Category;
import com.xzh.reggie.entity.Dish;
import com.xzh.reggie.serivce.CategoryService;
import com.xzh.reggie.serivce.DishFlavorService;
import com.xzh.reggie.serivce.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result<String> save(@RequestBody DishDto dishDto){

        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);

        return Result.success("新增菜品成功");
    }

    /**
     * 修改菜品功能
     * @param dishDto
     * @return
     */
    @PutMapping
    public Result<String> Update(@RequestBody DishDto dishDto){

        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);

        return Result.success("更新菜品成功");
    }

    /**
     * 菜品分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name){

        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();


        queryWrapper.like(name!=null,Dish::getName,name);

        queryWrapper.orderByDesc(Dish::getUpdateTime);

        dishService.page(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item)->{
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();

            Category category = categoryService.getById(categoryId);

            if(category!=null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());


        dishDtoPage.setRecords(list);

        return Result.success(dishDtoPage);
    }

    /**
     * 修改菜品回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DishDto> getPage(@PathVariable Long id){
//        System.out.println("id = " + id);
        DishDto byIdWithFlavo = dishService.getByIdWithFlavor(id);
        return Result.success(byIdWithFlavo);
    }

}
