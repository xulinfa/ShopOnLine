package com.example.xulf.shoponline.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.DetailActivity;
import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.adapter.CategoryAdapter;
import com.example.xulf.shoponline.adapter.CategoryNameAdapter;
import com.example.xulf.shoponline.adapter.CategoryProListAdapter;
import com.example.xulf.shoponline.adapter.HomeHotAdapter;
import com.example.xulf.shoponline.bean.Product;
import com.example.xulf.shoponline.bean.ReturnJsonList;
import com.example.xulf.shoponline.bean.Type;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.nineoldandroids.animation.ObjectAnimator;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*产品匪类二级联动
*/
public class CategoryFragment extends Fragment{
    private ListView listView;//左边的分类列表

    private GridView gridView;//对应左边类型相应的产品

    private List<Type> typeList;//类型列表

    private List<Product> productList;//对应的茶品列表

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();//网络请求

    private CategoryProListAdapter productAdapter;//GriView中的产品适配器

    private CategoryNameAdapter categoryNameAdapter;//类别适配器

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.from(getActivity()).inflate(R.layout.fragment_type_category,null);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=(ListView)view.findViewById(R.id.lv_category_left_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String category=typeList.get(i).getCategorys();
               // Toast.makeText(getActivity(),category,Toast.LENGTH_SHORT).show();
                getProductData(category);//获取对应的产品数据
            }
        });
        gridView=(GridView)view.findViewById(R.id.gv_category_category_productlist);
        /*对应产品点击事件，跳到详情页*/
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //传值跳转
                Product product=new Product();
                product=productList.get(i);
                Intent intentDetail=new Intent(getActivity(), DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("productDetail", product);
                intentDetail.putExtras(bundle);
                startActivity(intentDetail);
              //  getActivity().finish();

            }
        });
        getCategoryData();//初始化类型数据
        getProductData("美酒");//初始化首个产品列表

    }



    /*获取分类数据*/
    private void getCategoryData(){

        okHttpHelper.get(Contants.PRODUCTCATEGORY, new MyCallback<ReturnJsonList<Type>>(getActivity()) {
            @Override
            public void doRequestBefore(Request request) {

            }

            @Override
            public void onError(Response response) {

            }

            @Override
            public void onSuccess(Response response, ReturnJsonList<Type> typeReturnJsonList) {
                String code=typeReturnJsonList.getCode();
                String msg=typeReturnJsonList.getMsg();
                Log.i("wwwwww",typeReturnJsonList.getData().toString());
                if (code.equals("200")) {
                    typeList = typeReturnJsonList.getData();
                    initCategory();//初始化类型
                   // Toast.makeText(getActivity(),typeList.toString(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),code,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*初始化类型*/
    private void initCategory(){
       // getCategoryData();//获取网络数据
        if(typeList!=null) {
            categoryNameAdapter = new CategoryNameAdapter(typeList);
            listView.setAdapter(categoryNameAdapter);//设置适配器;
        }
    }

    /*获取相应产品的网络数据*/
    private void getProductData(String category){
        Map<String,Object> params = new HashMap<>(1);
        params.put("categorys",category);
        okHttpHelper.post(Contants.PRODUCTWITHTYPE, params, new MyCallback<ReturnJsonList<Product>>(this.getActivity()) {
            @Override
            public void doRequestBefore(Request request) {

            }

            @Override
            public void onError(Response response) {

            }

            @Override
            public void onSuccess(Response response, ReturnJsonList<Product> productReturnJsonList) {
                String code=productReturnJsonList.getCode();
                String msg=productReturnJsonList.getMsg();
                if(code.equals("200")){
                    productList=productReturnJsonList.getData();
                  //  Toast.makeText(getContext(),productList.toString(),Toast.LENGTH_SHORT).show();
                    initProduct();//适配产品
                }

            }
        });


    }
    /*初始化数据*/
    private void initProduct(){
       // getProductData();//获取相应的网络数据
        if(productList!=null) {
            productAdapter = new CategoryProListAdapter(productList);
            gridView.setAdapter(productAdapter);
        }
    }


}
