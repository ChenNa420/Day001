package com.baway.demo1.p;

import com.baway.demo1.bean.Product;
import com.baway.demo1.m.MyModel;
import com.baway.demo1.m.MyModelInterFace;
import com.baway.demo1.v.ViewInterFace;

/**
 * @Author：dell
 * @E-mail： 15001194794@163.com
 * @Date：2019/3/13 16:34
 * @Description：描述信息
 */
public class MyPresenter implements PresenterInterFace{
    //获取model层的对象
    MyModelInterFace myModel;
    ViewInterFace viewInterFace;

    public MyPresenter(ViewInterFace viewInterFace) {
        myModel=new MyModel();
        this.viewInterFace = viewInterFace;
    }

    @Override
    public void toModel() {
        //调用请求数据的方法
        myModel.toRequest("http://172.17.8.100/small/commodity/v1/commodityList", null, 1, new MyModel.MyCallBack() {
            @Override
            public void succes(Object obj) {
                //拿到返回数据list
                //通过接口对象把list传到view
                if(obj instanceof Product){
                  Product  Product=(Product) obj;
                  viewInterFace.refreshDisplay(Product.getResult().getMlss().getCommodityList());
                }
            }

            @Override
            public void error(String str) {

            }
        });
    }

    @Override
    public void onDestroy() {
        viewInterFace=null;
    }
}
