package com.example.xulf.shoponline;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.adapter.CategoryNameAdapter;
import com.example.xulf.shoponline.adapter.CategoryProListAdapter;
import com.example.xulf.shoponline.adapter.HomeHotAdapter;
import com.example.xulf.shoponline.bean.Product;
import com.example.xulf.shoponline.bean.ReturnJsonList;
import com.example.xulf.shoponline.bean.Type;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XuLF on 2017/3/11.
 * 首页分类进入
 */
public class CategoryByTypeHide extends AppCompatActivity {

    private ListView listView;//左边的分类列表

    private GridView gridView;//对应左边类型相应的产品

    private List<Type> typeList;//类型列表

    private List<Product> productList;//对应的茶品列表

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();//网络请求

    private CategoryProListAdapter productAdapter;//GriView中的产品适配器

    private CategoryNameAdapter categoryNameAdapter;//类别适配器

    private TextView tvTitle;//标题

    private  String category="";//搜索名称
    private App app=App.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_type_category);
        app.myActivityManager.addActivity(this);//添加Activity
        getSupportActionBar().hide();

        Intent intent=this.getIntent();
        category=intent.getStringExtra("intentCategory");

        tvTitle=(TextView)findViewById(R.id.tv_product_category_title);
        listView=(ListView)findViewById(R.id.lv_category_left_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String category=typeList.get(i).getCategorys();
                // Toast.makeText(getActivity(),category,Toast.LENGTH_SHORT).show();
                tvTitle.setText(category);
                getProductData(category);//获取对应的产品数据
            }
        });
        gridView=(GridView)findViewById(R.id.gv_category_category_productlist);
        /*对应产品点击事件，跳到详情页*/
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //传值跳转
                Product product=new Product();
                product=productList.get(i);
                Intent intentDetail=new Intent(CategoryByTypeHide.this, DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("productDetail", product);
                intentDetail.putExtras(bundle);
                startActivity(intentDetail);
                //  getActivity().finish();

            }
        });
        getCategoryData();//初始化类型数据
        getProductData(category);
        tvTitle.setText(category);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.myActivityManager.finishActivity(this);
    }

    /*获取分类数据*/
    private void getCategoryData(){

        okHttpHelper.get(Contants.PRODUCTCATEGORY, new MyCallback<ReturnJsonList<Type>>(getApplicationContext()) {
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
                }else{
                    Toast.makeText(getApplicationContext(),code,Toast.LENGTH_SHORT).show();
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
        okHttpHelper.post(Contants.PRODUCTWITHTYPE, params, new MyCallback<ReturnJsonList<Product>>(getApplicationContext()) {
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
                    initProduct();//适配产品
                }else{
                    Toast.makeText(getApplicationContext(),"code:"+code+",msg:"+msg,Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    /*初始化数据*/
    private void initProduct(){
        if(productList!=null) {
            productAdapter = new CategoryProListAdapter(productList);
            gridView.setAdapter(productAdapter);
        }
}
}
