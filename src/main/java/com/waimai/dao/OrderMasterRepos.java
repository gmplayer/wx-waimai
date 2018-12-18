package com.waimai.dao;

import com.waimai.pojo.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepos extends JpaRepository<OrderMaster,String> {

    Page<OrderMaster> findByBuyerOpenId(String buyerOpenid, Pageable pageable);
}
