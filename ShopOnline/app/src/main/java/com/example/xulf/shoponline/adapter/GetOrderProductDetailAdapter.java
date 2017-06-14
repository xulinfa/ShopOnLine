package com.example.xulf.shoponline.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.AddPinglunActivity;
import com.example.xulf.shoponline.MyOrderDetailActivity;
import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.bean.GetOrderProduct;
import com.example.xulf.shoponline.com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by XuLF on 2017/3/14
 * 查看订单详情的时候，其中购买商品列表的适配器.
 */
public class GetOrderProductDetailAdapter extends BaseAdapter {
    private List<GetOrderProduct> getOrderProductList;

    private Context context;

    public GetOrderProductDetailAdapter(List<GetOrderProduct> productList,Context context) {
        this.getOrderProductList = productList;
        this.context=context;
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
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_product_detail,null);
            SmartImageView simpleDraweeView=(SmartImageView)view.findViewById(R.id.sdv_item_orderproductdetail_pic);

            TextView tvName=(TextView)view.findViewById(R.id.tv_Orderdetail_productName);//品名
            TextView tvDEc=(TextView)view.findViewById(R.id.tv_orderdetailitemproductDec);//描述
            TextView tvPrice=(TextView)view.findViewById(R.id.tv_orderdetailitemprice);//价格
            TextView tvNum=(TextView)view.findViewById(R.id.tv_orderdetailproductitemnum);//件数
           // Button btnshouhou=(Button)view.findViewById(R.id.btn_shouhou);//售后按钮
            Button btnPingjia=(Button)view.findViewById(R.id.btn_order_pingjia_id);//评价按钮

            btnPingjia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, AddPinglunActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    intent.putExtra("proID",getOrderProductList.get(i).getProductID());
                    context.startActivity(intent);

                }
            });


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
