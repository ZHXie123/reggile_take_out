package com.xzh.reggie.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzh.reggie.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
