package com.waimai.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.waimai.enums.OrderStatusEnum;
import com.waimai.enums.PayStatusEnum;
import com.waimai.pojo.OrderDetail;
import com.waimai.utils.EnumUtil;
import com.waimai.utils.serializer.DateToLong;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//不为null的字段返回，为null不返回
public class OrderDTO {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenId;
    private BigDecimal orderAmount;
    private Integer orderStatus;
    private Integer payStatus ;

    @JsonSerialize(using= DateToLong.class)//使用DateToLong类将date类型转换为long
    private Date createTime;
    @JsonSerialize(using= DateToLong.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList = new ArrayList<>();

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
