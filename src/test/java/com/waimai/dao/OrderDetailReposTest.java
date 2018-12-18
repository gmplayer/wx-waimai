package com.waimai.dao;

import com.waimai.pojo.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailReposTest {
    @Autowired
    private OrderDetailRepos repos;

    @Test
    public void save(){
        OrderDetail detail = new OrderDetail();
        detail.setDetailId("aaaa");
        detail.setOrderId("12345");
        detail.setProductId("11111");
        detail.setProductName("宫保鸡丁");
        detail.setProductPrice(new BigDecimal(20));
        detail.setProductQuantity(2);
        detail.setProductIcon("/img/123.png");
         OrderDetail result =  repos.save(detail);
         Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
       List<OrderDetail> list =   repos.findByOrderId("12345");
        Assert.assertNotNull(list);
    }
}