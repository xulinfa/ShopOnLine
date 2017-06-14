package com.example.xulf.shoponline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.xulf.shoponline.adapter.ProductAdapter;
import com.example.xulf.shoponline.bean.Product;
import com.example.xulf.shoponline.bean.ReturnJson;
import com.example.xulf.shoponline.bean.ReturnJsonList;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XuLF on 2017/3/19.
 */
public class SearchProResultActivity extends AppCompatActivity {
    private ListView lvSearch;//搜索内容列表容器

    private ImageView imageView;///无数据时图片

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();//网络请求

    private List<Product> productList;//搜索产品列表

    private Product product;//商品项

    private String searchName;//搜索产品名称

    private ProductAdapter productAdapter;//适配器

    private App app=App.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpro_result);
        getSupportActionBar().hide();

        app.myActivityManager.addActivity(this);

        lvSearch=(ListView)findViewById(R.id.iv_search);
        imageView=(ImageView)findViewById(R.id.img_searchnodata);

        initData();//适配数据

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                product=productList.get(i);//点中的商品
                Intent intentDetail=new Intent(getApplicationContext(), DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("productDetail", product);
                intentDetail.putExtras(bundle);
                startActivity(intentDetail);
                finish();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.myActivityManager.finishActivity(this);
    }

    /*获取网络数据*/
    private void initData(){
        Intent intent=this.getIntent();
        searchName=intent.getStringExtra("searchname");
        Log.i("sss",searchName);
        Map<String,Object> params = new HashMap<>(1);
        params.put("likeName",searchName);
        okHttpHelper.post(Contants.SEARCHPROMIHU, params, new MyCallback<ReturnJsonList<Product>>(getApplicationContext()) {
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
                    if(productList.size()>0){
                        productAdapter = new ProductAdapter(productList, getApplicationContext());
                        lvSearch.setAdapter(productAdapter);
                        imageView.setVisibility(View.GONE);
                    }else{
                        imageView.setVisibility(View.VISIBLE);
                    }

                }else{
                    imageView.setVisibility(View.VISIBLE);
                    Log.i("seracherr","code:"+code+",msg:"+msg);
                }

            }


        });
    }
}
