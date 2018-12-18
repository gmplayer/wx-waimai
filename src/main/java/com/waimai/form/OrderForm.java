package com.waimai.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderForm {
    @NotEmpty(message="姓名必填")
    private String name;
    @NotEmpty(message = "电话必填")
    private String phone;
    @NotEmpty(message = "地址必填")
    private  String address;
    @NotEmpty(message = "openid必填")
    private String openid;
    @NotEmpty(message = "订单明细必填")
    private String items;

}
