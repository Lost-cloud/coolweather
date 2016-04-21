package com.app.coolweather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hai on 2016/4/21.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address,final HttpCallBackListener listener){

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try {
                    URL url=new URL(address);
                    connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream is=connection.getInputStream();
                    BufferedReader bf=new BufferedReader(new InputStreamReader(is));
                    StringBuilder stringBuilder=new StringBuilder();
                    String line;
                    while((line=bf.readLine())!=null){
                        stringBuilder.append(line);
                    }
                    if (listener!=null){
                        listener.onFinish(stringBuilder.toString());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    listener.onError(e);
                } catch (IOException e) {
                    e.printStackTrace();

                }finally {
                    if (connection!=null){
                        connection.disconnect();

                    }
                }
            }
        }).start();

    }
}
