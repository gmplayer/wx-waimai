package com.waimai.service;

import com.waimai.dto.CartDTO;
import com.waimai.pojo.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);
    List<ProductInfo> findAll();
    List<ProductInfo> findUpAll();
    ProductInfo save(ProductInfo productInfo);
    Page<ProductInfo> findAll(Pageable pageable);
    //加库存
    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    void descreaseStock(List<CartDTO> cartDTOList);


    //上架
    void onSale(String productId);
    //下架
    void offSale(String productId);

}
