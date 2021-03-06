package com.waimai.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum implements  CodeEnum{

    WAIT_PAY(0,"未支付"),
    HAS_PAY(1,"已经支付")

    ;
    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
