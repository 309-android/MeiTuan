package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.androidClass.meituan.Adapter.StoreAdapter;
import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Store;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShowModuleActivity extends AppCompatActivity {

    // 返回按钮
    private ImageButton backToHomePageFromModule;
    // 模块名
    private TextView moduleName;
    // 数据list
    private ListView moduleList;
    // 搜索框内容
    private EditText inputContent;
    // 数据
    private List<Store> stores = new ArrayList<>();
    // 店铺适配器
    private StoreAdapter storeAdapter;

    private ImageLoaderConfiguration config;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_module);
        // 返回按钮
        initBackButton();
        // 模块名
        moduleName = findViewById(R.id.moduleName_TextView);
        // 数据list
        moduleList = findViewById(R.id.moduleList_ListView);
        // 搜索框内容
        inputContent = findViewById(R.id.inputContent_EditText);
        // 初始化数据
        initStores();
    }

    /**
     * 返回按钮跳转
     */
    private void initBackButton() {
        // 返回按钮
        backToHomePageFromModule = findViewById(R.id.backToHomePageFromModule_Button);
        backToHomePageFromModule.setOnClickListener(v ->{
            startActivity(new Intent(this, HomePageActivity.class));
        });
    }

    /**
     * 初始化数据
     */
    private void initStores() {
        // 初始化店铺数据
        OKHttpUtils okHttpUtils = new OKHttpUtils();
        // 获取首页传来的bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            // 从bundle中获取分类数据
            String storeCategory = (String) bundle.get("storeCategory");
            Map<String, Object> map = new HashMap<>();
            map.put("storeCategory",storeCategory);

            okHttpUtils.post("/store/get",map);

            okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
                @Override
                public void error(String error) {
                    Toast.makeText(getApplicationContext(), "服务器出错啦，请稍后再试", Toast.LENGTH_LONG).show();
                }

                @Override
                public void success(String json) {
                    // json数组转list对象数组
                    stores = JSON.parseObject(json, new TypeReference<List<Store>>() {
                    });

                    Log.d("young", "ModuleStores : " + stores.toString());

                    // 初始化listview
                    storeAdapter = new StoreAdapter(ShowModuleActivity.this, R.layout.store_item, stores,imageLoader);
                    moduleList.setAdapter(storeAdapter);

                }
            });

            switch (storeCategory){

                case "2": moduleName.setText("甜点饮品");
                break;

                case "3": moduleName.setText("超市便利");
                break;

                case "4": moduleName.setText("蔬菜水果");
                break;

                default: moduleName.setText("美食");
                break;
            }
        }
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