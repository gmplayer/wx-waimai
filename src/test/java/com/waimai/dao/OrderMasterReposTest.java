package com.waimai.dao;

import com.waimai.pojo.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterReposTest {
    @Autowired
    private OrderMasterRepos repos;

    @Test
    public void save(){
        OrderMaster order = new OrderMaster();
        order.setOrderId("123456");
        order.setBuyerName("gzy");
        order.setBuyerPhone("13810630033");
        order.setBuyerAddress("beijing");
        order.setBuyerOpenId("abcd");
        order.setOrderAmount(new BigDecimal(99.0));

        OrderMaster result = repos.save(order);
        Assert.assertNotNull(result);

    }
    @Test
    public void findByBuyerOpenId() throws Exception{

        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderMaster> page = repos.findByBuyerOpenId("abcd",pageRequest);
        System.out.println(page.getTotalElements());

    }
}