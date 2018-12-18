package com.waimai.service.impl;

import com.waimai.pojo.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Test
    public void findOne() {
        ProductCategory productCategory = categoryServiceImpl.findOne(10);

        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> list = categoryServiceImpl.findAll();
        Assert.assertNotNull(list);
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> list  = categoryServiceImpl.findByCategoryTypeIn(Arrays.asList(1,2,3,4));
        Assert.assertNotNull(list);

    }

    @Test
    public void save() {
        ProductCategory pc = new ProductCategory("主食", 4);
        categoryServiceImpl.save(pc);

        Assert.assertNotEquals(new Integer(0), pc.getCategoryId());

    }
}