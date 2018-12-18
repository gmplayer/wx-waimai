package com.waimai.controller;

import com.waimai.dto.OrderDTO;
import com.waimai.enums.ResultEnum;
import com.waimai.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/seller/order")
@Slf4j
public class SellerController {
    @Autowired
    private OrderService orderService;

    /*
    * 订单列表
    * */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value="page",defaultValue = "0") Integer page,
                                @RequestParam(value="size",defaultValue = "1") Integer size){

        PageRequest pageRequest = new PageRequest(page>=1?(page-1):0,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        Map<String ,Object> map = new HashMap<>();
        map.put("orderDTOPage",orderDTOPage);
        map.put("currpage",page);
        log.info("总共页数：{}",orderDTOPage.getTotalPages());
        log.info("记录：{}",orderDTOPage.getContent());
        return new ModelAndView("order/list",map);


    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId){
        Map<String, Object> map = new HashMap<>();
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }catch (Exception e){
            log.info("取消订单错误，查不到订单{}", orderId);

            map.put("message", e.getMessage());
            map.put("url", "seller/order/list");
            return new ModelAndView("common/error", map);

        }
        map.put("message", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url", "seller/order/list");
        return new ModelAndView("/common/success",map);

    }

}
