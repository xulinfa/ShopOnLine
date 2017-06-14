package com.example.xulf.shoponline.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.MyOrderActivity;
import com.example.xulf.shoponline.MyOrderDetailActivity;
import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.bean.GetOrderProduct;
import com.example.xulf.shoponline.bean.OrderBean;;
import com.example.xulf.shoponline.bean.ReturnJson;

import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XuLF on 2017/3/14.
 * 订单适配器
 */
public class OrderAdapter extends BaseAdapter{

    private List<OrderBean> list;//订单数据

    private OrderBean orderBean;//一条订单

    private GetOrderProductAdapter getOrderProductAdapter;//获取订单里面的商品的适配器

    private MyOrderActivity context;//上下文

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();

    private String code;//点中的code，得到订单ID



    public OrderAdapter(List<OrderBean> list,MyOrderActivity context) {
        this.list = list;
        this.context=context;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View view1=null;
   /*     if (view!=null){
            view1=view;
        }else {*/
            view1= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order,null);

            //设置试图宽高属性
            /*ViewGroup.LayoutParams layoutParams=(ViewGroup.LayoutParams)viewGroup.getLayoutParams();
            layoutParams.width=viewGroup.getWidth()/10*4 ;
            view1.setLayoutParams(layoutParams);*/

            TextView isJiaoyi=(TextView)view1.findViewById(R.id.tv_jaioyi_now);//是否进行交易
            ListView listView=(ListView)view1.findViewById(R.id.lv_order_list);//订单列表项
            Button btnSure=(Button)view1.findViewById(R.id.btn_order_sure);//订单确认
            Button btnDetail=(Button)view1.findViewById(R.id.btn_order_detail);//查看订单详情


            if(list.get(i).getOrderState().equals("2")){
                btnSure.setVisibility(View.GONE);
                isJiaoyi.setText("交易完成");
            }else {
                btnSure.setVisibility(View.VISIBLE);
                isJiaoyi.setText("正在交易");
            }

            /*查看你订单详情*/
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderBean=list.get(i);//得到相应订单
                    /*跳转到订单详情页*/
                    Intent intent=new Intent(context, MyOrderDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("OrderDetail",orderBean);
                    intent.putExtras(bundle);
                    context.startActivity(intent);


                }
            });
            /*点击确认订单*/
            btnSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    code=list.get(i).getOrderID();
                    //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    //    设置Title的图标
                    builder.setIcon(R.mipmap.icon_oeder_check);
                    //    设置Title的内容
                    builder.setTitle("确认订单");
                    //    设置Content来显示一个信息
                    builder.setMessage("确定已完成吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            changeOrderState(code);//确认订单的方法
                            notifyDataSetChanged();
                            context.update();//刷新
                        }
                    });

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    //    显示出该对话框
                    builder.show();


                }
            });



            List<GetOrderProduct> orderProducts=list.get(i).getOrderProductList();//订单中的产品信息
            getOrderProductAdapter=new GetOrderProductAdapter(orderProducts);//订单中的产品列表适配器
            listView.setAdapter(getOrderProductAdapter);
            fixListViewHeight(listView);//重新测量ListView的宽高
            listView.setFocusable(false);//隐藏焦点




    /*    }*/

        return view1;
    }

    /*改变订单状态*/
    public void changeOrderState(String ordercode){
        Map<String,Object> params = new HashMap<>(2);
        params.put("orderID",ordercode);
        params.put("orderState","2");
        okHttpHelper.post(Contants.ALERTORDERSTATE, params, new MyCallback<ReturnJson<String >>(context) {
            @Override
            public void doRequestBefore(Request request) {

            }

            @Override
            public void onError(Response response) {

            }

            @Override
            public void onSuccess(Response response, ReturnJson<String> stringReturnJson) {
                String code=stringReturnJson.getCode();//返回码
                String msg=stringReturnJson.getMsg();//返回文字
                if(code.equals("200")) {
                    //返回码200代表成功
                    Log.i("makesureOrder","success");
                }else{
                    Log.i("OrderGetErrcode","code:"+code+",msg:"+msg);//log输出错误码
                }
            }

        });
    }


    /*为ListView重新布局*/
    public void fixListViewHeight(ListView listView) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listViewItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listViewItem.measure(0, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度
        // params.height设置ListView完全显示需要的高度
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }



}
