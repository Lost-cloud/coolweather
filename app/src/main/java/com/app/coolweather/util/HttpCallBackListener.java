package com.app.coolweather.util;

/**
 * Created by hai on 2016/4/21.
 */
public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
