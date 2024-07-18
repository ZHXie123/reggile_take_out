package com.xzh.reggie.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzh.reggie.entity.DishFlavor;
import com.xzh.reggie.mapper.DishFlavorMapper;
import com.xzh.reggie.serivce.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
