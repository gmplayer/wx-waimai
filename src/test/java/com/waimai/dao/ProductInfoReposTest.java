package com.waimai.dao;

import com.waimai.pojo.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoReposTest {

    @Autowired
    private ProductInfoRepos productInfoRepos;

    @Test
    public void save(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("宫保鸡丁");
        productInfo.setProductPrice(new BigDecimal(20.0));
        productInfo.setProductDescription("好鸡肉，好鸡丁");
        productInfo.setProductStock(100);
        productInfo.setProductIcon("/img/123.png");
        productInfo.setCategoryType(2);
        productInfo.setProductStatus(0);

        productInfoRepos.save(productInfo);

    }

    @Test
    public void findByProductStatus() {
    }
}