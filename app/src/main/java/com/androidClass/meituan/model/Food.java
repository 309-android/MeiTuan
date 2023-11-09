package com.androidClass.meituan.model;

import java.io.Serializable;

/**
 * 食物实体类
 */
public class Food implements Serializable {
    // 食物id
    private Integer id;

    // 店铺id
    private Integer storeId;

    // 分类id
    private Integer CategoryId;

    // 食物名字
    private String foodName;

    // 食物图片地址
    private String foodImg;

    // 食物价格
    private String foodAmount;

    // 食物月售
    private String monthSale;


    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", CategoryId=" + CategoryId +
                ", foodName='" + foodName + '\'' +
                ", foodImg='" + foodImg + '\'' +
                ", foodAmount='" + foodAmount + '\'' +
                ", monthSale='" + monthSale + '\'' +
                '}';
    }

    public Integer getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    public String getMonthSale() {
        return monthSale;
    }

    public void setMonthSale(String monthSale) {
        this.monthSale = monthSale;
    }

    public Food() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }

    public String getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(String foodAmount) {
        this.foodAmount = foodAmount;
    }
}
