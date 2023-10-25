package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidClass.meituan.R;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.androidClass.meituan.utils.SoftInputUtil;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class getMsgCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_msg_code);

        EditText inputCode = findViewById(R.id.inputCode_EditText);

        // 获取验证码界面显示的号码
        TextView getMsgCodePhoneNumber = findViewById(R.id.getMsgCodePhoneNumber_TextView);

        Bundle fromLoginView = getIntent().getExtras();

        String phoneNumber =(String) fromLoginView.get("phoneNumber");

        getMsgCodePhoneNumber.setText(phoneNumber);

        // 输入验证码监听
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
                if(s.toString().length() == 6){
                    SoftInputUtil.hideSoftInput(inputCode);

                    Map<String,Object> map = new HashMap<>();
                    map.put("phoneNumber",phoneNumber);
                    map.put("code",s.toString());
                    okHttpUtils.POST_JSON("/user/loginWithCode",map);
                    okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
                        @Override
                        public void error(String error) {

                        }
                        @Override
                        public void success(String json) {
                            Gson gson = new Gson();
                            String status = gson.fromJson(json, String.class);
                            Log.d("young","checkCodeStatus : " + status);
                            if("success".equals(status)){
                                Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"您输入的验证码错误，请重试",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}