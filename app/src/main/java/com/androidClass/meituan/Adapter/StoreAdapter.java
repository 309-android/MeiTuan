package com.androidClass.meituan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.androidClass.meituan.utils.getImageResourceId;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Store;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class StoreAdapter extends ArrayAdapter {

    private static class ViewHolder {
        ImageView storeImage;
        TextView storeName;
        TextView storeScore;
        TextView monthSale;
        TextView peopleAvg;
        TextView minTakeOutNum;
        TextView deliveryNum;
        TextView comment;
    }

    // 创建ImageLoader对象
    private ImageLoader imageLoader ;


    private final int resourceId;

    public StoreAdapter(Context context, int textViewResourceId, List<Store> stores,ImageLoader imageLoader){
        super(context,textViewResourceId,stores);
        resourceId = textViewResourceId;
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        Store store = (Store) getItem(position); // 获取当前项的Store实例
//        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
//        // 获取要动态填充的视图对象
////        TextView storeId =(TextView) view.findViewById(R.id.storeId_TextView);
//        ImageView storeImage = (ImageView) view.findViewById(R.id.store_image);//获取该布局内的图片视图
//        TextView storeName = (TextView) view.findViewById(R.id.store_name);//获取该布局内的文本视图
//        TextView storeScore =(TextView) view.findViewById(R.id.storeScore_TextView);
//        TextView monthSale =(TextView) view.findViewById(R.id.monthSale_TextView);
//        TextView peopleAvg =(TextView) view.findViewById(R.id.peopleAvg_TextView);
//        TextView minTakeOutNum =(TextView) view.findViewById(R.id.minTakeOutNum_TextView);
//        TextView deliveryNum =(TextView) view.findViewById(R.id.deliveryNum_TextView);
//        TextView comment =(TextView) view.findViewById(R.id.comment_TextView);
//
//        // 设置值
////        storeId.setText(String.format("%d",store.getId()));
//
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .build();
//
//        imageLoader.displayImage(store.getImage(),storeImage,options);
//
//        //storeImage.setImageResource(getImageResourceId.getImageResourceId(getContext(),store.getImage()));//为图片视图设置图片资源
//        storeName.setText(store.getStoreName());//为文本视图设置文本内容
//        storeScore.setText(String.format("%s分",store.getStoreScore()));// 店铺评分设置
//        monthSale.setText(String.format("月售%s+",store.getMonthSale()));// 店铺月售设置
//        peopleAvg.setText(String.format("人均 ￥%s",store.getPeopleAvg()));// 店铺人均设置
//        minTakeOutNum.setText(String.format("起送 ￥%s",store.getMinTakeOutNum()));// 店铺起送价格
//        deliveryNum.setText(String.format("配送 约 ￥%s",store.getDeliveryNum()));// 店铺配送费设置
//        comment.setText(store.getComment());// 店铺评价设置
//
//        return view;
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            holder =    new ViewHolder();
            holder.storeImage = convertView.findViewById(R.id.store_image);
            holder.storeName = convertView.findViewById(R.id.store_name);
            holder.storeScore = convertView.findViewById(R.id.storeScore_TextView);
            holder.monthSale = convertView.findViewById(R.id.monthSale_TextView);
            holder.peopleAvg = convertView.findViewById(R.id.peopleAvg_TextView);
            holder.minTakeOutNum = convertView.findViewById(R.id.minTakeOutNum_TextView);
            holder.deliveryNum = convertView.findViewById(R.id.deliveryNum_TextView);
            holder.comment = convertView.findViewById(R.id.comment_TextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Store store = (Store) getItem(position); // 获取当前项的Store实例

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        imageLoader.displayImage(store.getImage(), holder.storeImage, options);

        holder.storeName.setText(store.getStoreName());//为文本视图设置文本内容
        holder.storeScore.setText(String.format("%s分",store.getStoreScore()));// 店铺评分设置
        holder.monthSale.setText(String.format("月售%s+",store.getMonthSale()));// 店铺月售设置
        holder.peopleAvg.setText(String.format("人均 ￥%s",store.getPeopleAvg()));// 店铺人均设置
        holder.minTakeOutNum.setText(String.format("起送 ￥%s",store.getMinTakeOutNum()));// 店铺起送价格
        holder.deliveryNum.setText(String.format("配送 约 ￥%s",store.getDeliveryNum()));// 店铺配送费设置
        holder.comment.setText(store.getComment());// 店铺评价设置

        return convertView;
    }
}
