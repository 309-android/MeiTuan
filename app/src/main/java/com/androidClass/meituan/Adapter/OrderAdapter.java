package com.androidClass.meituan.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Order;
import com.androidClass.meituan.utils.getImageResourceId;

import java.util.List;

public class OrderAdapter extends ArrayAdapter {

    private final int resourceId;

    public OrderAdapter(Context context, int textViewResourceId, List<List<Order>> orders){
        super(context,textViewResourceId,orders);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        List<Order> orders = (List<Order>) getItem(position); // 获取当前项的Store实例
        Log.d("order",orders.toString());
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        // 获取要动态填充的视图对象
        // 店铺图
        ImageView orderStoreImage =(ImageView) view.findViewById(R.id.orderStoreImage_ImageView);
        // 店铺名
        TextView orderStoreName =(TextView) view.findViewById(R.id.orderStoreName_TextView);
        // 订单状态
        TextView orderStatus = (TextView) view.findViewById(R.id.orderStatus_TextView);//获取该布局内的图片视图
        // 食物图片1
        ImageView orderFoodImageOne = (ImageView) view.findViewById(R.id.orderFoodImageOne_ImageView);
        // 食物名字1
        TextView orderFoodNameOne = (TextView) view.findViewById(R.id.orderFoodNameOne_TextView);
        // 食物图片2
        ImageView orderFoodImageTwo = (ImageView) view.findViewById(R.id.orderFoodImageTwo_ImageView);
        // 食物名字2
        TextView orderFoodNameTwo = (TextView) view.findViewById(R.id.orderFoodNameTwo_TextView);
        // 食物图片3
        ImageView orderFoodImageThree = (ImageView) view.findViewById(R.id.orderFoodImageThree_ImageView);
        // 食物名字3
        TextView orderFoodNameThree = (TextView) view.findViewById(R.id.orderFoodNameThree_TextView);
        // 订单金额
        TextView orderAmount =(TextView) view.findViewById(R.id.orderAmount_TextView);
        // 订单件数
        TextView orderNum =(TextView) view.findViewById(R.id.orderNum_TextView);

        // 设置值
        // orderStoreImage.setImageResource(getImageResourceId.getImageResourceId(getContext(),order.get));

        if(orders.size() == 1){
            Order orderDO = orders.get(0);
            // 店铺缩略图
            orderStoreImage.setImageResource(getImageResourceId.getImageResourceId(getContext(),orderDO.getStore().getImage()));
            // 店铺名
            orderStoreName.setText(orderDO.getStore().getStoreName());
            // 订单状态
            orderStatus.setText(orderDO.getStatus().equals("1") ? "已完成":"未完成");
            // 食物图片1
            orderFoodImageOne.setImageResource(getImageResourceId.getImageResourceId(getContext(),orderDO.getFood().getFoodImg()));
            // 食物名字1
            orderFoodNameOne.setText(orderDO.getFood().getFoodName());
            // 订单金额
            orderAmount.setText(String.format(" ￥%d",orderDO.getOrderAmount()));
            // 订单件数
            orderNum.setText("共 1 件");
        }else if(orders.size() == 2){
            Order orderDO1 = orders.get(0);
            Order orderDO2 = orders.get(1);
            // 店铺缩略图
            orderStoreImage.setImageResource(getImageResourceId.getImageResourceId(getContext(),orderDO1.getStore().getImage()));
            // 店铺名
            orderStoreName.setText(orderDO1.getStore().getStoreName());
            // 订单状态
            orderStatus.setText(orderDO1.getStatus().equals("1") ? "已完成":"未完成");
            // 食物图片1
            orderFoodImageOne.setImageResource(getImageResourceId.getImageResourceId(getContext(),orderDO1.getFood().getFoodImg()));
            // 食物名字1
            orderFoodNameOne.setText(orderDO1.getFood().getFoodName());
            // 食物图片2
            orderFoodImageTwo.setImageResource(getImageResourceId.getImageResourceId(getContext(),orderDO2.getFood().getFoodImg()));
            // 食物名字2
            orderFoodNameTwo.setText(orderDO2.getFood().getFoodName());
            // 订单金额
            String amount = String.valueOf(orderDO1.getOrderAmount() + orderDO2.getOrderAmount());
            orderAmount.setText(String.format(" ￥%s",amount));
            // 订单件数
            orderNum.setText("共 2 件");
        }else if(orders.size() == 3){
            Order orderDO1 = orders.get(0);
            Order orderDO2 = orders.get(1);
            Order orderDO3 = orders.get(2);
            // 店铺缩略图
            orderStoreImage.setImageResource(getImageResourceId.getImageResourceId(getContext(),orderDO1.getStore().getImage()));
            // 店铺名
            orderStoreName.setText(orderDO1.getStore().getStoreName());
            // 订单状态
            orderStatus.setText(orderDO1.getStatus().equals("1") ? "已完成":"未完成");
            // 食物图片1
            orderFoodImageOne.setImageResource(getImageResourceId.getImageResourceId(getContext(),orderDO1.getFood().getFoodImg()));
            // 食物名字1
            orderFoodNameOne.setText(orderDO1.getFood().getFoodName());
            // 食物图片2
            orderFoodImageTwo.setImageResource(getImageResourceId.getImageResourceId(getContext(),orderDO2.getFood().getFoodImg()));
            // 食物名字2
            orderFoodNameTwo.setText(orderDO2.getFood().getFoodName());
            // 食物图片3
            orderFoodImageThree.setImageResource(getImageResourceId.getImageResourceId(getContext(),orderDO3.getFood().getFoodImg()));
            // 食物名字3
            orderFoodNameThree.setText(orderDO3.getFood().getFoodName());
            // 订单金额
            String amount = String.valueOf(orderDO1.getOrderAmount() + orderDO2.getOrderAmount() + orderDO3.getOrderAmount());
            orderAmount.setText(String.format(" ￥%s",amount));
            // 订单件数
            orderNum.setText("共 3 件");
        }

        return view;
    }
}
