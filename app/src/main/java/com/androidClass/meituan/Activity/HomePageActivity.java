package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.androidClass.meituan.Adapter.FoodAdapter;
import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页的activity
 */
public class HomePageActivity extends AppCompatActivity {

    private List<Food> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // 美食按钮监听
        findViewById(R.id.FineFood_Button).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"this is fineFood module",Toast.LENGTH_LONG).show();
        });

        // 饮品按钮监听
        findViewById(R.id.Drink_Button).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"this is drink module",Toast.LENGTH_LONG).show();
        });

        // 超市按钮监听
        findViewById(R.id.Market_Button).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"this is superMarket module",Toast.LENGTH_LONG).show();
        });

        // 水果按钮监听
        findViewById(R.id.Fruit_Button).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"this is fruit module",Toast.LENGTH_LONG).show();
        });

        initFoods();
        FoodAdapter adapter = new FoodAdapter(HomePageActivity.this, R.layout.food_item, foodList);
        ListView listview =(ListView) findViewById(R.id.foodList_ListView);
        listview.setAdapter(adapter);

    }

    private void initFoods(){
        for (int i = 0; i < 10; i++) {
            Food food = new Food("咖啡","@drawable/test");
            foodList.add(food);
        }
    }
}