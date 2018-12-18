package com.waimai.controller;

import com.waimai.dto.OrderDTO;
import com.waimai.enums.ProductStatusEnum;
import com.waimai.enums.ResultEnum;
import com.waimai.form.ProductForm;
import com.waimai.pojo.ProductCategory;
import com.waimai.pojo.ProductInfo;
import com.waimai.service.CategoryService;
import com.waimai.service.ProductService;
import com.waimai.utils.UniqueUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value="page",defaultValue = "0") Integer page,
                             @RequestParam(value="size",defaultValue = "1") Integer size){
        Integer currPage = page>=1?(page-1):0;
        PageRequest pageRequest = new PageRequest(currPage,size);
        Page<ProductInfo>  productInfoPage = productService.findAll(pageRequest);
        Map<String,Object> map = new HashMap<>();
        map.put("productInfoPage",productInfoPage);
        map.put("currpage",currPage);
        map.put("size",size);
        return new ModelAndView("/product/list",map);

    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value="productId",required = false) String productId){
        Map<String, Object> map = new HashMap<>();
        try {
            if(!StringUtils.isEmpty(productId)){
               ProductInfo productInfo =   productService.findOne(productId);
                map.put("productInfo",productInfo);
            }
            //查询所有类目
            List<ProductCategory> categoryList =  categoryService.findAll();
            map.put("categoryList",categoryList);

            return new ModelAndView("/product/index",map);


        }catch (Exception e){
            log.info("取消订单错误，查不到订单{}", productId);

            map.put("message", ResultEnum.ORDER_NOT_EXIST.getMsg());
            map.put("url", "seller/order/list");
            return new ModelAndView("common/error", map);

        }


    }

    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId) {
        Map<String, Object> map = new HashMap<>();
        try {
            productService.onSale(productId);
        }catch (Exception e){
            log.info("上架错误，查不到商品{}", productId);

            map.put("message", e.getMessage());
            map.put("url", "seller/product/list");
            return new ModelAndView("common/error", map);

        }
        map.put("message", ResultEnum.SUCCESS.getMsg());
        map.put("url", "seller/product/list");
        return new ModelAndView("/common/success",map);


    }

    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId) {
        Map<String, Object> map = new HashMap<>();
        try {
            productService.offSale(productId);
        }catch (Exception e){
            log.info("下架错误，查不到商品{}", productId);

            map.put("message", e.getMessage());
            map.put("url", "seller/product/list");
            return new ModelAndView("common/error", map);

        }
        map.put("message", ResultEnum.SUCCESS.getMsg());
        map.put("url", "seller/product/list");
        return new ModelAndView("/common/success",map);


    }


    @PostMapping(value= "/save")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult,
                             Map<String, Object> map){
        if(bindingResult.hasErrors()){
            map.put("message", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "seller/product/index");
            return new ModelAndView("common/error", map);

        }


        try {
            ProductInfo productInfo = new ProductInfo();
            if(!StringUtils.isEmpty(productForm.getProductId())){
                productInfo  = productService.findOne(productForm.getProductId());
            }else{
                productForm.setProductId(UniqueUtil.getUniqueKey());
            }


            BeanUtils.copyProperties(productForm,productInfo);
            productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
            productService.save(productInfo);
        }catch (Exception e){
            map.put("message", e.getMessage());
            map.put("url", "seller/product/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url","seller/product/list");
        return new ModelAndView("common/success", map);


    }



}
