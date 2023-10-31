package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.androidClass.meituan.R;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.androidClass.meituan.utils.SoftInputUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 用密码登录的activity
 */
public class UsePasswordActivity extends AppCompatActivity {


    private EditText userPhoneNumberWithPwd;
    private EditText userPassword;
    private Button login_button;
    private CheckBox password_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_password);

        // 用密码登录手机号码输入框
        userPhoneNumberWithPwd = findViewById(R.id.userPhoneNumberWithPwd);

        // 用户密码
        userPassword = findViewById(R.id.userPassword);

        // 用密码登录的登录按钮
        login_button = findViewById(R.id.login_button);

        // 协议勾选框
        password_check = findViewById(R.id.password_check);
        password_check.setChecked(true);

        phoneNumberListener();

        // 用验证码登录跳转
        findViewById(R.id.useMsgCodeLogin).setOnClickListener(v -> {
            Intent intent = new Intent(this, UseMsgCodeActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 输入手机号跳转获取验证码逻辑
     */
    public void phoneNumberListener() {
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
                if (s.length() == 11 && s.toString().startsWith("1")) {
                    // 关闭软键盘
                    SoftInputUtil.hideSoftInput(userPhoneNumberWithPwd);

                    // 设置登录按钮颜色
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#FFC033"));
                    login_button.setEnabled(true);
                    login_button.setBackgroundTintList(colorStateList);
                } else if (s.length() > 11 || !s.toString().startsWith("1")) {
                    // 手机号大于11位或者不是以1开头弹出提示
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号码", Toast.LENGTH_LONG).show();
                    // 号码输入错误获取登录按钮变为灰色并禁用按钮
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#CCCCCC"));
                    login_button.setEnabled(false);
                    login_button.setBackgroundTintList(colorStateList);
                } else {
                    // 号码输入错误获取登录按钮变为灰色并禁用按钮
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#CCCCCC"));
                    login_button.setEnabled(false);
                    login_button.setBackgroundTintList(colorStateList);
                }
            }
        });

        // 登录按钮的逻辑
        login_button.setOnClickListener(v -> {
            String phoneNumber = userPhoneNumberWithPwd.getText().toString();
            String password = userPassword.getText().toString();
            Map<String, Object> map = new HashMap<>();
            map.put("phoneNumber", phoneNumber);
            map.put("password", password);
            OKHttpUtils okHttpUtils = new OKHttpUtils();
            okHttpUtils.POST_JSON("/user/login", map);
            okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
                @Override
                public void error(String error) {
//                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                    Log.d("young", error);
                    Toast.makeText(getApplicationContext(), "服务器请求错误，请稍后再试", Toast.LENGTH_LONG).show();
                }

                @Override
                public void success(String json) {
                    Gson gson = new Gson();
                    String loginStatus = gson.fromJson(json, String.class);
                    Log.d("young", "loginStatus : " + loginStatus);
                    if ("success".equals(loginStatus)) {
                        // 登录成功跳转首页
                        Intent intent = new Intent(UsePasswordActivity.this, HomePageActivity.class);
                        startActivity(intent);
                    } else if ("error".equals(loginStatus)) {
                        Toast.makeText(getApplicationContext(), "账号或者密码输入错误！请重试", Toast.LENGTH_LONG).show();
                    }
                }
            });
        });
    }
}
