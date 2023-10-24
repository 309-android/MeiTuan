package com.androidClass.meituan.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//import retrofit2.http.Url;

/**
 * OKHttp网络请求工具类
 * get
 * post
 * 上传图片
 */

public class OKHttpUtils {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OKHttpGetListener onOKHttpGetListener;
    private MyHandler myHandler = new MyHandler();
    private static final String TAG = "OKHttpUitls";
    private OkHttpClient client = null;

    private String BaseUrl="http://10.0.2.2:8080";

//    private String BaseUrl="http://47.100.104.187:8080/ssm05/";

    // /get
    public void get(String url) {

        url=BaseUrl+url;
        try {
            if (client == null) {
                client = new OkHttpClient();
            }
            //创建请求对象
            Request request = new Request.Builder().url(url).build();
            //创建Call请求队列
            //请求都是放到一个队列里面的
            Call call = client.newCall(request);

            Log.d(TAG, "get() returned: " + call + "------------");
            //开始请求
            call.enqueue(new Callback() {
                //       失败，成功的方法都是在子线程里面，不能直接更新UI
                @Override
                public void onFailure(Call call, IOException e) {
                    Message message = myHandler.obtainMessage();
                    message.obj = "请求失败";
                    message.what = 0;
                    myHandler.sendMessage(message);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Message message = myHandler.obtainMessage();
                    String json = response.body().string();
                    message.obj = json;
                    message.what = 1;
                    myHandler.sendMessage(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //    post 请求
    public void post(String url, Map<String, Object> params) {
        try {
            if (client == null) {
                client = new OkHttpClient();
            }
            url=BaseUrl+url;
            FormBody.Builder builder = new FormBody.Builder();
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    Log.i("参数:", entry.getKey() + ":" + entry.getValue());
                    builder.add(entry.getKey(), entry.getValue().toString());
                }
            }


            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Message message = myHandler.obtainMessage();
                    message.obj = "请求失败";
                    message.what = 0;
                    myHandler.sendMessage(message);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Message message = myHandler.obtainMessage();
                    String json = response.body().string();
                    message.obj = json;
                    message.what = 1;
                    myHandler.sendMessage(message);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void POST_JSON(String url, Map<String, Object> params) {
        try {
            if (client == null) {
                client = new OkHttpClient();
            }
            url=BaseUrl + url;

            GsonBuilder builder = new GsonBuilder();
            // Register an adapter to manage the date types as long values
            builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            });

            Gson gson = builder.create();
            String json=gson.toJson(params);
            Log.d("json",json);

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();


            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Message message = myHandler.obtainMessage();
                    message.obj = "请求失败";
                    message.what = 0;
                    myHandler.sendMessage(message);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Message message = myHandler.obtainMessage();
                    String json = response.body().string();
                    message.obj = json;
                    message.what = 1;
                    myHandler.sendMessage(message);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

    //    上传图片地址
    public void photo(String url, List<String> photoPath, Map<String, Object> params) {

        try {
            url=BaseUrl+url;
            if (client == null) {
                client = new OkHttpClient();
            }
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

//            添加参数
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    Log.i("参数:", entry.getKey() + ":" + entry.getValue());
                    builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
                }
            }
//            添加图片
            if (photoPath.size() > 0) {
                for (int i = 0; i < photoPath.size(); i++) {
                    File f = new File(photoPath.get(i));
                    if (f == null) break;
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        Bitmap bm = BitmapFactory.decodeFile(f.getAbsolutePath(), options);
                        bm.compress(Bitmap.CompressFormat.JPEG, 90, new FileOutputStream(f));
                        bm.recycle();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    builder.addFormDataPart("multipartFile", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
                }
            }

            MultipartBody requesBody = builder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requesBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Message message = myHandler.obtainMessage();
                    message.obj = "请求失败";
                    message.what = 0;
                    myHandler.sendMessage(message);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Message message = myHandler.obtainMessage();
                    String json = response.body().string();
                    message.obj = json;
                    message.what = 1;
                    myHandler.sendMessage(message);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //使用接口回到，将数据返回
    public interface OKHttpGetListener {
        void error(String error);

        void success(String json);
    }

    //给外部调用的方法
    public void setOnOKHttpGetListener(OKHttpGetListener onOKHttpGetListener) {
        this.onOKHttpGetListener = onOKHttpGetListener;
    }


    //使用Handler，将数据在主线程返回
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int w = msg.what;
            Log.d(TAG, "handleMessage() returned: " + msg);
            if (w == 0) {
                String error = (String) msg.obj;
                onOKHttpGetListener.error(error);
            }
            if (w == 1) {
                String json = (String) msg.obj;
                onOKHttpGetListener.success(json);
            }
        }
    }

}