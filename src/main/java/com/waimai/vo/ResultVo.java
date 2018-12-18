package com.waimai.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//属性为null的不返回
public class ResultVo<T> {
    private Integer code;
    private String msg="";
    private T data;
}
