package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.androidClass.meituan.Adapter.StoreAdapter;
import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Store;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.androidClass.meituan.utils.SPUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyStarActivity extends AppCompatActivity {

    // 店铺集合
    private List<Store> storeList = new ArrayList<>();
    // 店铺适配器
    private StoreAdapter storeAdapter;
    // 店铺ListView
    private ListView listview;
    private ImageLoaderConfiguration config;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_star);

        // 初始化ImageLoader配置
        initImageLoader();

        initStores();
    }

    /**
     * 初始化ImageLoader配置
     */
    private void initImageLoader(){
        config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .build();
        imageLoader.init(config);
    }

    /**
     * 初始化店铺数据
     */
    private void initStores() {
        // 初始化店铺数据
        OKHttpUtils okHttpUtils = new OKHttpUtils();
        Map<String,Object> map = new HashMap<>();
        if (!SPUtils.contains(getApplicationContext(),"phoneNumber")){
            Toast.makeText(getApplicationContext(), "请先登录，再查看收藏", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, UsePasswordActivity.class));
        }
        String phoneNumber =(String) SPUtils.get(getApplicationContext(), "phoneNumber", "");
        map.put("phoneNumber",phoneNumber);
        okHttpUtils.post("/star/getAll",map);
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

                Log.d("young", "starList : " + storeList.toString());

                // 初始化listview
                storeAdapter = new StoreAdapter(MyStarActivity.this, R.layout.store_item, storeList, imageLoader);
                listview = findViewById(R.id.storeList_ListView);
                listview.setAdapter(storeAdapter);

            }
        });
    }
}