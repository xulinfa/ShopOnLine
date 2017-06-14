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
 * Created by XuLF on 2017/1/18.
 * 分类适配器
 */
public class CategoryAdapter extends BaseAdapter{

    private List<Type> list;//数据
    private Type type=null;


    public CategoryAdapter(List<Type> list) {
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
        if (view!=null){
            view1=view;
        }else {
            view1= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category,null);

            //设置试图宽高属性
            /*ViewGroup.LayoutParams layoutParams=(ViewGroup.LayoutParams)viewGroup.getLayoutParams();
            layoutParams.width=viewGroup.getWidth()/10*4 ;
            view1.setLayoutParams(layoutParams);*/

            TextView title=(TextView)view1.findViewById(R.id.txt_name);
            SmartImageView pic=(SmartImageView)view1.findViewById(R.id.iv_icon);
            type=list.get(i);
            title.setText(type.getCategorys());
            pic.setImageUrl(type.getGetCategorysImg());
        }

        return view1;
    }
}
