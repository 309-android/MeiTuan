package com.androidClass.meituan.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
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
import com.androidClass.meituan.model.Address;
import com.androidClass.meituan.model.Order;
import com.androidClass.meituan.utils.getImageResourceId;

import java.util.List;

public class AddressAdapter extends ArrayAdapter {

    private final int resourceId;

    public AddressAdapter(Context context, int textViewResourceId, List<Address> addresses) {
        super(context, textViewResourceId, addresses);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Address address = (Address) getItem(position); // 获取当前项的Store实例
        Log.d("order", address.toString());
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        // 获取要动态填充的视图对象
        // 地址标签
        TextView label_TextView = (TextView) view.findViewById(R.id.label_TextView);
        // 地址
        TextView detail_TextView = (TextView) view.findViewById(R.id.detail_TextView);
        // 收货人姓名
        TextView consignee_TextView = (TextView) view.findViewById(R.id.consignee_TextView);
        // 性别
        TextView gender_TextView = (TextView) view.findViewById(R.id.gender_TextView);
        // 电话号码
        TextView phoneNumber_TextView = (TextView) view.findViewById(R.id.phoneNumber_TextView);

        // 设置值

        if(address.getLabel() == null){
            label_TextView.setText("");
            label_TextView.setBackgroundColor(Color.rgb(255,255,255));
        }else{
            label_TextView.setText(address.getLabel());
        }
        detail_TextView.setText(address.getDetail());
        consignee_TextView.setText(address.getConsignee());
        gender_TextView.setText("1".equals(address.getSex()) ? "先生" : "女士");

        String[] split = address.getPhoneNumber().split("");
        StringBuilder phone = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if ( i >= 4){
                split[i] = "*";
            }
            phone.append(split[i]);
        }
        phoneNumber_TextView.setText(phone);


        return view;
    }
}
