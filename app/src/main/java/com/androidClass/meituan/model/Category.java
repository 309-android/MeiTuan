package com.androidClass.meituan.model;

public class Category {

    private Integer id;

    private Integer storeId;

    private String name;



    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", name='" + name + '\'' +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
