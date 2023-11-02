package com.androidClass.meituan.Activity;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.androidClass.meituan.R;
import com.androidClass.meituan.utils.SPUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * 我的页面的activity
 */
public class IndividualMsgActivity extends AppCompatActivity {
    // 底部导航栏
    private BottomNavigationView navigationView;
    // 登录/注册按钮
    private Button toLoginOrRegisterButton;
    private ImageButton toMyAddressButton;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_msg);

        // 登录/注册按钮
        toLoginOrRegisterButton = findViewById(R.id.toLoginOrRegister_Button);

        toMyAddressButton = findViewById(R.id.toMyAddress_Button);

        // 我的地址跳转
        toMyAddressButton.setOnClickListener(v ->{
            startActivity(new Intent(this, MyAddressActivity.class));
        });

        // 初始化底部导航栏
        initBottomNavigation();

        checkLogin();

    }

    /**
     * 判断是否登录
     */
    private void checkLogin() {
        // 判断是否登录
        if (SPUtils.contains(getApplicationContext(),"phoneNumber")){
            //如果已经登录
            String phoneNumber = (String) SPUtils.get(getApplicationContext(), "phoneNumber", "");
            toLoginOrRegisterButton.setText(phoneNumber);
            toLoginOrRegisterButton.setOnClickListener(v -> {
                // 已登录则跳转到设置界面
                startActivity(new Intent(IndividualMsgActivity.this, ExitLoginActivity.class));
            });
        }else{
            // 未登录状态
            toLoginOrRegisterButton.setText("登录/注册");
            // 登录/注册跳转
            toLoginOrRegisterButton.setOnClickListener(v -> {
                // 跳转到用验证码登录
                startActivity(new Intent(IndividualMsgActivity.this, UseMsgCodeActivity.class));
            });
        }
    }

    /**
     * 初始化底部导航栏
     */
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
