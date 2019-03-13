package com.baway.demo1.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author：dell
 * @E-mail： 15001194794@163.com
 * @Date：2019/3/13 19:40
 * @Description：描述信息
 */
public class okHttpUtil {
    OkHttpClient okHttpClient;
    public static okHttpUtil okhttputil;

    private okHttpUtil(){
        okHttpClient=new OkHttpClient();
        okHttpClient.newBuilder().addInterceptor(new MyInterceptor()).build();
    }
    public static synchronized okHttpUtil getInstace(){
       if(okhttputil==null) {
           okhttputil = new okHttpUtil();
       }
       return okhttputil;
    }
    public void doGet(String url, Map<String,String>map, final Handler handler,final int type){
        if(map!=null && map.size()>0){
            String str="";
            StringBuilder builder=new StringBuilder();
            for (String key:map.keySet()) {
                String pkey=key;
                String value=map.get(pkey);
                builder.append(pkey)
                        .append("=")
                        .append(value)
                        .append("&");
            }
            str=builder.toString();
            int index = str.lastIndexOf("&");
            str = str.substring(0, index);
            url=url+"?"+str;
        }
        Request request=new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("aaa",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json=response.body().string();
                Message message=new Message();
                message.obj=json;
                message.arg1=type;
                handler.sendMessage(message);
            }
        });
    }
    public class MyInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request=chain.request();
            Response response = chain.proceed(request);

            return response;
        }
    }
}
