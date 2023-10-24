package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidClass.meituan.R;

public class usePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_password);

        // 用密码登录手机号码输入框
        EditText userPhoneNumberWithPwd = findViewById(R.id.userPhoneNumberWithPwd);

        // 用户密码
        EditText userPassword = findViewById(R.id.userPassword);

        // 用密码登录的登录按钮
        Button login_button = findViewById(R.id.login_button);

        // 输入手机号的监听
        userPhoneNumberWithPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 11 && s.toString().startsWith("1")){
                    // 设置登录按钮颜色
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#FFC033"));
                    login_button.setEnabled(true);
                    login_button.setBackgroundTintList(colorStateList);
                }else if(s.length() > 11 || !s.toString().startsWith("1") ){
                    // 手机号大于11位或者不是以1开头弹出提示
                    Toast.makeText(getApplicationContext(),"请输入正确的手机号码",Toast.LENGTH_LONG).show();
                    // 号码输入错误获取登录按钮变为灰色并禁用按钮
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#CCCCCC"));
                    login_button.setEnabled(false);
                    login_button.setBackgroundTintList(colorStateList);
                }
                else{
                    // 号码输入错误获取登录按钮变为灰色并禁用按钮
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#CCCCCC"));
                    login_button.setEnabled(false);
                    login_button.setBackgroundTintList(colorStateList);
                }
            }
        });

        // 用验证码登录跳转
        findViewById(R.id.useMsgCodeLogin).setOnClickListener(v->{
            Intent intent = new Intent(this, UserLoginActivity.class);
            startActivity(intent);
        });

        // 登录按钮的逻辑
        login_button.setOnClickListener(v->{
            String phoneNumber = userPhoneNumberWithPwd.getText().toString();
            String password = userPassword.getText().toString();
        });
    }
}