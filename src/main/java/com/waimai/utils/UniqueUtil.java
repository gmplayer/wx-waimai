package com.waimai.utils;

import java.util.Random;

public class UniqueUtil {
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        Integer a  = random.nextInt(900000)+100000;
        return System.currentTimeMillis()+ String.valueOf(a);
    }
}
