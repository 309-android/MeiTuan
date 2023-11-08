package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Address;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.androidClass.meituan.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加地址
 */
public class AddAddressActivity extends AppCompatActivity {

    // 性别单选框
    private RadioGroup checkGenderRadioGroup;
    // 标签单选框
    private RadioGroup getLabelRadioGroup;
    // 地址实体类
    private Address address = new Address();
    // 地址输入框
    private EditText detailInputEditText;
    // 联系人姓名
    private EditText consigneeInputEditText;
    // 收货手机号
    private EditText phoneNumberInputEditText;
    // 添加地址按钮
    private Button saveAddressButton;
    // 返回按钮
    private ImageButton backToHomePage_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        // 性别单选
        checkGenderRadioGroup = findViewById(R.id.checkGender_RadioGroup);
        // 标签单选框
        getLabelRadioGroup = findViewById(R.id.getLabel_RadioGroup);
        // 地址输入框
        detailInputEditText = findViewById(R.id.detailInput_EditText);
        // 联系人姓名
        consigneeInputEditText = findViewById(R.id.consigneeInput_EditText);
        // 收货手机号
        phoneNumberInputEditText = findViewById(R.id.phoneNumberInput_EditText);
        // 添加地址按钮
        saveAddressButton = findViewById(R.id.saveAddress_Button);
        // 返回按钮
        backToHomePage_Button = findViewById(R.id.backToHomePage_Button);

        // 返回到首页
        backToHomePage_Button.setOnClickListener(v -> {
            startActivity(new Intent(this, MyAddressActivity.class));
        });

        // 获得选择的标签和性别
        getLabelAndSex();
        // 输入框的监听
        EditTextListener();
        // 新增地址
        addAddress();
    }

    /**
     * 获得选择的标签和性别
     */
    private void getLabelAndSex() {
        checkGenderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = findViewById(checkedId);
                // 设置值
                address.setSex("先生".equals(checkedRadioButton.getText().toString()) ? "1" : "0");
            }
        });

        getLabelRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = findViewById(checkedId);
                // 设置值
                address.setLabel(checkedRadioButton.getText().toString());
            }
        });
    }

    /**
     * 输入框的监听
     */
    private void EditTextListener(){
        // 地址输入框的监听
        detailInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 将地址添加到address中
                address.setDetail(s.toString());
            }
        });

        // 联系人输入框的监听
        consigneeInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 将联系人添加到address中
                address.setConsignee(s.toString());
            }
        });
        // 手机号输入框的监听
        phoneNumberInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 检验手机号
                if (s.toString().length() == 11 && s.toString().startsWith("1")){
                    // 将联系人添加到address中
                    address.setPhoneNumber(s.toString());
                }
            }
        });
    }

    /**
     * 新增地址
     */
    public void addAddress(){
        // 先将登录的手机号存入address中
        String phoneNumber = (String) SPUtils.get(getApplicationContext(), "phoneNumber", "");
        address.setLoginPhoneNumber(phoneNumber);
        // 点击按钮的监听
        saveAddressButton.setOnClickListener(v -> {
            Log.d("young",address.toString());
            // 发送请求
            OKHttpUtils okHttpUtils = new OKHttpUtils();

            if (address.getDetail() != null  && address.getConsignee() != null
                    && address.getSex() != null && address.getPhoneNumber() != null
                    && address.getLoginPhoneNumber() != null){

                // 如果都不为空 才放入map
                Map<String,Object> map = new HashMap<>();

                map.put("detail",address.getDetail());
                map.put("label",address.getLabel());
                map.put("consignee",address.getConsignee());
                map.put("phoneNumber",address.getPhoneNumber());
                map.put("loginPhoneNumber",address.getLoginPhoneNumber());
                map.put("sex",address.getSex());

                okHttpUtils.POST_JSON("/address/add",map);
                okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
                    @Override
                    public void error(String error) {
                        Toast.makeText(getApplicationContext(),"服务器出错啦，请稍后再试！",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void success(String json) {
                        String addStatus = JSON.parseObject(json, new TypeReference<String>() {
                        });
                        if ("success".equals(addStatus)){
                            Toast.makeText(getApplicationContext(),"保存地址成功！",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AddAddressActivity.this, MyAddressActivity.class));
                        }else if("error".equals(addStatus)){
                            Toast.makeText(getApplicationContext(),"服务器出错啦，请稍后再试！",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            } else if (address.getSex() == null){
                Toast.makeText(getApplicationContext(),"请选择您的称呼！",Toast.LENGTH_LONG).show();
            }else if(address.getConsignee() == null){
                Toast.makeText(getApplicationContext(),"请输入收货人的姓名！",Toast.LENGTH_LONG).show();
            }else if(address.getDetail() == null ){
                Toast.makeText(getApplicationContext(),"请输入您的收货地址！",Toast.LENGTH_LONG).show();
            }else if(address.getPhoneNumber() == null){
                Toast.makeText(getApplicationContext(),"您输入的手机号码格式不正确！",Toast.LENGTH_LONG).show();
            }


        });

    }
}