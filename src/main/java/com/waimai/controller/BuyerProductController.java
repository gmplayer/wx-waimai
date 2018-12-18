package com.waimai.controller;

import com.waimai.pojo.ProductCategory;
import com.waimai.pojo.ProductInfo;
import com.waimai.service.CategoryService;
import com.waimai.service.ProductService;
import com.waimai.utils.ResultUtil;
import com.waimai.vo.ProductInfoVo;
import com.waimai.vo.ProductVo;
import com.waimai.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public ResultVo list(){
        //1.查询所有商品
        List<ProductInfo> productList = productService.findAll();

        //2.查询所有类目,根据查询出来的商品的类目id
//        List<Integer> categoryTypeList = new ArrayList<Integer>();
//
//        for(ProductInfo productInfo:productList){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        //lambda表达式
        //List<Integer> categoryTypeList = productList.stream().map(e->e.getCategoryType()).collect(Collectors.toList());
        //List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        List<ProductCategory> categoryList = categoryService.findAll();

         //3.数据封装
        List<ProductVo> list = new ArrayList<ProductVo>();
        for(ProductCategory productCategory:categoryList){
            ProductVo productVo = new ProductVo();

            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVo> productInfoVos= new ArrayList<ProductInfoVo>();
            for(ProductInfo productInfo:productList){
                if(productCategory.getCategoryType().equals(productInfo.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVos.add(productInfoVo);
                }

            }
            productVo.setProductInfoVoList(productInfoVos);
            list.add(productVo);


        }


        return ResultUtil.success(list);

    }
}
