package com.waimai.handler;

import com.waimai.exception.SellerAuthException;
import com.waimai.exception.WaimaiException;
import com.waimai.utils.ResultUtil;
import com.waimai.vo.ResultVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class WaimaiExceptionHandler {

    @ExceptionHandler(value= WaimaiException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultVo handlerWaimaiException(WaimaiException e){
        return ResultUtil.fail(e.getCode(),e.getMessage());

    }

    //拦截登录异常的
    @ExceptionHandler(value=SellerAuthException.class)
    public ModelAndView handlerAuthException(SellerAuthException e){
        return new ModelAndView("redirect:/seller/user/index");

    }
}
