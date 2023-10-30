package com.androidClass.meituan.model;


import java.io.Serializable;


public class Store implements Serializable {

    // 店铺id
    private int id;
    // 店铺名字
    private String storeName;
    // 店铺图片名
    private String Image;
    // 店铺评分
    private String storeScore;
    // 店铺月售
    private int monthSale;
    // 店铺人均价格
    private int peopleAvg;
    // 店铺起送价格
    private int minTakeOutNum;
    // 配送价格
    private int deliveryNum;
    // 店铺评价
    private String comment;


    public Store() {
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", Image='" + Image + '\'' +
                ", storeScore='" + storeScore + '\'' +
                ", monthSale=" + monthSale +
                ", peopleAvg=" + peopleAvg +
                ", minTakeOutNum=" + minTakeOutNum +
                ", deliveryNum=" + deliveryNum +
                ", comment='" + comment + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getStoreScore() {
        return storeScore;
    }

    public void setStoreScore(String storeScore) {
        this.storeScore = storeScore;
    }

    public int getMonthSale() {
        return monthSale;
    }

    public void setMonthSale(int monthSale) {
        this.monthSale = monthSale;
    }

    public int getPeopleAvg() {
        return peopleAvg;
    }

    public void setPeopleAvg(int peopleAvg) {
        this.peopleAvg = peopleAvg;
    }

    public int getMinTakeOutNum() {
        return minTakeOutNum;
    }

    public void setMinTakeOutNum(int minTakeOutNum) {
        this.minTakeOutNum = minTakeOutNum;
    }

    public int getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(int deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
