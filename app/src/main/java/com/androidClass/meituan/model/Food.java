package com.androidClass.meituan.model;

import android.graphics.drawable.Drawable;

public class Food {

    private String foodName;

    private String Image;

    public Food(String foodName, String image) {
        this.foodName = foodName;
        Image = image;
    }

    public Food() {
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodName='" + foodName + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
