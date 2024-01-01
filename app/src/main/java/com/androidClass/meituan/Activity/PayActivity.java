package com.androidClass.meituan.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.androidClass.meituan.Adapter.OrderFoodAdapter;
import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Address;
import com.androidClass.meituan.model.Food;
import com.androidClass.meituan.model.Store;
import com.androidClass.meituan.utils.OKHttpUtils;
import com.androidClass.meituan.utils.SPUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayActivity extends AppCompatActivity {

    private TextView addressTextView;
    private TextView consigneeTextView;
    private TextView phoneTextView;
    private TextView timeTextView;
    private TextView storeNameTextView;
    private TextView takeOutNumTextView;
    private TextView allNumTextView;
    private TextView orderAmountTextView;
    private ImageButton checkAddressButton;
    private ListView orderFoodListView;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private ImageLoaderConfiguration config;
    private OrderFoodAdapter orderFoodAdapter;
    private Button toPayButton;
    private List<Food> foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        addressTextView = findViewById(R.id.address_TextView);
        consigneeTextView = findViewById(R.id.consignee_TextView);
        phoneTextView = findViewById(R.id.phone_TextView);
        timeTextView = findViewById(R.id.time_TextView);
        storeNameTextView = findViewById(R.id.storeName_TextView);
        takeOutNumTextView = findViewById(R.id.takeOutNum_TextView);
        allNumTextView = findViewById(R.id.allNum_TextView);
        orderAmountTextView = findViewById(R.id.order_AmountTextView);
        checkAddressButton = findViewById(R.id.checkAddress_Button);
        toPayButton = findViewById(R.id.toPay_Button);

        initInfo();
        initImageLoader();
        initListView();
        checkAddressButtonListener();
        payButtonListener();

    }

    /**
     * 初始化ImageLoader配置
     */
    private void initImageLoader() {
        config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .build();
        imageLoader.init(config);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    private void initListView(){
        Bundle extras = getIntent().getExtras();
        String orderFoodStr =(String) extras.get("orderFoods");
        foods = JSON.parseObject(orderFoodStr, new TypeReference<List<Food>>() {
        });
        orderFoodListView = findViewById(R.id.orderFood_ListView);
        orderFoodAdapter = new OrderFoodAdapter(PayActivity.this, R.layout.order_food_item, foods, imageLoader);
        orderFoodListView.setAdapter(orderFoodAdapter);
    }

    // 初始化信息
    private void initInfo(){
        // 地址信息填充
        OKHttpUtils okHttpUtils = new OKHttpUtils();
        String phone = (String) SPUtils.get(getApplicationContext(), "phoneNumber", "");
        Map<String,Object> map = new HashMap<>();
        map.put("phoneNumber",phone);
        okHttpUtils.post("/address/getDefault",map);
        okHttpUtils.setOnOKHttpGetListener(new OKHttpUtils.OKHttpGetListener() {
            @Override
            public void error(String error) {
                Toast.makeText(getApplicationContext(), "服务器出错啦，请重试", Toast.LENGTH_LONG).show();
            }

            @Override
            public void success(String json) {
                Address address = JSON.parseObject(json, new TypeReference<Address>() {
                });
                if (address == null){
                    addressTextView.setText("前往选择地址");
                    consigneeTextView.setText("前往选择联系人");
                    phoneTextView.setText("前往选择手机号");
                }else{
                    addressTextView.setText(address.getDetail());
                    consigneeTextView.setText(address.getConsignee());
                    phoneTextView.setText(address.getPhoneNumber());
                }
            }
        });
        LocalTime now = LocalTime.now();
        LocalTime arrTime = now.plusMinutes(30);
        LocalTime arrTime1 = now.plusMinutes(45);
        int hour = arrTime.getHour();
        int minute = arrTime.getMinute();
        timeTextView.setText(String.format("立即配送  预计%d:%d-%d:%d送达",hour,minute,arrTime1.getHour(),arrTime1.getMinute()));

        Bundle bundle = getIntent().getExtras();
        String storeStr = (String) bundle.get("store");
        String allNum =(String) bundle.get("allNum");
        Store store = JSON.parseObject(storeStr, new TypeReference<Store>() {
        });
        storeNameTextView.setText(store.getStoreName());
        takeOutNumTextView.setText(String.format("￥%s",store.getDeliveryNum()));
        allNumTextView.setText(allNum);
        double a = Double.parseDouble(allNum.substring(1));
        double b = Double.parseDouble(store.getDeliveryNum());
        double sum = a + b;
        // 使用 DecimalFormat 限制小数位数为两位
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedTotalPrice = df.format(sum);
        orderAmountTextView.setText(String.format("￥%s",formattedTotalPrice));
    }

    private void checkAddressButtonListener(){
        checkAddressButton.setOnClickListener(v->{
            startActivity(new Intent(this, AddAddressActivity.class));
        });
    }

    // 支付按钮监听
    private void payButtonListener(){
        toPayButton.setOnClickListener(v->{
            //创建提醒对话框的构建器
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("正在支付中");
            builder.setMessage("您将支付这笔订单，支付完成可在订单中查看。");
            //设置肯定按钮
            builder.setPositiveButton("确定", (dialog, which) -> {
                // TODO 发送请求生成订单
                OKHttpUtils okHttpUtils = new OKHttpUtils();
                Map<String,Object> map = new HashMap<>();
            });
            //设置否定按钮
            builder.setNegativeButton("取消",(dialog, which)->{

            });

            //根据构建器构建对话框对象
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

}