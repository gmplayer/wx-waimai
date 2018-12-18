package com.waimai.controller;

import com.waimai.constant.CookieConstant;
import com.waimai.constant.RedisConstant;
import com.waimai.enums.ResultEnum;
import com.waimai.pojo.SellerInfo;
import com.waimai.service.SellerService;
import com.waimai.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller/user")
public class SellerUserController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map){
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if(sellerInfo == null ){
            map.put("message", ResultEnum.LOGIN_ERROR);
            map.put("url","/seller/order/list");
            return new ModelAndView("/common/error",map);
        }
        //2. 写入redis
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,RedisConstant.EXPIRE, TimeUnit.SECONDS);

        //3.设置token到cookie
        CookieUtil.setCookie(response, CookieConstant.TOKEN,token,CookieConstant.EXPIRE);


        return new ModelAndView("redirect:/seller/order/list");

    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {
        //1.查询cookie
        Cookie cookie =  CookieUtil.getCookie(request,CookieConstant.TOKEN);
        if(cookie!=null){
            //2.清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //3.清除cookie
            CookieUtil.setCookie(response,CookieConstant.TOKEN,null,0);

        }
        map.put("message","登出成功");
        return new ModelAndView("/seller/order/list",map);
    }

}
