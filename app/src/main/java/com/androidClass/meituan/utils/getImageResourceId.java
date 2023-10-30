package com.androidClass.meituan.utils;

import android.content.Context;

public class getImageResourceId {
    public static int getImageResourceId(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

}
