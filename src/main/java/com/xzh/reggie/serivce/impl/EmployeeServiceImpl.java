package com.xzh.reggie.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzh.reggie.entity.Employee;
import com.xzh.reggie.mapper.EmployeeMapper;
import com.xzh.reggie.serivce.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
