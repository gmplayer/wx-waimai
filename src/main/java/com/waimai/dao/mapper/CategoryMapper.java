package com.waimai.dao.mapper;


import com.waimai.pojo.ProductCategory;

import java.util.List;

public interface CategoryMapper {
    //使用xml配置文件
    ProductCategory getProductCategoryById(Integer category_id);

    int addProductCategory(ProductCategory productCategory);

    int updateProductCategory(ProductCategory productCategory);

    int delProductCategory(String categoryId);

    List<ProductCategory> getProductCategoryAll();

}
