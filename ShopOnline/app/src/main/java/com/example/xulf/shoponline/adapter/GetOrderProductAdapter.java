package com.example.xulf.shoponline.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.bean.GetOrderProduct;
import com.example.xulf.shoponline.bean.OrderProduct;
import com.example.xulf.shoponline.com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by XuLF on 2017/2/23.
 */
public class GetOrderProductAdapter extends BaseAdapter {
    private List<GetOrderProduct> getOrderProductList;

    public GetOrderProductAdapter(List<GetOrderProduct> productList) {
        this.getOrderProductList = productList;
    }

    @Override
    public int getCount() {
        return getOrderProductList.size();
    }

    @Override
    public Object getItem(int i) {
        return getOrderProductList.get(i);
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
            TextView tvDEc=(TextView)view.findViewById(R.id.tv_orderitemgetproductDec);//描述
            TextView tvPrice=(TextView)view.findViewById(R.id.tv_itemprice);
            TextView tvNum=(TextView)view.findViewById(R.id.tv_productitemnum);

            if(getOrderProductList.get(i).getProductImg()!=null) {
                simpleDraweeView.setImageUrl(getOrderProductList.get(i).getProductImg());
            }
            //simpleDraweeView.setImageURI(Uri.parse(productList.get(i).getProductImg()));
            tvName.setText(getOrderProductList.get(i).getProductName());
            tvDEc.setText(getOrderProductList.get(i).getProductDetail());
            tvNum.setText("x "+getOrderProductList.get(i).getBuyNumber());
            tvPrice.setText("￥"+getOrderProductList.get(i).getPrice()+"");
        }

        return view;
    }
}
