package com.xzh.reggie.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzh.reggie.entity.Setmeal;
import com.xzh.reggie.mapper.SetmealMapper;
import com.xzh.reggie.serivce.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
