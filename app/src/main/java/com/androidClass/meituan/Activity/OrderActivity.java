package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.androidClass.meituan.Adapter.OrderAdapter;
import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Food;
import com.androidClass.meituan.model.Order;
import com.androidClass.meituan.model.Store;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.androidClass.meituan.utils.SPUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    // 底部导航栏
    private BottomNavigationView navigationView;
    // 全部订单按钮
    private Button getAllOrdersButton;
    // 待评价按钮
    private Button getNoCommentsOrdersButton;
    // 订单数据
    private List<List<Order>> orders;

    // 订单适配器
    private OrderAdapter orderAdapter;
    // 订单ListView
    private ListView orderList_listView;

    private ImageLoaderConfiguration config;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // 初始化 ImageLoader
        initImageLoader();

        // 初始化底部导航栏
        initBottomNavigation();

        getAllOrdersButton = findViewById(R.id.getAllOrders_Button);

        getNoCommentsOrdersButton = findViewById(R.id.getNoCommentsOrders_Button);

        // 初始化订单数据
        initOrders();

    }

    /**
     * 底部导航栏初始化
     */
    private void initBottomNavigation() {
        // 底部导航栏初始化
        navigationView = findViewById(R.id.bottom_navigation_order);

        // 设置默认选中订单
        navigationView.setSelectedItemId(R.id.item_order);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.item_home) {
                // 跳转到HomeActivity
                Intent homeIntent = new Intent(OrderActivity.this, HomePageActivity.class);
                startActivity(homeIntent);
                return true;
            } else if (item.getItemId() == R.id.item_order) {
                // 跳转到OrderActivity
//                Intent profileIntent = new Intent(OrderActivity.this, OrderActivity.class);
//                startActivity(profileIntent);
                return true;
            } else if (item.getItemId() == R.id.item_user) {
                // 跳转到IndividualMsgActivity
                Intent settingsIntent = new Intent(OrderActivity.this, IndividualMsgActivity.class);
                startActivity(settingsIntent);
                return true;
            }
            return false;
        });
    }

    /**
     * 初始化订单数据
     */
    private void initOrders() {
        OKHttpUtils okHttpUtils = new OKHttpUtils();
        Bundle bundle = getIntent().getExtras();
        // 查看是否已保存用户数据
        if (!SPUtils.contains(getApplicationContext(), "phoneNumber")) {
            // 如果为保存用户数据
            Toast.makeText(getApplicationContext(), "您当前还没登录呢，请登录后查看订单", Toast.LENGTH_LONG).show();
        } else {
            // 登录时保存的手机号
            String phoneNumber = (String) SPUtils.get(getApplicationContext(), "phoneNumber", "");
            // 放入map用作后端接受的参数
            Map<String, Object> map = new HashMap<>();
            map.put("phoneNumber", phoneNumber);
            okHttpUtils.post("/order/getAllOrders", map);
            okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
                @Override
                public void error(String error) {

                }

                @Override
                public void success(String json) {
                    if ("[]".equals(json)) {
                        Toast.makeText(getApplicationContext(), "当前还没有订单哦", Toast.LENGTH_LONG).show();
                    } else {
                        orders = JSON.parseObject(json, new TypeReference<List<List<Order>>>() {
                        });
                        Log.d("young", "json data : " + json);
                        // 为order中的food字段 手动赋值
                        initFoodInOrders();
                        initStoreInOrders();
                    }

                }
            });
        }
    }

    /**
     * 为orders集合中的food 赋值
     */
    private void initFoodInOrders() {
        // 初始化订单中的食物
        OKHttpUtils okHttpUtils = new OKHttpUtils();
        okHttpUtils.post("/food/getAllFood", null);
        okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
            @Override
            public void error(String error) {
                Toast.makeText(getApplicationContext(), "服务器出错啦，请稍后再试", Toast.LENGTH_LONG).show();
            }

            @Override
            public void success(String json) {
                List<Food> foodDOS = JSON.parseObject(json, new TypeReference<List<Food>>() {
                });
                // 对orders进行处理
                for (List<Order> order : orders) {
                    for (Order orderDO : order) {
                        for (Food foodDO : foodDOS) {
                            if (foodDO.getId() == orderDO.getFoodId()) {
                                orderDO.setFood(foodDO);
                            }
                        }
                    }
                }


            }
        });
    }

    private void initStoreInOrders() {
        // 初始化订单中的店铺
        OKHttpUtils okHttpUtils = new OKHttpUtils();
        Map<String, Object> map = new HashMap<>();
        map.put("storeCategory", "");
        okHttpUtils.post("/store/get", map);
        okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
            @Override
            public void error(String error) {

            }

            @Override
            public void success(String json) {
                List<Store> stores = JSON.parseObject(json, new TypeReference<List<Store>>() {
                });

                // 对orders进行处理
                for (List<Order> order : orders) {
                    for (Order orderDO : order) {
                        for (Store store : stores) {
                            if (orderDO.getStoreId() == store.getId()) {
                                orderDO.setStore(store);
                            }
                        }
                    }
                }
                Log.d("young", "orders data : " + orders.toString());

                // 初始化listview
                orderAdapter = new OrderAdapter(OrderActivity.this, R.layout.order_item, orders, imageLoader );
                orderList_listView = (ListView) findViewById(R.id.orderList_ListView);
                orderList_listView.setAdapter(orderAdapter);
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
}