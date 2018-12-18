package com.waimai.dao;

import com.waimai.pojo.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepos extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String openid);

}
