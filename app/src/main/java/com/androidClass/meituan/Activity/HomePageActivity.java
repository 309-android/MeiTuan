package com.androidClass.meituan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.androidClass.meituan.Adapter.StoreAdapter;
import com.androidClass.meituan.Adapter.ViewPagerAdapter;
import com.androidClass.meituan.Fragment.HomeFragment;
import com.androidClass.meituan.Fragment.OrderFragment;
import com.androidClass.meituan.Fragment.UserFragment;
import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Store;
import com.androidClass.meituan.model.User;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页的activity
 */
public class HomePageActivity extends AppCompatActivity {

    private List<Store> storeList = new ArrayList<>();
    private BottomNavigationView navigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


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
                StoreAdapter adapter = new StoreAdapter(HomePageActivity.this, R.layout.food_item, storeList);
                ListView listview = (ListView) findViewById(R.id.foodList_ListView);
                listview.setAdapter(adapter);
            }
        });
    }

    /**
     * 初始化底部按钮
     */
    private void initBottomMenu(){
        // 初始化
        navigationView = findViewById(R.id.nav_bottom);
        viewPager = findViewById(R.id.ViewPager);

        HomeFragment homeFragment= new HomeFragment();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment()); //新建一个homeFragment对象将这个对象加入到数组fragments中
        fragments.add(new OrderFragment());
        fragments.add(new UserFragment());

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments);
        //创建对象并通过构造函数初始化，该适配器可以知道要显示哪些片段。
        viewPager.setAdapter(viewPagerAdapter);
        //将前面创建的 viewPagerAdapter 适配器设置给 viewPager 视图组件，以便在 ViewPager 中显示相应的页面。

        //底部导航栏监听事件
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //根据菜单ID显示页面
                if(item.getItemId() == R.id.item_home){//监听事件中，点击菜单立马执行item.getItemId()方法
                    //item.getItemId() 方法用于获取选中的 MenuItem 的唯一标识符（ID）
                    // viewPager.setCurrentItem(0);
                    // 将 ViewPager 的当前页面显示成索引为 0 的页面

                    return true;
                }else if(item.getItemId() == R.id.item_order){
                    viewPager.setCurrentItem(1);
                    return true;
                }else if(item.getItemId() == R.id.item_user){
                    viewPager.setCurrentItem(2);
                    return true;
                }
                return false;
            }
        });

        // 添加页面切换的监听器，根据页面切换实现菜单切换
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){  // 根据页面位置更新导航栏的选中状态
                    case 0:
                        navigationView.setSelectedItemId(R.id.item_home);
                        //将导航栏中的选中项设置为 R.id.item_home
                        break;
                    case 1:
                        navigationView.setSelectedItemId(R.id.item_order);
                        break;
                    case 2:
                        navigationView.setSelectedItemId(R.id.item_user);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });//通过使用页面切换监听器，
        // 我们可以根据页面切换的情况来更改导航栏的选中状态，
        // 从而实现页面切换时导航栏菜单的同步切换效果。

    }

}