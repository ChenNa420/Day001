package com.baway.demo1.m;

import android.os.Handler;
import android.os.Message;

import com.baway.demo1.bean.Product;
import com.baway.demo1.util.OkhttpUtile;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;


/**
 * @Author：dell
 * @E-mail： 15001194794@163.com
 * @Date：2019/3/13 16:26
 * @Description：描述信息
 */
public class MyModel implements MyModelInterFace{

    //全局变量
    MyCallBack myCallBack;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int type=msg.arg1;
            switch (type){
                case 1:
                    String json= (String) msg.obj;
                    Gson gson=new Gson();
                    Product product=gson.fromJson(json,Product.class);
                    myCallBack.succes(product);
                    break;
                case 2:
                    String json2=(String) msg.obj;
                    Gson gson1=new Gson();
                    Product product1=gson1.fromJson(json2,Product.class);
                    myCallBack.succes(product1);
                    break;
            }
        }
    };

    @Override
    public void toRequest(String url, Map<String, String> map, int type,final MyModel.MyCallBack myCallBack) {
        this.myCallBack=myCallBack;
        OkhttpUtile.getInstace().doGet(url,map,handler,type);
    }
    //
    public interface MyCallBack {
        public void succes(Object obj);
        public void error(String str);
    }
}
