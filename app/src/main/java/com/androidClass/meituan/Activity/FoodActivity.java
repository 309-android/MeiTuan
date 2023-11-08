package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidClass.meituan.Adapter.FoodAdapter;
import com.androidClass.meituan.Adapter.StoreAdapter;
import com.androidClass.meituan.model.Food;
import com.androidClass.meituan.utils.getImageResourceId;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Store;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodActivity extends AppCompatActivity {

    // 店铺名
    private TextView storeNameTextView;
    // 店铺评分
    private TextView storeScoreTextView;
    // 店铺月售
    private TextView monthSaleTextView;
    // 店铺图片
    private ImageView storeImageImageView;
    // 店铺简介
    private TextView storeSummaryTextView;

    private ImageLoaderConfiguration config;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private ListView foodListView;

    private List<Food> foods;

    private FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        // 初始化ImageLoader配置
        initImageLoader();
        // 初始化店铺信息
        initStoreInfo();

        // 初始化菜品
        initFood();

    }

    /**
     * 初始化店铺信息
     */
    private void initStoreInfo() {
        // 组件初始化
        storeNameTextView = findViewById(R.id.storeName_TextView);
        storeScoreTextView = findViewById(R.id.storeScore_TextView);
        monthSaleTextView = findViewById(R.id.monthSale_TextView);
        storeImageImageView = findViewById(R.id.storeImage_ImageView);
        storeSummaryTextView = findViewById(R.id.storeSummary_TextView);
        // 首页传来的店铺id
        Bundle bundle = getIntent().getExtras();
        String storeId = (String) bundle.get("storeId");

        OKHttpUtils okHttpUtils = new OKHttpUtils();
        Map<String,Object> map = new HashMap<>();
        map.put("storeId",storeId);
        okHttpUtils.post("/store/getById",map);
        okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
            @Override
            public void error(String error) {
                Toast.makeText(getApplicationContext(), "服务器出错啦，请稍后再试", Toast.LENGTH_LONG).show();
            }

            @Override
            public void success(String json) {
                Store store = JSON.parseObject(json, new TypeReference<Store>() {
                });

                if (store != null){
                    storeNameTextView.setText(store.getStoreName());
                    storeScoreTextView.setText(store.getStoreScore());
                    monthSaleTextView.setText(String.format("月售%s+",store.getMonthSale()));
                    imageLoader.displayImage(store.getImage(),storeImageImageView,options);
                    storeSummaryTextView.setText(store.getSummary());
                }

            }
        });
    }

    /**
     * 初始化ImageLoader配置
     */
    private void initImageLoader(){
        config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .build();
        imageLoader.init(config);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    private void initFood(){

        // 首页传来的店铺id
        Bundle bundle = getIntent().getExtras();
        String storeId = (String) bundle.get("storeId");

        OKHttpUtils okHttpUtils = new OKHttpUtils();
        Map<String,Object> map = new HashMap<>();
        map.put("storeId",storeId);
        okHttpUtils.post("/food/getByStoreId",map);
        okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {


            @Override
            public void error(String error) {
                Toast.makeText(getApplicationContext(), "服务器出错啦，请稍后再试", Toast.LENGTH_LONG).show();
            }

            @Override
            public void success(String json) {
                foods = JSON.parseObject(json, new TypeReference<List<Food>>() {
                });

                Log.d("young","foods : "+foods.toString());

                if ("[]".equals(foods.toString())){
                    Toast.makeText(getApplicationContext(), "当前店铺还没有菜品哦", Toast.LENGTH_LONG).show();
                }else{
                    // 初始化listview
                    foodAdapter = new FoodAdapter(FoodActivity.this, R.layout.food_item, foods, imageLoader);
                    foodListView = (ListView) findViewById(R.id.foodList_ListView);
                    foodListView.setAdapter(foodAdapter);
                }

            }
        });



    }
}