package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.androidClass.meituan.Adapter.AddressAdapter;
import com.androidClass.meituan.Adapter.StoreAdapter;
import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Address;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.androidClass.meituan.utils.SPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAddressActivity extends AppCompatActivity {


    // 地址集合
    private List<Address> addresses;
    // 地址适配器
    private AddressAdapter addressAdapter;
    // 地址展示的listview
    private ListView listview;
    // 新增地址按钮
    private Button toSaveAddressButton;
    private ImageButton backToHomePageFromMyAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        backToHomePageFromMyAddButton = findViewById(R.id.backToHomePageFromMyAdd_Button);

        toSaveAddressButton = findViewById(R.id.toSaveAddress_Button);

        // 新增地址跳转
        toSaveAddressButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AddAddressActivity.class));
        });

        // 返回首页跳转
        backToHomePageFromMyAddButton.setOnClickListener(v -> {
            startActivity(new Intent(this, HomePageActivity.class));
        });

        // 初始化地址数据
        initAddresses();

    }

    /**
     * 初始化地址信息
     */
    private void initAddresses(){
        // 初始化地址信息
        OKHttpUtils okHttpUtils = new OKHttpUtils();
        String phoneNumber = (String) SPUtils.get(getApplicationContext(), "phoneNumber", "");

        Map<String,Object> map = new HashMap<>();
        map.put("phoneNumber",phoneNumber);

        okHttpUtils.post("/address/getAll",map);

        okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
            @Override
            public void error(String error) {
                Toast.makeText(getApplicationContext(), "服务器出错啦，请稍后再试", Toast.LENGTH_LONG).show();
            }

            @Override
            public void success(String json) {
                addresses = JSON.parseObject(json, new TypeReference<List<Address>>() {
                });
                Log.d("young","addresses data ==> " + addresses.toString());

                // 初始化listview
                addressAdapter = new AddressAdapter(MyAddressActivity.this, R.layout.address_item, addresses);
                listview = (ListView) findViewById(R.id.addList_ListView);
                listview.setAdapter(addressAdapter);
            }
        });

    }
}