package com.xzh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzh.reggie.common.Result;
import com.xzh.reggie.entity.Category;
import com.xzh.reggie.serivce.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping
    public Result<String> save(@RequestBody Category category){
        log.info("category{}",category);
        categoryService.save(category);
        return Result.success("新增分类成功");
    }

    @GetMapping("/page")
    public Result<Page> page(int page,int pageSize){

        Page<Category> pageInfo = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo,queryWrapper);

        return Result.success(pageInfo);
    }

    @DeleteMapping
    public Result<String> delete(Long id){
        log.info("删除分类,id为{}",id);

        //categoryService.removeById(id);
        categoryService.remove(id);

        return Result.success("分类信息删除成功");
    }

    @PutMapping
    public Result<String> update(@RequestBody Category category){
        log.info("修改分类信息{}",category);

        categoryService.updateById(category);
        return Result.success("修改信息成功"); 
    }

    @GetMapping("/list")
    public Result<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());

        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);

        return Result.success(list);
    }
}
