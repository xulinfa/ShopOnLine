package com.example.xulf.shoponline.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.CreateOrderActivity;
import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.adapter.CartAdapter;
import com.example.xulf.shoponline.bean.Cart;
import com.example.xulf.shoponline.bean.OrderProduct;
import com.example.xulf.shoponline.service.CartService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment{

    private RecyclerView recyclerView;//列表控件
    private TextView tvTotalPrice;//总金额
    private CheckBox checkAll;//全选按钮
    private Button btnPay;//付款按钮
    private Button btnDel;//删除按钮
    private Button btnActionChange;//编辑完成
    private CartService cartService;//购物车
    private CartAdapter cartAdapter;//购物车适配器
    private View layoutnodata;//没有数据时显示

    private final static int ACTION_COMPLETE=1;//完成
    private final static int ACTION_EDIT=2;//编辑

    private List<OrderProduct> orderProductList=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cart, container, false);
        //对象获取
        recyclerView=(RecyclerView)view.findViewById(R.id.cart_showShop);
        checkAll=(CheckBox)view.findViewById(R.id.cart_checkAll);//全选
        btnPay=(Button) view.findViewById(R.id.btn_cartPay);//结算
        btnDel=(Button) view.findViewById(R.id.btn_cartDel);//删除
        tvTotalPrice=(TextView)view.findViewById(R.id.tv_cartPrice);//总价
        layoutnodata=(LinearLayout) view.findViewById(R.id.ll_nodata);
        btnActionChange=(Button)view.findViewById(R.id.btn_cart_action_change);

        /*编辑完成*/
        btnActionChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),"asa",Toast.LENGTH_SHORT).show();
                int tag= (int) view.getTag();
                if(tag==ACTION_COMPLETE){
                    edit();//编辑样式
                }else if (tag==ACTION_EDIT){
                    complete();//完成样式
                }
            }
        });

        /*创建订单*/
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orderProductList=cartAdapter.getCheckOrderProduct();//获取要提交的订单
                Intent intent=new Intent(getActivity(), CreateOrderActivity.class);
                intent.putExtra("orderProduct", (Serializable) orderProductList);
                startActivity(intent);
               // getActivity().finish();

            }
        });

        /*删除选中的*/
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  orderProductList=cartAdapter.getCheckOrderProduct();//获取要提交的订单
                cartAdapter.delCartProduct();
                Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
            }
        });
        edit();//开始时购物车的样式
        initCartData();//初始化购物车
        //条目点击事件
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //判断是否有数据
        //cartService=new CartService(getActivity());
        List<Cart> carts1=cartService.getAll();//获取所有数据
        if(carts1==null||carts1.size()==0){
            layoutnodata.setVisibility(View.VISIBLE);
        }else{
            layoutnodata.setVisibility(View.GONE);
        }
    }

    //初始化数据购物车数据
    private void initCartData(){
        cartService=new CartService(getActivity());
        List<Cart> carts=cartService.getAll();//获取所有数据
        if(carts==null||carts.size()==0){
            layoutnodata.setVisibility(View.VISIBLE);
        }else{
            layoutnodata.setVisibility(View.GONE);
        }
        cartAdapter=new CartAdapter(carts,getActivity(),tvTotalPrice,checkAll);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //数据刷新
    public void refData(){
        cartAdapter.clear();
        List<Cart> carts = cartService.getAll();//获取所有数据
        cartAdapter.addData(carts);
        cartAdapter.getTotalPrice();

        /*判断是否显示没有数据的图标*/
        if(carts==null||carts.size()==0){
            layoutnodata.setVisibility(View.VISIBLE);
        }else{
            layoutnodata.setVisibility(View.GONE);
        }
    }

    /*完成样式*/
   private void complete(){
       btnActionChange.setTag(ACTION_COMPLETE);
       btnActionChange.setText("完成");
       btnPay.setVisibility(View.GONE);
       tvTotalPrice.setVisibility(View.GONE);
       checkAll.setChecked(true);
       btnDel.setVisibility(View.VISIBLE);
   }

    /*编辑样式*/
    private void edit(){
        btnActionChange.setTag(ACTION_EDIT);
        btnActionChange.setText("编辑");
        btnPay.setVisibility(View.VISIBLE);
        tvTotalPrice.setVisibility(View.VISIBLE);
        checkAll.setChecked(true);
        btnDel.setVisibility(View.GONE);
    }



}
