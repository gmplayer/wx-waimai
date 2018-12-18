package com.waimai.exception;

import com.waimai.enums.ResultEnum;

public class WaimaiException extends  RuntimeException {
    private Integer code;

    public WaimaiException(Integer code,String msg){
        super(msg);
        this.code = code;
    }

    public WaimaiException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
