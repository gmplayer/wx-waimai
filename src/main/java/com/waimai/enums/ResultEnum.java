package com.waimai.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0,"成功"),
    PARA_ERROR(1,"参数不正确"),

    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_NOT_ENOUGH(11,"库存不足"),
    PRODUCT_STATUS_ERROR(12,"商品状态不正确"),

    ORDER_NOT_EXIST(20,"订单不存在"),
    ORDER_DETAIL_NOT_EXIST(21,"订单明细不存在"),
    ORDER_STATUS_ERROR(23,"订单状态不正确"),
    ORSER_UPDATE_ERROR(24,"订单更新失败"),
    ORDER_PAY_STATUS_ERROR(25,"订单支付状态不正确"),
    ORDER_CANCEL_SUCCESS(26,"订单取消成功"),
    CART_EMPTY(26,"购物车为空"),

    LOGIN_ERROR(27,"登录失败，登录信息不正确"),
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
