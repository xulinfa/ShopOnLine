package com.example.xulf.shoponline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.xulf.shoponline.adapter.OrderAdapter;
import com.example.xulf.shoponline.adapter.OrderPageAdapter;
import com.example.xulf.shoponline.bean.OrderBean;
import com.example.xulf.shoponline.bean.OrderProduct;
import com.example.xulf.shoponline.bean.Product;
import com.example.xulf.shoponline.bean.ReturnJson;
import com.example.xulf.shoponline.bean.ReturnJsonList;
import com.example.xulf.shoponline.fragment.OrderListFragment;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XuLF on 2016/12/29.
 * 所有订单
 */
public class MyOrderActivity extends AppCompatActivity{


    private TabLayout tabLayout;//订单上方的侧滑栏

    private ViewPager viewPager;//Fragment

    //private OrderAdapter orderAdapter;

    private  List<OrderBean> orderBeanList;//订单列表集合


    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();//获取okhttp实例

    private OrderPageAdapter orderPageAdapter;//订单页适配器

    private  App app=App.getInstance();//App实例对象

    private String kk="5555";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myallorder);

        app.myActivityManager.addActivity(this);//添加Activity
        tabLayout=(TabLayout)findViewById(R.id.tb_head);//侧滑
        viewPager=(ViewPager)findViewById(R.id.vp_order);//Fragment

        getAllOrder();//得到网络数据并且显示

/*
        orderBeanList=new ArrayList<>();
        OrderBean orderBean=new OrderBean();
        orderBean.setOrderID("1");
        orderBean.setRecipients("lin");
        orderBean.setAddress("123");
        orderBean.setOrderState("1");
        orderBean.setOrderTime("2017-13-12");
        List<OrderProduct> orderProducts=new ArrayList<>();
        for (int i=0;i<2;i++){
            OrderProduct orderProduct=new OrderProduct();
            orderProduct.setBuy_number(1);
            orderProduct.setProductDetail("烟草");

            orderProduct.setPrice(22.0);
            orderProduct.setProductID(i+"");

            orderProduct.setProductName("中华烟");
            orderProduct.setProductImg("");
            orderProducts.add(orderProduct);

        }
        orderBean.setOrderProduct(orderProducts);


        OrderBean orderBean1=new OrderBean();
        orderBean1.setOrderID("2");
        orderBean1.setRecipients("lin");
        orderBean1.setAddress("123");
        orderBean1.setOrderState("1");
        orderBean1.setOrderTime("2017-13-12");
        List<OrderProduct> orderProducts1=new ArrayList<>();
        for (int i=0;i<2;i++){
            OrderProduct orderProduct=new OrderProduct();
            orderProduct.setBuy_number(1);
            orderProduct.setProductDetail("烟草");

            orderProduct.setPrice(22.0);
            orderProduct.setProductID(i+"");
            orderProduct.setProductName("中华烟");
            orderProduct.setProductImg("");
            orderProducts1.add(orderProduct);
        }
        orderBean1.setOrderProduct(orderProducts1);


        orderBeanList.add(orderBean);
        orderBeanList.add(orderBean1);
        Gson gson=new Gson();
        String hh=gson.toJson(orderBeanList);
        Log.i("hh",hh);*/


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.myActivityManager.finishActivity(this);//结束Frgment
    }

    /*获取全部订单的数据*/
    private void getAllOrder(){
        Map<String,Object> params = new HashMap<>(1);//访问参数
        params.put("userName",app.getUser().getUserName());//传递用户的登录名其实就是手机号码为请求参数
        /*网络post请求*/
        okHttpHelper.post(Contants.GETALLORDER, params, new MyCallback<ReturnJsonList<OrderBean>>(getApplicationContext()) {
            @Override
            public void doRequestBefore(Request request) {

            }
            @Override
            public void onError(Response response) {
                Log.i("internetOrderErr","Error");
            }

            @Override
            public void onSuccess(Response response, ReturnJsonList<OrderBean> orderBeanReturnJsonList) {
                String code=orderBeanReturnJsonList.getCode();//返回码
                String msg=orderBeanReturnJsonList.getMsg();//返回文字
                if(code.equals("200")){
                    //返回码200代表成功
                    Log.i("orderBeanList",orderBeanReturnJsonList.getData().toString());
                    orderBeanList=orderBeanReturnJsonList.getData();//获取得到数据
                    setOrderList(orderBeanList);//设置订单数据值
                    initTab();//初始化数据
                    kk="6666";

                }else{
                    Toast.makeText(getApplication(),"失败,code:"+code,Toast.LENGTH_SHORT).show();
                    Log.i("OrderGetErrcode","code:"+code+",msg:"+msg);//log输出错误码
                }

            }

        });
    }



    /*
    * 初始化顶部侧滑栏
    * */
    private void initTab(){
        /*集合Frafgment*/
        List<Fragment> list = new ArrayList<>();
        list.add(OrderListFragment.newInstance(0));//第一个（全部）
        list.add(OrderListFragment.newInstance(1));//第二个（正在交易）
        list.add(OrderListFragment.newInstance(2));//交易完成

        /*Fragment适配*/
        orderPageAdapter=new OrderPageAdapter(this.getSupportFragmentManager(), this, list);
        viewPager.setAdapter(orderPageAdapter);
        viewPager.setOffscreenPageLimit(3);//预先加载Fragment数量
        tabLayout.setupWithViewPager(viewPager);//将viewPage的引用加载到Tablayout中去
        tabLayout.setTabMode(TabLayout.MODE_FIXED);//使的tab占满屏的宽度
    }


    /*获取数据集合*/
    public List<OrderBean> getOrderList(){
        return orderBeanList;
    }

    /*订单数据集合设置*/
    public void  setOrderList( List<OrderBean> orderBeanList1){
        orderBeanList=orderBeanList1;
    }

    /*更新数据*/
    public void update(){
        Log.i("update","begin");
        //orderBeanList=null;
      //  setOrderList(orderBeanList);
        //initTab();//初始化数据
      //  getAllOrder();
        Intent intent=new Intent(MyOrderActivity.this,MyOrderActivity.class);
        startActivity(intent);
        Log.i("update","end");

    }






}
