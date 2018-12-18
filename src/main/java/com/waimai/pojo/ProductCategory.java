package com.waimai.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

//@Table(value="s_product_category")  如果类名和表名不一致使用@Table注解
@Entity
@DynamicUpdate
/*
* 如果ProductCategory里面声明了create_time和update_time属性 则更新的时候，如果不赋值是不会自从更新update_time时间的。
* 如果定义了create_time和update_time属性，还要update_time自动更新，则需要使用注解@DynamicUpdate,同时更新的记录还要和原记录数据要由差异，如果完全一致，则jpa不会自动更新该记录
* */
@Data  //如果不希望使用setter和getter方法，只使用属性，可以定义@Data这个注解
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer categoryId;
    private String categoryName;
    private Integer categoryType;

    public ProductCategory(){}

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
                '}';
    }
}
