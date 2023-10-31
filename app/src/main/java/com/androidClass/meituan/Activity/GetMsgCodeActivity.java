package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidClass.meituan.R;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.androidClass.meituan.utils.SoftInputUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取验证码界面的activity
 */
public class GetMsgCodeActivity extends AppCompatActivity {

    private EditText inputCode;
    private TextView getMsgCodePhoneNumber;
    private TextView seconds_TextView;
    private String phoneNumber;
    private Button secondGetCode_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_msg_code);

        // 输入验证码editText
        inputCode = findViewById(R.id.inputCode_EditText);

        secondGetCode_button = findViewById(R.id.secondGetCode_Button);

        // 倒计时
        seconds_TextView = findViewById(R.id.seconds_TextView);

        // 设置倒计时60s才可重新获取验证码
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 倒计时
                seconds_TextView.setText(String.valueOf(millisUntilFinished / 1000));
                // 设置按钮不可用
                secondGetCode_button.setEnabled(false);
                secondGetCode_button.setClickable(false);
            }

            @Override
            public void onFinish() {
                //按钮可用
                seconds_TextView.setText("");
                secondGetCode_button.setText("重新获取验证码");
                secondGetCode_button.setClickable(true);
                secondGetCode_button.setEnabled(true);
            }
        }.start();

        // 获取验证码界面显示的号码
        getMsgCodePhoneNumber = findViewById(R.id.getMsgCodePhoneNumber_TextView);

        // 从登录页面传来的数据包
        Bundle fromLoginView = getIntent().getExtras();

        // 手机号
        phoneNumber = (String) fromLoginView.get("phoneNumber");

        getMsgCodePhoneNumber.setText(phoneNumber);

        // 输入验证码登录逻辑
        inputListener();
        // 重新获取验证码逻辑
        secondGetCode();
    }

    /**
     * 输入验证码登录逻辑
     */
    public void inputListener() {
        inputCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                OKHttpUtils okHttpUtils = new OKHttpUtils();

                // 输入到达6位 就发送一个请求
                if (s.toString().length() == 6) {
                    SoftInputUtil.hideSoftInput(inputCode);

                    Map<String, Object> map = new HashMap<>();
                    map.put("phoneNumber", phoneNumber);
                    map.put("code", s.toString());
                    okHttpUtils.POST_JSON("/user/loginWithCode", map);
                    okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
                        @Override
                        public void error(String error) {

                        }

                        @Override
                        public void success(String json) {
                            Gson gson = new Gson();
                            String status = gson.fromJson(json, String.class);
                            Log.d("young", "checkCodeStatus : " + status);
                            if ("success".equals(status)) {
                                //Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_LONG).show();
                                // 登录成功跳转首页
                                Intent intent = new Intent(GetMsgCodeActivity.this, HomePageActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "您输入的验证码错误，请重试", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 重新获取验证码逻辑
     */
    public void secondGetCode() {
            secondGetCode_button.setOnClickListener(v -> {

                OKHttpUtils okHttpUtils = new OKHttpUtils();
                // 获取验证码
                Map<String, Object> map = new HashMap<>();
                map.put("phoneNumber", phoneNumber);
                okHttpUtils.post("/user/getCode", map);
                okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
                    @Override
                    public void error(String error) {
                        Toast.makeText(getApplicationContext(), "服务器出错啦，请重试", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void success(String json) {
                        Gson gson = new Gson();
                        String status = gson.fromJson(json, String.class);
                        Log.d("young", "getSecondCodeStatus : " + status);
                        if ("success".equals(status)) {
                            // 成功获取验证码
                            Toast.makeText(getApplicationContext(), "重新获取验证码成功！", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "服务器出错啦，请稍后再试", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            });
    }
}