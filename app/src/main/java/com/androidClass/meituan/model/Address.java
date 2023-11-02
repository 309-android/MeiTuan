package com.androidClass.meituan.model;

import java.io.Serializable;

public class Address implements Serializable {


    // 地址id
    private Integer id;

    // 用户id
    private Integer userId;

    // 收货姓名
    private String consignee;

    // 性别
    private String sex;

    //手机号
    private String phoneNumber;

    // 地址细节
    private String detail;

    // 地址标签
    private String label;

    // 是否默认地址
    private String isDefault;

    // 创建时间
    private String createTime;

    // 用户登录的手机号
    private String loginPhoneNumber;


    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", userId=" + userId +
                ", consignee='" + consignee + '\'' +
                ", sex='" + sex + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", detail='" + detail + '\'' +
                ", label='" + label + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", createTime='" + createTime + '\'' +
                ", loginPhoneNumber='" + loginPhoneNumber + '\'' +
                '}';
    }

    public String getLoginPhoneNumber() {
        return loginPhoneNumber;
    }

    public void setLoginPhoneNumber(String realPhoneNumber) {
        this.loginPhoneNumber = realPhoneNumber;
    }

    public Address() {
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

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
