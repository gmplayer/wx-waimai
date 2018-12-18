package com.waimai.service.impl;

import com.waimai.pojo.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo= productService.findOne("232323");
        log.info("ProductInfo:{}",productInfo);
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findUpAll() {
    }

    @Test
    public void save() {
    }

    @Test
    public void findAll1Page() {
        PageRequest pageRequest = new PageRequest(0,10);
        Page<ProductInfo> page = productService.findAll(pageRequest);
        System.out.println(page.getTotalElements());

    }
}