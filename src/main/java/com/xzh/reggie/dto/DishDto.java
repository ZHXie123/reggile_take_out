package com.xzh.reggie.dto;



import com.xzh.reggie.entity.Dish;
import com.xzh.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * 原本的dish不够用了，对dish类进行扩展
 */
@Data
public class DishDto extends  Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
