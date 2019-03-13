package com.baway.demo1.v;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.baway.demo1.MainActivity;
import com.baway.demo1.R;
import com.baway.demo1.adapter.MyAdapter;
import com.baway.demo1.bean.Product;
import com.baway.demo1.p.MyPresenter;
import com.baway.demo1.p.PresenterInterFace;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：dell
 * @E-mail： 15001194794@163.com
 * @Date：2019/3/13 16:46
 * @Description：描述信息
 */
public class MyActivity extends Activity implements ViewInterFace{
    RecyclerView recyclerView;
    PresenterInterFace presenterInterFace;
    MyAdapter myAdapter;
    List<Product.ResultBean.MlssBean.CommodityListBeanXX> mlist=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        recyclerView=findViewById(R.id.recyclerView);
        //实例化布局管理
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        //设置管理器显示方式
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //衔接recycleView
        recyclerView.setLayoutManager(layoutManager);
        //设置适配器
        myAdapter=new MyAdapter(mlist,this);
        recyclerView.setAdapter(myAdapter);
        //p接口对象实例化.
        presenterInterFace=new MyPresenter(this);
        //请求数据
        presenterInterFace.toModel();
    }

    @Override
    public void refreshDisplay(List<Product.ResultBean.MlssBean.CommodityListBeanXX> list) {
        mlist.addAll(list);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterInterFace.onDestroy();
        presenterInterFace=null;
    }
}
