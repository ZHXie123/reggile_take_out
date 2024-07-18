package com.xzh.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzh.reggie.common.Result;
import com.xzh.reggie.entity.Employee;
import com.xzh.reggie.serivce.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.core.conditions.query.Query;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        String password = employee.getPassword();

        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        if(emp == null){
            return Result.error("登录失败");
        }

        if(!emp.getPassword().equals(password)){
            return Result.error("登录失败");
        }

        if(emp.getStatus()==0){
            return Result.error("账号已禁用");
        }

        request.getSession().setAttribute("employee",emp.getId());

        return Result.success(emp);
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    //新增员工
    @PostMapping
    public Result<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，员工信息{}",employee.toString());

        //设置初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        //employee.setCreateTime(LocalDateTime.now());

        //employee.setUpdateTime(LocalDateTime.now());

        //Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);

        employeeService.save(employee);


        return Result.success("新增员工成功");
    }

    //员工信息分页查询
    @GetMapping("/page")
    public Result<Page> page(int page,int pageSize,String name){

        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);

        Page pageInfo = new Page(page,pageSize);
        
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);

        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo,queryWrapper);

        return Result.success(pageInfo);
    }

    @PutMapping
    public Result<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());

//        employee.setUpdateTime(LocalDateTime.now());
//        Long empId = (Long)request.getSession().getAttribute("employee");
//        employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return Result.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if(employee!=null)return Result.success(employee);
        return Result.error("没有查询到员工信息");
    }

}
