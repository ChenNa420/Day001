package com.baway.demo1.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.text.Format;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpUtile {

    OkHttpClient okHttpClient;

    public static OkhttpUtile okhttpUtile;
    //构造方法私有化
    private OkhttpUtile(){
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().addInterceptor(new MyInterceptor())
                .build();
    }

    public static synchronized OkhttpUtile getInstace(){
        if(okhttpUtile == null){
            okhttpUtile = new OkhttpUtile();
        }
        return okhttpUtile;
    }

    public void doGet(String url , Map<String ,String> map , final Handler mH , final int type){
        if(map != null && map.size() >0){
            //遍历参数集合
            String str = "";
            StringBuilder builder = new StringBuilder();
            for(String key : map.keySet()){
                String pkey = key;
                String value = map.get(pkey);
                builder.append(pkey)
                        .append("=")
                        .append(value)
                        .append("&");
            }
            str = builder.toString();
            //因为for循环遍历键值对 所以会多拼接一个&
            int index = str.lastIndexOf("&");
            str = str.substring(0 , index);
            url = url+ "?" + str;
        }

        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);

        //使用了okhttp的异步请求方式
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("tag" ,"e == " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功 获取到json字符串
                String json = response.body().string();
                Message message = new Message();
                message.obj = json;
                message.arg1 = type;
                mH.sendMessage(message);
            }
        });
    }

    public void doPost(String url , Map<String ,String> map , final int type , final Handler mH){
        FormBody.Builder formBody = new FormBody.Builder();
        for(String str : map.keySet()){
            formBody.add(str , map.get(str));
        }

        RequestBody body = formBody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                Message message = new Message();
                message.obj = json;
                message.arg1 = type;
                mH.sendMessage(message);
            }
        });
    }

   public class MyInterceptor implements Interceptor{

       @Override
       public Response intercept(Chain chain) throws IOException {
           Request request = chain.request();
           Response response = chain.proceed(request);
           Log.e("tag" ,"Url = " + request.url());
           Log.e("tag" ,"response = " + response.body().string());
           return response;
       }
   }
}
