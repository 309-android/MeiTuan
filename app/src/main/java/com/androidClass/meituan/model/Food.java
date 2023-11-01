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

    // 食物名字
    private String foodName;

    // 食物图片地址
    private String foodImg;

    // 食物价格
    private String foodAmount;


    public Food() {
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", foodName='" + foodName + '\'' +
                ", foodImg='" + foodImg + '\'' +
                ", foodAmount='" + foodAmount + '\'' +
                '}';
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
