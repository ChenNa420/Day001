package com.baway.demo1.m;

import java.util.Map;

/**
 * @Author：dell
 * @E-mail： 15001194794@163.com
 * @Date：2019/3/13 16:23
 * @Description：描述信息
 */
public interface MyModelInterFace {
    public void toRequest(String url, Map<String,String>map,int type,MyModel.MyCallBack myCallBack);
}
