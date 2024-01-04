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

        if (store !=null){
            imageLoader.displayImage(store.getImage(), holder.storeImage, options);

            holder.storeName.setText(store.getStoreName());//为文本视图设置文本内容
            holder.storeScore.setText(String.format("%s分",store.getStoreScore()));// 店铺评分设置
            holder.monthSale.setText(String.format("月售%s+",store.getMonthSale()));// 店铺月售设置
            holder.peopleAvg.setText(String.format("人均 ￥%s",store.getPeopleAvg()));// 店铺人均设置
            holder.minTakeOutNum.setText(String.format("起送 ￥%s",store.getMinTakeOutNum()));// 店铺起送价格
            holder.deliveryNum.setText(String.format("配送 约 ￥%s",store.getDeliveryNum()));// 店铺配送费设置
            holder.comment.setText(store.getComment());// 店铺评价设置
        }

        return convertView;
    }
}
