package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.androidClass.meituan.Adapter.StoreAdapter;
import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Store;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.androidClass.meituan.utils.SPUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页的activity
 */
public class HomePageActivity extends AppCompatActivity {

    // 店铺集合
    private List<Store> storeList = new ArrayList<>();
    // 底部导航栏
    private BottomNavigationView navigationView;
    // 店铺适配器
    private StoreAdapter storeAdapter;
    // 店铺ListView
    private ListView listview;
    // 添加地址按钮
    private Button toAddAddressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // 初始化底部导航栏
        initBottomNavigation();

        // 添加地址按钮
        toAddAddressButton = findViewById(R.id.toAddAddress_Button);

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
        // 添加地址按钮监听
        toAddAddress();

    }

    /**
     * 初始化底部导航栏
     */
    private void initBottomNavigation() {
        // 底部导航栏初始化
        navigationView = findViewById(R.id.bottom_navigation_home);
        // 设置默认选中首页
        navigationView.setSelectedItemId(R.id.item_home);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.item_home) {
                // 跳转到HomeActivity
//                Intent homeIntent = new Intent(HomePageActivity.this, HomePageActivity.class);
//                startActivity(homeIntent);
                return true;
            } else if (item.getItemId() == R.id.item_order) {
                // 跳转到OrderActivity
                Intent orderIntent = new Intent(HomePageActivity.this, OrderActivity.class);
                startActivity(orderIntent);
                return true;
            } else if (item.getItemId() == R.id.item_user) {
                // 跳转到IndividualMsgActivity
                Intent mineIntent = new Intent(HomePageActivity.this, IndividualMsgActivity.class);
                startActivity(mineIntent);
                return true;
            }
            return false;
        });
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
                Toast.makeText(getApplicationContext(), "服务器出错啦，请稍后再试", Toast.LENGTH_LONG).show();
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
     * 点击店铺逻辑
     */
    public void clickStore() {
        // 设置点击store事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "点了一下 "
                        + storeList.get(position).getId().toString()
                        + storeList.get(position).getStoreName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 添加地址按钮监听
     */
    private void toAddAddress() {

        toAddAddressButton.setOnClickListener(v -> {
            if (SPUtils.contains(getApplicationContext(),"phoneNumber")) {
                startActivity(new Intent(this, MyAddressActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "您还没有登录呢，请登录后查看地址", Toast.LENGTH_LONG).show();
            }
        });
    }
}