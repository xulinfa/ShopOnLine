package com.example.xulf.shoponline;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.adapter.GetOrderProductDetailAdapter;
import com.example.xulf.shoponline.bean.GetOrderProduct;
import com.example.xulf.shoponline.bean.OrderBean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by XuLF on 2017/3/14.
 */
public class MyOrderDetailActivity extends AppCompatActivity {
    private TextView tvReceiverName;//收货人名字
    private TextView tvReceiverNum;//收货人号码
    private TextView tvOrderAdress;//收获地址
    private ListView lvOrderProduct;//订单商品列表
    private TextView tvAllOrderPrice;//总价
    private TextView tvPay;//实付
    private TextView tvOrderCode;//订单号
    //private Button btnCopyCode;//复制订单号按钮
    private TextView tvTime;//下单时间

    private  App app=App.getInstance();




    private GetOrderProductDetailAdapter getOrderProductDetailAdapter;//适配器

    private List<GetOrderProduct> getOrderProductList;//订单中的商品

    private OrderBean orderBean;//订单

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        app.myActivityManager.addActivity(this);//添加Activity

        tvReceiverName=(TextView)findViewById(R.id.tv_order_receiverName);//收货人姓名
        tvReceiverNum=(TextView)findViewById(R.id.tv_order_receiverNum);//收货人号码
        tvOrderAdress=(TextView)findViewById(R.id.tv_order_receiverAdress);//收货地址
        lvOrderProduct=(ListView)findViewById(R.id.lv_order_detail);//订单商品列表
        tvAllOrderPrice=(TextView)findViewById(R.id.tv_allPriceOfOrderDec);//总价
        tvPay=(TextView)findViewById(R.id.tv_payPrice);//实付
        tvOrderCode=(TextView)findViewById(R.id.tv_order_code);//订单号
       // btnCopyCode=(Button)findViewById(R.id.btn_copy_ordercode);//复制订单号按钮
        tvTime=(TextView)findViewById(R.id.tv_order_time_detail);//订单时间

        /*得到相应的订单*/
        Intent intent=this.getIntent();
        orderBean= (OrderBean) intent.getSerializableExtra("OrderDetail");
        /*设置值*/
        tvReceiverName.setText(orderBean.getRecipients());//收货人
        tvReceiverNum.setText(app.getUser().getUserName());//收获号码
        tvTime.setText(orderBean.getOrderTime());//shijian
        tvOrderAdress.setText(orderBean.getAddress());//地址
        tvOrderCode.setText(orderBean.getOrderID());//订单号



        /*得到订单里的商品*/
        getOrderProductList=orderBean.getOrderProductList();
        getOrderProductDetailAdapter=new GetOrderProductDetailAdapter(getOrderProductList,getApplicationContext());
        lvOrderProduct.setAdapter(getOrderProductDetailAdapter);
        fixListViewHeight(lvOrderProduct);//重新布局
        lvOrderProduct.setFocusable(false);

        tvPay.setText("￥"+moneyCount(getOrderProductList)+"");
        tvAllOrderPrice.setText("￥"+moneyCount(getOrderProductList)+"");



    }
    /*复制订单号*/
    public void CopyCodeClick(View view){
        /*剪切板复制使用*/
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        ClipData myClip;
        String text =tvOrderCode.getText().toString();
        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(getApplicationContext(),"已经复制",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.myActivityManager.finishActivity(this);//添加Activity
    }

    /*计算总金额*/
    private double moneyCount(List<GetOrderProduct> productList){
        double money=0.0;
        if(productList!=null&&productList.size()>0){
            for (GetOrderProduct item:
                 productList) {
                money+=item.getBuyNumber()*item.getPrice();//总jine

            }
        }else {
            money=0.0;
        }

        return money;
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
