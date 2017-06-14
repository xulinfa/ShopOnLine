package com.example.xulf.shoponline.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.bean.Type;
import com.example.xulf.shoponline.com.loopj.android.image.SmartImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by XuLF on 2016/12/28.
 */
public class HomeGVAdapter extends BaseAdapter {
    private  List<Type> lits;
    private LayoutInflater mInflater;
    private Type type=null;

    public HomeGVAdapter(List<Type> lits) {
        this.lits = lits;
    }

    @Override
    public int getCount() {
        return lits.size();
    }

    @Override
    public Object getItem(int i) {
        return lits.get(i);
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
            view1 = mInflater.inflate(R.layout.item_menu,null);
            TextView title=(TextView)view1.findViewById(R.id.txt_name);
            SmartImageView pic=(SmartImageView)view1.findViewById(R.id.iv_icon);
            type=lits.get(i);
            title.setText(type.getCategorys());
            pic.setImageUrl(type.getGetCategorysImg());

        }
        return view1;
    }
}
