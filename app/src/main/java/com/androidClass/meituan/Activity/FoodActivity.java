package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidClass.meituan.Adapter.CategoryAdapter;
import com.androidClass.meituan.Adapter.FoodAdapter;
import com.androidClass.meituan.model.Category;
import com.androidClass.meituan.model.Food;
import com.androidClass.meituan.utils.SPUtils;

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

    private ListView categoryListView;

    private List<Category> categories;

    private List<Food> foods;

    private FoodAdapter foodAdapter;

    private CategoryAdapter categoryAdapter;
    private Button makeOrdersButton;

    Button showCommentButton;
    View makeOrdersUnderline;
    View showCommentUnderline;
    ImageButton backToHomePageFromFoodButton;
    ImageButton starStoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        makeOrdersUnderline = findViewById(R.id.makeOrdersUnderline_View);
        showCommentUnderline = findViewById(R.id.showCommentUnderline_View);

        // 返回按钮
        backToHomePageFromFoodButton = findViewById(R.id.backToHomePageFromFood_Button);
        // 收藏按钮
        starStoreButton = findViewById(R.id.starStore_Button);

        makeOrdersUnderline.setVisibility(View.VISIBLE);  // 显示小横线
        // 获取店铺id
        Bundle extras = getIntent().getExtras();
        String storeId = (String) extras.get("storeId");

        // 初始化ImageLoader配置
        initImageLoader();
        // 初始化店铺信息
        initStoreInfo();

        // 初始化菜品
        initFood();

        // 点菜监听
        clickMakeOrders();

        // 评价监听
        clickShowComments();

        // 初始化分类
        initCategory();

        // 返回按钮监听
        backToHomePageFromFoodButtonListener();

        // 收藏按钮监听
        starStoreButtonListener(storeId);

    }

    private void clickShowComments(){
        showCommentButton = findViewById(R.id.showComment_Button);

        showCommentButton.setOnClickListener(v -> {
            showCommentUnderline.setVisibility(View.VISIBLE);  // 显示小横线
            makeOrdersUnderline.setVisibility(View.INVISIBLE);  // 隐藏另一个按钮的小横线

        });
    }

    private void clickMakeOrders() {
        makeOrdersButton = findViewById(R.id.makeOrders_Button);

        makeOrdersButton.setOnClickListener( v -> {
            makeOrdersUnderline.setVisibility(View.VISIBLE);  // 显示小横线
            showCommentUnderline.setVisibility(View.INVISIBLE);  // 隐藏另一个按钮的小横线
            initFood();
        });
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

    /**
     * 初始化菜品
     */
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

    /**
     * 初始化分类
     */
    private void initCategory(){
        // 首页传来的店铺id
        Bundle bundle = getIntent().getExtras();
        String storeId = (String) bundle.get("storeId");

        OKHttpUtils okHttpUtils = new OKHttpUtils();
        Map<String,Object> map = new HashMap<>();
        map.put("storeId",storeId);
        okHttpUtils.post("/category/getAllByStoreId",map);
        okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
            @Override
            public void error(String error) {
                Toast.makeText(getApplicationContext(), "服务器出错啦，请稍后再试", Toast.LENGTH_LONG).show();
            }

            @Override
            public void success(String json) {
                categories = JSON.parseObject(json,new TypeReference<List<Category>>(){});

                if ("[]".equals(categories.toString())){
                    Toast.makeText(getApplicationContext(), "当前店铺还没有菜品分类哦", Toast.LENGTH_LONG).show();
                }else {
                    // 初始化listview
                    categoryAdapter = new CategoryAdapter(FoodActivity.this, R.layout.category_item, categories);
                    categoryListView = (ListView) findViewById(R.id.categoryList_ListView);
                    categoryListView.setAdapter(categoryAdapter);

                    // 分类点击事件
                    clickCategory(okHttpUtils);
                }
            }
        });
    }

    /**
     * 分类的点击事件
     */
    private void clickCategory(OKHttpUtils okHttpUtils){
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                categoryAdapter.setSelectedPosition(position);  // 更新被点击的位置

                Map<String,Object> map = new HashMap<>();
                map.put("categoryId",categories.get(position).getId());

                okHttpUtils.post("/food/getByCategoryId",map);
                okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
                    @Override
                    public void error(String error) {
                        Toast.makeText(getApplicationContext(), "服务器出错啦，请稍后再试", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void success(String json) {
                        foods = JSON.parseObject(json,new TypeReference<List<Food>>(){});

                        if ("[]".equals(categories.toString())){
                            Toast.makeText(getApplicationContext(), "当前店铺还没有菜品分类哦", Toast.LENGTH_LONG).show();
                        }else {
                            foodAdapter.clear();  // 清空原有数据
                            foodAdapter.addAll(foods);  // 添加新数据
                            foodAdapter.notifyDataSetChanged();  // 刷新界面
                        }
                    }
                });
            }
        });

    }

    /**
     * 返回按钮监听
     */
    private void backToHomePageFromFoodButtonListener(){
        backToHomePageFromFoodButton.setOnClickListener(v->{
            startActivity(new Intent(FoodActivity.this,HomePageActivity.class));
        });
    }

    /**
     * 收藏按钮监听
     */
    private void starStoreButtonListener(String storeId){
        starStoreButton.setOnClickListener(v->{
            // 获取用户手机号
            String phoneNumber = (String) SPUtils.get(getApplicationContext(), "phoneNumber", "");
            OKHttpUtils okHttpUtils = new OKHttpUtils();
            HashMap<String, Object> map = new HashMap<>();
            map.put("phoneNumber",phoneNumber);
            map.put("storeId",storeId);
            okHttpUtils.POST_JSON("/star/newStar",map);
            okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
                @Override
                public void error(String error) {
                    Toast.makeText(getApplicationContext(),"服务器出错啦，请稍后再试！",Toast.LENGTH_LONG).show();
                }

                @Override
                public void success(String json) {
                    String isStarred = JSON.parseObject(json, new TypeReference<String>() {
                    });
                    // 返回信息
                    Toast.makeText(getApplicationContext(),isStarred,Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}