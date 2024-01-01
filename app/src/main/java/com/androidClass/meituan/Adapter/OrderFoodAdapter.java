package com.androidClass.meituan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Food;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class OrderFoodAdapter extends ArrayAdapter {

    private final int resourceId;

    private List<Food> foods = new ArrayList<>();


    private Double sum;


    // 创建ImageLoader对象
    private ImageLoader imageLoader ;


    public OrderFoodAdapter(Context context, int textViewResourceId, List<Food> foods, ImageLoader imageLoader) {
        super(context, textViewResourceId, foods);
        resourceId = textViewResourceId;
        this.imageLoader = imageLoader;
        this.foods = foods; // 将传入的食物列表赋值给成员变量 this.foods
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Food food = (Food) getItem(position); // 获取当前项的food实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        // 获取要动态填充的视图对象
        // 食物图片
        ImageView foodImage_ImageView = (ImageView) view.findViewById(R.id.foodImage_ImageView);
        // 食物名称
        TextView foodName_TextView = (TextView) view.findViewById(R.id.foodName_TextView);
        // 食物价格
        TextView foodAmount_TextView = (TextView) view.findViewById(R.id.foodAmount_TextView);
        // 食物数量
        TextView foodNum_TextView = (TextView) view.findViewById(R.id.foodNum_TextView);

        // 设置值

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        imageLoader.displayImage(food.getFoodImg(),foodImage_ImageView,options);
        foodName_TextView.setText(food.getFoodName());
        foodAmount_TextView.setText(String.format("￥%s",food.getFoodAmount()));
        foodNum_TextView.setText("×1");


        return view;
    }
}
