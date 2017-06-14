package com.example.xulf.shoponline.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.bean.Type;
import com.example.xulf.shoponline.com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by XuLF on 2016/12/28.
 * 分类左边的适配器
 */
public class CategoryNameAdapter extends BaseAdapter {
    private  List<Type> list;
    private LayoutInflater mInflater;
    private Type type=null;

    public CategoryNameAdapter(List<Type> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1=null;
        if(view!=null){
            view1=view;
        }else {
            mInflater = LayoutInflater.from(viewGroup.getContext());
            view1 = mInflater.inflate(R.layout.item_text_category_left,null);
            TextView title=(TextView)view1.findViewById(R.id.tv_category_left_typename);
            type=list.get(i);
            title.setText(type.getCategorys());

        }
        return view1;
    }
}
