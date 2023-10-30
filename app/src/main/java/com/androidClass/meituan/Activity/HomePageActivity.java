package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.androidClass.meituan.Adapter.StoreAdapter;
import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页的activity
 */
public class HomePageActivity extends AppCompatActivity {

    private List<Store> storeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // 美食按钮监听
        findViewById(R.id.FineFood_Button).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"this is fineFood module",Toast.LENGTH_LONG).show();
        });

        // 饮品按钮监听
        findViewById(R.id.Drink_Button).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"this is drink module",Toast.LENGTH_LONG).show();
        });

        // 超市按钮监听
        findViewById(R.id.Market_Button).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"this is superMarket module",Toast.LENGTH_LONG).show();
        });

        // 水果按钮监听
        findViewById(R.id.Fruit_Button).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"this is fruit module",Toast.LENGTH_LONG).show();
        });

        initFoods();
        StoreAdapter adapter = new StoreAdapter(HomePageActivity.this, R.layout.food_item, storeList);
        ListView listview =(ListView) findViewById(R.id.foodList_ListView);
        listview.setAdapter(adapter);

    }

    private void initFoods(){


        for (int i = 0; i < 50; i++) {
            Store store = new Store();
            store.setStoreName("瑞幸咖啡");
            store.setImage("test");
            store.setStoreScore("4.6");
            store.setMonthSale(1000);
            store.setDeliveryNum(5);
            store.setMinTakeOutNum(20);
            store.setPeopleAvg(15);
            store.setComment("不好喝");

            storeList.add(store);
        }


    }
}