package com.waimai.dao;

import com.waimai.pojo.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;


import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryReposTest {

    @Autowired
    private ProductCategoryRepos productCategoryRepos;

    @Test
    public void findOneTest(){
       ProductCategory pc =  productCategoryRepos.findById(1).get();
        System.out.println(pc.toString());
    }

    @Test
    @Transactional //测试完成将添加的数据删除，不真正添加留有脏数据
    public void saveTest(){
//        ProductCategory pc = new ProductCategory();
//        pc.setCategoryName("米线");
//        pc.setCategoryType(2);
//         productCategoryRepos.save(pc);
    }

    @Test
    public void updateTest(){
        ProductCategory pc =productCategoryRepos.findById(2).get();

        pc.setCategoryType(3);
        productCategoryRepos.save(pc);

    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(1,2,3,4);
        List<ProductCategory> result = productCategoryRepos.findByCategoryTypeIn(list);
        System.out.println(result.size()+"===============");
        Assert.assertNotNull(result);
        Assert.assertNotEquals(0,result.size());
    }

}