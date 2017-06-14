package com.example.xulf.shoponline.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.bean.Product;
import com.example.xulf.shoponline.com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by XuLF on 2017/2/20.
 * 匪类产品适配器
 */
public class CategoryProListAdapter extends BaseAdapter {

    private List<Product> list;

    private Product product;

    public CategoryProListAdapter(List<Product> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final  int i, View view1, ViewGroup viewGroup) {
        View view=null;
        if(view1!=null){
            view=view1;
        }else{
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_product,null);
            SmartImageView smartImageView=(SmartImageView)view.findViewById(R.id.img_categorypro_hot);
            TextView textView=(TextView)view.findViewById(R.id.tv_categorypro_price);
            TextView textView1=(TextView)view.findViewById(R.id.tv_ategorypro_name);

            product=list.get(i);
            smartImageView.setImageUrl(product.getProductImg());
            textView1.setText(product.getProductName());
            textView.setText("￥"+product.getPrice());
        }

        return view;
    }
}
