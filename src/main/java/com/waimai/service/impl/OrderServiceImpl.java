package com.waimai.service.impl;

import com.waimai.converter.OrderMasterToOrderDTO;
import com.waimai.dao.OrderDetailRepos;
import com.waimai.dao.OrderMasterRepos;
import com.waimai.dto.CartDTO;
import com.waimai.dto.OrderDTO;
import com.waimai.enums.OrderStatusEnum;
import com.waimai.enums.PayStatusEnum;
import com.waimai.enums.ResultEnum;
import com.waimai.exception.WaimaiException;
import com.waimai.pojo.OrderDetail;
import com.waimai.pojo.OrderMaster;
import com.waimai.pojo.ProductInfo;
import com.waimai.service.OrderService;
import com.waimai.service.ProductService;
import com.waimai.utils.UniqueUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMasterRepos orderMasterRepos;
    @Autowired
    private OrderDetailRepos orderDetailRepos;
    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount = new BigDecimal(0);//订单总价
        String orderId = UniqueUtil.getUniqueKey();

         //List<CartDTO> cartDTOList = new ArrayList<CartDTO>(); 可以通过在查询单价遍历订单明细的时候构造该对象，但是这样查询商品单价这个循环里面的功能就不集中。设计上不太合理，可以通过lambda表达式单独构造，后面
        //1.查询商品单价等信息
        for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo  = productService.findOne(orderDetail.getProductId());
            if(productInfo==null){
                throw new WaimaiException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算总价
            orderAmount = productInfo.getProductPrice()
                         .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                         .add(orderAmount);

//            OrderDTO orderDTO  = new OrderDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
//            cartDTOList.add(orderDTO);

            orderDetail.setDetailId(UniqueUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepos.save(orderDetail);

        }

        //3.写入订单
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);//先拷贝 后赋值

        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT_PAY.getCode());

        orderMasterRepos.save(orderMaster);

        List<CartDTO> cartDTOList = new ArrayList<CartDTO>();
        cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());



        //4.下单成功，减少库存
        productService.descreaseStock(cartDTOList);
        return orderDTO;

    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepos.findById(orderId).orElse(null);
        if(orderMaster ==null){
            throw  new WaimaiException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepos.findByOrderId(orderId);
        if(orderDetailList==null){
            throw new WaimaiException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterList = orderMasterRepos.findByBuyerOpenId(buyerOpenid,pageable);
        long total =  orderMasterList.getTotalElements();
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTO.convert(orderMasterList.getContent());
        Page<OrderDTO> page =new PageImpl<OrderDTO>(orderDTOList,pageable,total);

        return page;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
       //1.判断订单状
        if(orderDTO.getOrderStatus().equals(OrderStatusEnum.CANCEL.getCode()) ){
            throw new WaimaiException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        OrderMaster orderMaster = orderMasterRepos.findById(orderDTO.getOrderId()).orElse(null);
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster updateResult =  orderMasterRepos.save(orderMaster);
        if(updateResult==null){
            throw  new WaimaiException(ResultEnum.ORSER_UPDATE_ERROR);
        }
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());//设置更新订单状态
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //1.判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()) ){
            throw new WaimaiException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        OrderMaster orderMaster = orderMasterRepos.findById(orderDTO.getOrderId()).orElse(null);
        orderMaster.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        OrderMaster updateResult =  orderMasterRepos.save(orderMaster);
        if(updateResult==null){
            throw  new WaimaiException(ResultEnum.ORSER_UPDATE_ERROR);
        }
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());//设置更新订单状态
        return orderDTO;


    }

    @Override
    @Transactional
    public OrderDTO pay(OrderDTO orderDTO) {
        //1.判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new WaimaiException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.检车支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT_PAY.getCode())){
            throw new WaimaiException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        OrderMaster orderMaster = orderMasterRepos.findById(orderDTO.getOrderId()).orElse(null);
        orderMaster.setPayStatus(PayStatusEnum.HAS_PAY.getCode());
        OrderMaster updateResult =  orderMasterRepos.save(orderMaster);
        if(updateResult==null){
            throw  new WaimaiException(ResultEnum.ORSER_UPDATE_ERROR);
        }
        orderDTO.setPayStatus(PayStatusEnum.HAS_PAY.getCode());//设置更新订单状态
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> masterList = orderMasterRepos.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTO.convert(masterList.getContent());
        return new PageImpl<>(orderDTOList,pageable,masterList.getTotalElements());

    }
}
