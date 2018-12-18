package com.waimai.utils;

import com.waimai.vo.ResultVo;

public class ResultUtil {

    public static ResultVo success(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("ok");
        resultVo.setData(object);
        return resultVo;

    }

    public static ResultVo success(){
        return success(null);
    }

    public static ResultVo fail(Integer code,String msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;

    }



}
