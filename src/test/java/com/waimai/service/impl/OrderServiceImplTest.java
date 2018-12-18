package com.waimai.service.impl;

import com.waimai.dto.OrderDTO;
import com.waimai.pojo.OrderDetail;
import com.waimai.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("gzy");
        orderDTO.setBuyerOpenId("abcd");
        orderDTO.setBuyerPhone("13800138000");
        orderDTO.setBuyerAddress("beijing");

        //购物车
        List<OrderDetail> list = new ArrayList<OrderDetail>();
        OrderDetail detail1 = new OrderDetail();
        detail1.setProductId("11111");
        detail1.setProductQuantity(2);

        OrderDetail detail2 = new OrderDetail();
        detail2.setProductId("22222");
        detail2.setProductQuantity(3);
        list.add(detail1);
        list.add(detail2);
        orderDTO.setOrderDetailList(list);

        OrderDTO result = orderService.create(orderDTO);
        log.info("result:{}",result);

    }

    @Test
    public void findOne() {
        OrderDTO dto  =orderService.findOne("1540174275312965772");
        log.info("orderDTO:{}",dto);
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> dtos  = orderService.findList("abcd",request);
        log.info("List<OrderDTO>:{}",dtos);
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finish() {
        OrderDTO dto  =orderService.findOne("1540174275312965772");
        orderService.finish(dto);
    }

    @Test
    public void pay() {
        OrderDTO dto  =orderService.findOne("1540176287936839836");
        orderService.pay(dto);
        log.info("pay:{}",dto);
    }

    @Test
    public void findAllList(){
        PageRequest pageRequest = new PageRequest(0,2);
       Page<OrderDTO> orderDTOPage =  orderService.findList(pageRequest);
       log.info("findList:{}",orderDTOPage.getContent());
        Assert.assertTrue("查询所有订单列表：",orderDTOPage.getTotalElements()<0);

    }
}