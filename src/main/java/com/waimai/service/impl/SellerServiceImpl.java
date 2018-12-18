package com.waimai.service.impl;

import com.waimai.dao.SellerInfoRepos;
import com.waimai.pojo.SellerInfo;
import com.waimai.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerInfoRepos repos;
    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repos.findByOpenid(openid);
    }
}
