package com.waimai.converter;

import com.waimai.dto.OrderDTO;
import com.waimai.pojo.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMasterToOrderDTO {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO dto  = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,dto);
        return dto;

    }

    public static List<OrderDTO>  convert(List<OrderMaster> orderMasterList){
        return  orderMasterList.stream().map(e->convert(e)).collect(Collectors.toList());
    }
}
