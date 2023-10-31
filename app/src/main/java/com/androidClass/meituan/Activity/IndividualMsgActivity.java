package com.androidClass.meituan.Activity;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.androidClass.meituan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * 我的页面的activity
 */
public class IndividualMsgActivity extends AppCompatActivity {
    // 底部导航栏
    private BottomNavigationView navigationView;
    // 登录/注册按钮
    private Button toLoginOrRegisterButton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_msg);

        initBottomNavigation();

        // 登录/注册按钮
        toLoginOrRegisterButton = findViewById(R.id.toLoginOrRegister_Button);

        // 登录/注册跳转
        toLoginOrRegisterButton.setOnClickListener(v -> {

            // 跳转到用验证码登录
            startActivity(new Intent(IndividualMsgActivity.this, UseMsgCodeActivity.class));
        });
    }

    private void initBottomNavigation() {
        // 底部导航栏初始化
        navigationView = findViewById(R.id.bottom_navigation_my);

        // 设置默认选中我的
        navigationView.setSelectedItemId(R.id.item_user);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.item_home){
                // 跳转到HomeActivity
                Intent homeIntent = new Intent(IndividualMsgActivity.this, HomePageActivity.class);
                startActivity(homeIntent);
                return true;
            }else if(item.getItemId() == R.id.item_order){
                // 跳转到OrderActivity
                Intent orderIntent = new Intent(IndividualMsgActivity.this, OrderActivity.class);
                startActivity(orderIntent);
                return true;
            }else if(item.getItemId() == R.id.item_user){
                // 跳转到IndividualMsgActivity
//                Intent settingsIntent = new Intent(IndividualMsgActivity.this, IndividualMsgActivity.class);
//                startActivity(settingsIntent);
                return true;
            }
            return false;
        });
    }
}
