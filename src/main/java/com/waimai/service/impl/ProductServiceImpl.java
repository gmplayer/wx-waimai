package com.waimai.service.impl;

import com.waimai.dao.ProductInfoRepos;
import com.waimai.dto.CartDTO;
import com.waimai.enums.ProductStatusEnum;
import com.waimai.enums.ResultEnum;
import com.waimai.exception.WaimaiException;
import com.waimai.pojo.ProductInfo;
import com.waimai.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepos repos;

    @Override
    public ProductInfo findOne(String productId) {

        return repos.findById(productId).orElse(null);

//        Optional<ProductInfo> productInfoOptional = repos.findById(productId);
//        if(productInfoOptional.isPresent()){
//            return productInfoOptional.get();
//        }else{
//           return productInfoOptional.orElse(null);
//        }
    }

    @Override
    public List<ProductInfo> findAll() {
        return repos.findAll();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repos.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repos.save(productInfo);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repos.findAll(pageable);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {

    }

    @Override
    @Transactional
    public void descreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
           ProductInfo productInfo =  this.findOne(cartDTO.getProductId());
           if(productInfo==null){
               throw new WaimaiException(ResultEnum.PRODUCT_NOT_EXIST);
           }
           Integer result = productInfo.getProductStock()-cartDTO.getProductQuantity();
           if(result<0){
               throw new WaimaiException(ResultEnum.PRODUCT_STOCK_NOT_ENOUGH);
           }
           productInfo.setProductStock(result);
           this.save(productInfo);
        }

    }

    @Override
    public void onSale(String productId) {
        ProductInfo productInfo  = repos.getOne(productId);
        if(productInfo == null){
            throw new WaimaiException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus() == ProductStatusEnum.UP.getCode()){
            throw new WaimaiException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        repos.save(productInfo);
    }

    @Override
    public void offSale(String productId) {

        ProductInfo productInfo  = repos.getOne(productId);
        if(productInfo == null){
            throw new WaimaiException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus() == ProductStatusEnum.DOWN.getCode()){
            throw new WaimaiException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        repos.save(productInfo);
    }
}
