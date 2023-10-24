package com.androidClass.meituan.utils;

/**
 * 请求回调
 *
 */
public interface OnResponseListener {

    void onSuccess(String response);

    void onError(String error);
}
