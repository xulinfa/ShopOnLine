package com.example.xulf.shoponline.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xulf.shoponline.MyOrderActivity;
import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.adapter.OrderAdapter;
import com.example.xulf.shoponline.bean.OrderBean;
import com.example.xulf.shoponline.bean.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XuLF on 2017/3/5.
 * 订单列表
 */
public class OrderListFragment extends Fragment{

    private static final String ARG_PAGE="aaaa";

    private int pageIndex;
    private ListView lvOrder;

    private OrderAdapter orderAdapter;

    private static List<OrderBean> orderBeanList;

    private List<OrderBean> OrderBeanListAll=new ArrayList<>();//全部

    private List<OrderBean> OrderBeanListComplete=new ArrayList<>();//已经完成的订单

    private List<OrderBean> OrderBeanListUnComplete=new ArrayList<>();//未完成的订单

    private Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }



    public static OrderListFragment newInstance(int pager) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pager);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageIndex = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_order_list,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvOrder=(ListView)view.findViewById(R.id.lv_order);
        orderBeanList=((MyOrderActivity)activity).getOrderList();//得到数据
        for (OrderBean item:orderBeanList){
            OrderBeanListAll.add(item);
            if(item.getOrderState().equals("1")){
                OrderBeanListUnComplete.add(item);

            }else if(item.getOrderState().equals("2")){
                OrderBeanListComplete.add(item);
            }
            Log.i("ompletesize",OrderBeanListUnComplete.size()+"");
        }
        switch (pageIndex){
            case 0:
                Log.i("1555",pageIndex+"");
                orderBeanList=OrderBeanListAll;
                initDate(orderBeanList);
                break;
            case 1:
                Log.i("1444",pageIndex+"");
                Log.i("ompletesize1",OrderBeanListUnComplete.size()+"");
                orderBeanList=OrderBeanListUnComplete;
                initDate(orderBeanList);
                break;
            case 2:
                Log.i("1333",pageIndex+"");
                orderBeanList=OrderBeanListComplete;
                initDate(orderBeanList);
                break;
            default:
                break;
        }



    }

    public void updateDate(){
        ((MyOrderActivity)activity).update();
    }



    //数据适配
    private void initDate(List<OrderBean> list) {
        orderAdapter=new OrderAdapter(orderBeanList,(MyOrderActivity) getActivity());
        lvOrder.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();
    }


}
