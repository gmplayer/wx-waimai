package com.waimai.aspect;

import com.waimai.constant.CookieConstant;
import com.waimai.constant.RedisConstant;
import com.waimai.exception.SellerAuthException;
import com.waimai.utils.CookieUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.context.request.RequestContextHolder.getRequestAttributes;

@Aspect
@Component
public class SellAuthAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.waimai.controller.Seller*.*(..) )"+
              "&& !execution(public * com.waimai.controller.SellerUserController.*(..) )")
    public void verify(){}


    @Before("verify()")
    public void doVerify(){
       ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
       //c查询cookie
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if(cookie == null){
            throw new SellerAuthException();
        }
        //去redis查，没有token值 返回
        String tokenValue =  redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            throw  new SellerAuthException();
        }

    }
}
