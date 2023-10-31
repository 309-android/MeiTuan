package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.androidClass.meituan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrderActivity extends AppCompatActivity {
    // 底部导航栏
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        // 初始化底部导航栏
        initBottomNavigation();
    }

    /**
     * 底部导航栏初始化
     */
    private void initBottomNavigation() {
        // 底部导航栏初始化
        navigationView = findViewById(R.id.bottom_navigation_order);

        // 设置默认选中订单
        navigationView.setSelectedItemId(R.id.item_order);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.item_home){
                // 跳转到HomeActivity
                Intent homeIntent = new Intent(OrderActivity.this, HomePageActivity.class);
                startActivity(homeIntent);
                return true;
            }else if(item.getItemId() == R.id.item_order){
                // 跳转到OrderActivity
//                Intent profileIntent = new Intent(OrderActivity.this, OrderActivity.class);
//                startActivity(profileIntent);
                return true;
            }else if(item.getItemId() == R.id.item_user){
                // 跳转到IndividualMsgActivity
                Intent settingsIntent = new Intent(OrderActivity.this, IndividualMsgActivity.class);
                startActivity(settingsIntent);
                return true;
            }
            return false;
        });
    }
}