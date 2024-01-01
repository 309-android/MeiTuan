package com.androidClass.meituan.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
public class Order implements Serializable {
    // 订单id
    private Integer id;

    //用户id
    private Integer userId;

    //食物id
    private Integer foodId;

    //店铺id
    private Integer storeId;

    //订单金额
    private Double orderAmount;

    //订单状态
    private String status;

    // 订单编号
    private String code;

    // 订单时间
    private LocalDateTime orderTime;

    // 订单包含食物
    private Food food;

    // 订单店铺图
    private Store store;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", foodId=" + foodId +
                ", storeId=" + storeId +
                ", orderAmount=" + orderAmount +
                ", status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", orderTime=" + orderTime +
                ", food=" + food +
                ", store=" + store +
                '}';
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public Order() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }
}
