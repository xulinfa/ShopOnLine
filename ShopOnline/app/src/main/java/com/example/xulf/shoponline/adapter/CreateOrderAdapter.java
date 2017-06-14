package com.example.xulf.shoponline.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.bean.OrderProduct;
import com.example.xulf.shoponline.bean.Product;
import com.example.xulf.shoponline.com.loopj.android.image.SmartImageView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by XuLF on 2017/2/23.
 */
public class CreateOrderAdapter extends BaseAdapter {
    private List<OrderProduct> productList;

    public CreateOrderAdapter(List<OrderProduct> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view1, ViewGroup viewGroup) {
        View view=null;
        if(view1!=null){
            view=view1;
        }else{
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_createorder,null);
            SmartImageView simpleDraweeView=(SmartImageView)view.findViewById(R.id.sdv_itempic);

            TextView tvName=(TextView)view.findViewById(R.id.tv_createOrder_productName);
            TextView tvDEc=(TextView)view.findViewById(R.id.tv_orderitemgetproductDec);
            TextView tvPrice=(TextView)view.findViewById(R.id.tv_itemprice);
            TextView tvNum=(TextView)view.findViewById(R.id.tv_productitemnum);

            if(productList.get(i).getProductImg()!=null) {
                simpleDraweeView.setImageUrl(productList.get(i).getProductImg());
            }
            //simpleDraweeView.setImageURI(Uri.parse(productList.get(i).getProductImg()));
            tvName.setText(productList.get(i).getProductName());
            tvDEc.setText(productList.get(i).getProductDetail());
            tvNum.setText("x "+productList.get(i).getBuy_number());
            tvPrice.setText("￥"+productList.get(i).getPrice()+"");
        }

        return view;
    }
}
