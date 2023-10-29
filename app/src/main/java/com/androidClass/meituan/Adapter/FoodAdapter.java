package com.androidClass.meituan.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Food;

import java.util.List;

public class FoodAdapter extends ArrayAdapter {

    private final int resourceId;

    public FoodAdapter(Context context, int textViewResourceId, List<Food> foods){
        super(context,textViewResourceId,foods);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Food food = (Food) getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        ImageView fruitImage = (ImageView) view.findViewById(R.id.food_image);//获取该布局内的图片视图
        TextView fruitName = (TextView) view.findViewById(R.id.food_name);//获取该布局内的文本视图
        fruitImage.setImageResource(R.drawable.test);//为图片视图设置图片资源
        fruitName.setText(food.getFoodName());//为文本视图设置文本内容
        return view;
    }
}
