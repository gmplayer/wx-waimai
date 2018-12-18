package com.waimai.dao.mapper;

import com.waimai.pojo.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryMapperTest {
    @Autowired
    private  CategoryMapper categoryMapper;

    @Test
    public void getProductCategoryById() {
       ProductCategory category =  categoryMapper.getProductCategoryById(1);
        System.out.println("查询产品类别对象："+category.getCategoryName());
    }

    @Test
    public void addProductCategory() {
    }

    @Test
    public void updateProductCategory() {
    }

    @Test
    public void delProductCategory() {
    }

    @Test
    public void getProductCategoryAll() {
    }
}