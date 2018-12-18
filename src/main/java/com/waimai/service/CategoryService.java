package com.waimai.service;

import com.waimai.pojo.ProductCategory;
import io.lettuce.core.output.IntegerOutput;

import java.util.List;

public interface CategoryService {

     ProductCategory  findOne(Integer categoryId);
     List<ProductCategory> findAll();
     List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);
     ProductCategory save(ProductCategory productCategory);
}
