package com.androidClass.meituan.Adapter;

import static androidx.core.app.ActivityCompat.recreate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.androidClass.meituan.Activity.FoodActivity;
import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Address;
import com.androidClass.meituan.model.Food;
import com.androidClass.meituan.utils.SPUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FoodAdapter extends ArrayAdapter {

    public static final String FOOD_ADDED_ACTION = "FOOD_ADDED";
    private final int resourceId;

    private List<Food> foods = new ArrayList<>();

    // 创建ImageLoader对象
    private ImageLoader imageLoader ;

    private List<Food> orderFoods;


    public FoodAdapter(Context context, int textViewResourceId, List<Food> foods, ImageLoader imageLoader,List<Food> orderFoods) {
        super(context, textViewResourceId, foods);
        resourceId = textViewResourceId;
        this.imageLoader = imageLoader;
        this.orderFoods = orderFoods;
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
        // 食物月售
        TextView foodMonthSale_TextView = (TextView) view.findViewById(R.id.foodMonthSale_TextView);
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
        foodMonthSale_TextView.setText(String.format("月售%s",food.getMonthSale()));
        foodAmount_TextView.setText(food.getFoodAmount());
        foodNum_TextView.setText(String.valueOf(Collections.frequency(orderFoods,food)));

        // 设置添加按钮的点击事件
        ImageButton addFoodButton = view.findViewById(R.id.addFood_Button);
        ImageButton removeFoodButton = view.findViewById(R.id.removeFood_Button);

        addFoodButton.setOnClickListener(v -> {
            // 在这里处理按钮点击事件，例如添加食物到购物车等操作
//            Toast.makeText(getContext(),"点了一下 " + food.getFoodName(),Toast.LENGTH_LONG).show();
            orderFoods.add(food);
            // 发送广播通知食物被添加
            Intent intent = new Intent(FOOD_ADDED_ACTION);
            intent.putExtra("food", food);
            getContext().sendBroadcast(intent);
            foodNum_TextView.setText(String.valueOf(Collections.frequency(orderFoods,food)));
        });

        removeFoodButton.setOnClickListener(v -> {
            // 在这里处理按钮点击事件，例如添加食物到购物车等操作
//            Toast.makeText(getContext(),"点了一下 " + food.getFoodName(),Toast.LENGTH_LONG).show();
            orderFoods.remove(food);
            // 发送广播通知食物被移除
            Intent intent = new Intent(FOOD_ADDED_ACTION);
            intent.putExtra("food", food);
            getContext().sendBroadcast(intent);
            foodNum_TextView.setText(String.valueOf(Collections.frequency(orderFoods,food)));
        });

        return view;
    }
}
