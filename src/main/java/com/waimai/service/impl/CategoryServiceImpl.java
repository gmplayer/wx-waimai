package com.waimai.service.impl;

import com.waimai.dao.ProductCategoryRepos;
import com.waimai.pojo.ProductCategory;
import com.waimai.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryRepos repos;

    @Override
    public ProductCategory findOne(Integer categoryId) {

        return  repos.findById(categoryId).get();

    }

    @Override
    public List<ProductCategory> findAll() {
       return repos.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType) {
        return repos.findByCategoryTypeIn(categoryType);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repos.save(productCategory);
    }
}
