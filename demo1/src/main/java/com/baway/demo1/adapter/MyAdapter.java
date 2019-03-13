package com.baway.demo1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway.demo1.R;
import com.baway.demo1.bean.Product;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * @Author：dell
 * @E-mail： 15001194794@163.com
 * @Date：2019/3/13 19:13
 * @Description：描述信息
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.holder>{

    List<Product.ResultBean.MlssBean.CommodityListBeanXX> mlist;
    Context mcontext;

    public MyAdapter(List<Product.ResultBean.MlssBean.CommodityListBeanXX> mlist, Context mcontext) {
        this.mlist = mlist;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyAdapter.holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.layout_item,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.holder holder, int i) {
        String name = mlist.get(i).getCommodityName();
        String pic = mlist.get(i).getMasterPic();
        holder.textView.setText(name);
        Glide.with(mcontext).load(pic).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(mlist!=null){
            return mlist.size();
        }
        return 0;
    }
    public class holder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;
        public holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.text);
        }
    }
}
