package com.waimai.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.waimai.dto.CartDTO;
import com.waimai.dto.OrderDTO;
import com.waimai.enums.ResultEnum;
import com.waimai.exception.WaimaiException;
import com.waimai.form.OrderForm;
import com.waimai.pojo.OrderDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderFormToOrderDTO {

    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO dto = new OrderDTO();
        dto.setBuyerName(orderForm.getName());
        dto.setBuyerPhone(orderForm.getPhone());
        dto.setBuyerAddress(orderForm.getAddress());
        dto.setBuyerOpenId(orderForm.getOpenid());
        List<OrderDetail> list = new ArrayList<>();
        try {

            list = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {}.getType() );
        }catch (Exception e){
            log.error("转换错误：{}",orderForm.getItems());
            throw  new WaimaiException(ResultEnum.PARA_ERROR);
        }
        dto.setOrderDetailList(list);
        return dto;

    }
}
