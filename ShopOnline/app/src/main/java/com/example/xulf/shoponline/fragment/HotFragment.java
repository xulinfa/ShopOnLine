package com.example.xulf.shoponline.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.xulf.shoponline.DetailActivity;
import com.example.xulf.shoponline.MainActivity;
import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.SearchProResultActivity;
import com.example.xulf.shoponline.adapter.ProductAdapter;
import com.example.xulf.shoponline.adapter.ProductPageAdapter;
import com.example.xulf.shoponline.bean.Product;
import com.example.xulf.shoponline.bean.ReturnJsonList;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.List;

/*商品列表*/
public class HotFragment extends Fragment {
   // private TextView tvSearch;
    private EditText edtSearch;//搜索框内容

    private TextView tvSearch;//搜索文字按钮

    private OkHttpHelper okHttpHelper;//网络请求
    private static List<Product> data;//产品数据



    private TabLayout tabLayout;//默认、加个、综合
    private ViewPager viewPager;
    private ProductPageAdapter productPageAdapter;//商品适配器

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = (TabLayout) view.findViewById(R.id.tb_product_head);
        viewPager = (ViewPager) view.findViewById(R.id.vp_product);
        edtSearch=(EditText)view.findViewById(R.id.edt_search);//搜索框
        tvSearch=(TextView)view.findViewById(R.id.tv_search);//文字按钮
        /*搜索相关产品*/
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtSearch.getText()!=null&&edtSearch.getText().toString()!="") {
                    String searchname = edtSearch.getText().toString().trim();
                    Intent intent = new Intent(getActivity(), SearchProResultActivity.class);
                    intent.putExtra("searchname", searchname);
                    startActivity(intent);
                }
            }
        });
        okHttpHelper = OkHttpHelper.getInstance();
        loadAllHot();

    }

    @Override
    public void onResume() {
        super.onResume();
       /* loadAllHot();
        Toast.makeText(getContext(),"44",Toast.LENGTH_SHORT).show();*/
    }

    //初始化数据，请求网络
    private void loadAllHot() {
        okHttpHelper.get(Contants.PRODUCTLIST, new MyCallback<ReturnJsonList<Product>>(getActivity()) {


            @Override
            public void doRequestBefore(Request request) {

            }

            @Override
            public void onError(Response response) {
                Log.i("hoterror", "失败");
            }

            @Override
            public void onSuccess(Response response, ReturnJsonList<Product> productReturnJsonList) {
                Log.i("allproduct", "所有产品"+productReturnJsonList.getData().toString());
                data = productReturnJsonList.getData();
                initTab(data);
                Log.i("allproduct2", "所有产品解析完成");
            }

        });
    }
/*初始化tab*/
    private void initTab(List<Product> data) {
        Log.i("hotfragment", "initTab: ========0=====");
        List<Fragment> list = new ArrayList<>();
        list.add(ProductOrderByFragment.newInstance(0,data));
        list.add(ProductOrderByFragment.newInstance(1,data));
        list.add(ProductOrderByFragment.newInstance(2,data));
        productPageAdapter = new ProductPageAdapter(getActivity().getSupportFragmentManager(), this.getActivity(), list);
        Log.i("hotfragment", "initTab: =============");
        viewPager.setAdapter(productPageAdapter);
        viewPager.setOffscreenPageLimit(3);//3个fragment
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

/*

    //数据适配
    private void initDate(){
        if(list!=null){
            productAdapter =new ProductAdapter(list,this.getActivity());
           // recyclerView.setAdapter(productAdapter);
            listView.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
        }
    }
*/

   /* @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        product=list.get(i);
        Intent intentDetail=new Intent(getContext(), DetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("productDetail", product);
        intentDetail.putExtras(bundle);
        startActivity(intentDetail);

       // Toast.makeText(view.getContext(),"点击进入详情"+list.get(i),Toast.LENGTH_SHORT).show();
    }*/


    //====================侧滑栏改变事件==========

}
