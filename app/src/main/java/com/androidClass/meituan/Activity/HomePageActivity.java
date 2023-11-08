package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.androidClass.meituan.model.Address;
import com.androidClass.meituan.model.Store;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.androidClass.meituan.utils.SPUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private ImageLoaderConfiguration config;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // 初始化ImageLoader配置
        initImageLoader();

        // 检查地址信息
        checkAddress();

        // 初始化底部导航栏
        initBottomNavigation();

        // 添加地址按钮
        toAddAddressButton = findViewById(R.id.toAddAddress_Button);

        // 模块按钮监听
        moduleButtonListener();

        // 初始化店铺
        initStores();
        // 添加地址按钮监听
        toAddAddress();

    }

    /**
     * 模块按钮监听
     */
    private void moduleButtonListener() {
        // 美食按钮监听
        findViewById(R.id.FineFood_Button).setOnClickListener(v -> {
            Intent intent = new Intent(this, ShowModuleActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("storeCategory","1");
            intent.putExtras(bundle);
            startActivity(intent);
        });

        // 饮品按钮监听
        findViewById(R.id.Drink_Button).setOnClickListener(v -> {
            Intent intent = new Intent(this, ShowModuleActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("storeCategory","2");
            intent.putExtras(bundle);
            startActivity(intent);
        });

        // 超市按钮监听
        findViewById(R.id.Market_Button).setOnClickListener(v -> {
            Intent intent = new Intent(this, ShowModuleActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("storeCategory","3");
            intent.putExtras(bundle);
            startActivity(intent);
        });

        // 水果按钮监听
        findViewById(R.id.Fruit_Button).setOnClickListener(v -> {
            Intent intent = new Intent(this, ShowModuleActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("storeCategory","4");
            intent.putExtras(bundle);
            startActivity(intent);
        });
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
        Map<String,Object> map = new HashMap<>();
        map.put("storeCategory","");
        okHttpUtils.post("/store/get",map);
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
                storeAdapter = new StoreAdapter(HomePageActivity.this, R.layout.store_item, storeList, imageLoader);
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
                // 点击店铺进入店铺
                Intent intent = new Intent(HomePageActivity.this, FoodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("storeId",storeList.get(position).getId().toString());
                intent.putExtras(bundle);
                startActivity(intent);
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

    /**
     * 初始化ImageLoader配置
     */
    private void initImageLoader(){
        config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .build();
        imageLoader.init(config);
    }

    /**
     * 查看用户是否存在地址信息
     */
    private void checkAddress(){
        OKHttpUtils okHttpUtils = new OKHttpUtils();
        if (SPUtils.contains(getApplicationContext(),"phoneNumber")){
            // 已登录 查询地址信息
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
                    List<Address> addresses = JSON.parseObject(json, new TypeReference<List<Address>>() {
                    });
                    Log.d("young","homePageAddressCheck : " + addresses.toString());
                    if ("[]".equals(addresses.toString())){
                        // 该用户无地址信息 设置按钮显示内容为添加地址
                        toAddAddressButton.setText("添加地址");
                    } else{
                      toAddAddressButton.setText("选择地址");
                        for (Address address : addresses) {
                            if ("1".equals(address.getIsDefault())){
                                toAddAddressButton.setText(address.getDetail());
                            }
                        }
                    }
                }
            });
        } else{
          // 未登录
        }
    }
}