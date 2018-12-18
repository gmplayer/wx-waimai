package com.waimai.utils;

import com.waimai.constant.RedisConstant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {

    public static void setCookie(HttpServletResponse response,String name,String value,int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static Cookie getCookie(HttpServletRequest request,String name){
        Map<String,Cookie>  map =  readCookieMap(request);
        if(map.containsKey(name)){
            return map.get(name);
        }
        else{
            return null;
        }

    }

    private static Map<String,Cookie> readCookieMap(HttpServletRequest request){
        Map<String,Cookie> map = new HashMap<>();
       Cookie[] cookies =  request.getCookies();
       if(cookies!=null){
           for(Cookie cookie:cookies){
               map.put(cookie.getName(),cookie);
           }
       }

       return map;

    }
}
