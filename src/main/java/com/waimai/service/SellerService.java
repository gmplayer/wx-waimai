package com.waimai.service;

import com.waimai.pojo.SellerInfo;

public interface SellerService {
    SellerInfo findSellerInfoByOpenid(String openid);
}
