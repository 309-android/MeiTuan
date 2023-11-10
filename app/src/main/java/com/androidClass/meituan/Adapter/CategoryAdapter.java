package com.androidClass.meituan.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidClass.meituan.R;
import com.androidClass.meituan.model.Category;
import com.androidClass.meituan.model.Food;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter {

    private final int resourceId;

    private int selectedPosition = -1;  // 用于保存被点击的位置


    public CategoryAdapter(Context context, int textViewResourceId, List<Category> categories) {
        super(context, textViewResourceId, categories);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Category category = (Category) getItem(position); // 获取当前项的category实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        // 获取要动态填充的视图对象
        TextView chooseCategory = (TextView) view.findViewById(R.id.chooseCategory);

        if (position == selectedPosition) {
            chooseCategory.setTextColor(Color.BLACK);  // 如果是被点击的位置，设置文字颜色为黑色
        } else {
            chooseCategory.setTextColor(Color.rgb(135,133,137));  // 否则设置文字颜色为灰色
        }

        // 设置值
        chooseCategory.setText(category.getName());

        return view;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;  // 更新被点击的位置
        notifyDataSetChanged();  // 通知适配器刷新列表
    }
}
