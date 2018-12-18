package com.waimai.controller;

import com.waimai.converter.OrderFormToOrderDTO;
import com.waimai.dto.OrderDTO;
import com.waimai.enums.ResultEnum;
import com.waimai.exception.WaimaiException;
import com.waimai.form.OrderForm;
import com.waimai.service.OrderService;
import com.waimai.utils.ResultUtil;
import com.waimai.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value="buyer/order")
public class BuyOrderController {
    @Autowired
    private OrderService orderService;
    //创建订单
    @RequestMapping(value="/create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new WaimaiException(ResultEnum.PARA_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO dto = OrderFormToOrderDTO.convert(orderForm);
        if(CollectionUtils.isEmpty(dto.getOrderDetailList())){
            log.error("购物车为空");
            throw new WaimaiException(ResultEnum.ORDER_STATUS_ERROR);
        }
        OrderDTO result =  orderService.create(dto);
        Map<String,String> map = new HashMap<String,String>();
        map.put("orderId",result.getOrderId());
        return ResultUtil.success(map);
    }
    //订单列表

    @RequestMapping(value="/list")
    public ResultVo<OrderDTO> list(@RequestParam("openid") String openid,
                                   @RequestParam(value="page",defaultValue = "0") Integer page,
                                   @RequestParam(value="size",defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            throw new WaimaiException(ResultEnum.PARA_ERROR);
        }

        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> result =orderService.findList(openid,pageRequest);
        return ResultUtil.success(result.getContent());

    }
    //查询订单详情
    @RequestMapping(value="/detail")
    public ResultVo<OrderDTO>  detail(@RequestParam("openid") String openid,@RequestParam("orderId") String orderId){
        OrderDTO dto  = new OrderDTO();
        dto  = orderService.findOne(orderId);
        //TODO 需要判断
        if(!dto.getBuyerOpenId().equals(openid)){//判断如果不是该openid的订单
            throw new WaimaiException(ResultEnum.PARA_ERROR);
        }
        return ResultUtil.success(dto);



    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVo  cancel(@RequestParam("openid") String openid,@RequestParam("orderId") String orderId){
        OrderDTO dto =  orderService.findOne(orderId);
        orderService.cancel(dto);
        return ResultUtil.success();

    }
    //完成订单
}
