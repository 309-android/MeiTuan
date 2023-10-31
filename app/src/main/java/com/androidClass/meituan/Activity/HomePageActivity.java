package com.androidClass.meituan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.androidClass.meituan.Adapter.StoreAdapter;
import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Store;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页的activity
 */
public class HomePageActivity extends AppCompatActivity {

    private List<Store> storeList = new ArrayList<>();
    private BottomNavigationView navigationView;
    private StoreAdapter storeAdapter;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        navigationView = findViewById(R.id.bottom_navigation);

        navigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.item_home){
                // 跳转到HomeActivity
                Intent homeIntent = new Intent(HomePageActivity.this, HomePageActivity.class);
                startActivity(homeIntent);
                return true;
            }else if(item.getItemId() == R.id.item_order){
                // 跳转到usePasswordActivity
                Intent profileIntent = new Intent(HomePageActivity.this, usePasswordActivity.class);
                startActivity(profileIntent);
                return true;
            }else if(item.getItemId() == R.id.item_user){
                // 跳转到IndividualMsgActivity
                Intent settingsIntent = new Intent(HomePageActivity.this, IndividualMsgActivity.class);
                startActivity(settingsIntent);
                return true;
            }
            return false;
        });

        navigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });

        // 美食按钮监听
        findViewById(R.id.FineFood_Button).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "this is fineFood module", Toast.LENGTH_LONG).show();
        });

        // 饮品按钮监听
        findViewById(R.id.Drink_Button).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "this is drink module", Toast.LENGTH_LONG).show();
        });

        // 超市按钮监听
        findViewById(R.id.Market_Button).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "this is superMarket module", Toast.LENGTH_LONG).show();
        });

        // 水果按钮监听
        findViewById(R.id.Fruit_Button).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "this is fruit module", Toast.LENGTH_LONG).show();
        });

        // 初始化店铺
        initStores();



    }

    /**
     * 初始化店铺数据
     */
    private void initStores() {
        // 初始化店铺数据
        OKHttpUtils okHttpUtils = new OKHttpUtils();
        okHttpUtils.get("/store/getAll");
        okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
            @Override
            public void error(String error) {
                Toast.makeText(getApplicationContext(),"服务器出错啦，请稍后再试",Toast.LENGTH_LONG).show();
            }
            @Override
            public void success(String json) {
                // json数组转list对象数组
                storeList = JSON.parseObject(json, new TypeReference<List<Store>>() {
                });

                Log.d("young", "storeList : " + storeList.toString());

                // 初始化listview
                storeAdapter = new StoreAdapter(HomePageActivity.this, R.layout.store_item, storeList);
                listview = (ListView) findViewById(R.id.storeList_ListView);
                listview.setAdapter(storeAdapter);

                // 点击店铺
                clickStore();
            }
        });
    }

    /**
     * 初始化底部按钮
     */
    private void initBottomMenu() {
        // 初始化
    }

    /**
     * 点击店铺逻辑
     */
    public void clickStore(){
        // 设置点击store事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"点了一下 " + storeList.get(position).getId().toString() + storeList.get(position).getStoreName(),Toast.LENGTH_LONG).show();
            }
        });
    }
}