package com.example.xulf.shoponline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.xulf.shoponline.adapter.AdressAdapter;
import com.example.xulf.shoponline.adapter.CartAdapter;
import com.example.xulf.shoponline.adapter.CreateOrderAdapter;
import com.example.xulf.shoponline.bean.Adress;
import com.example.xulf.shoponline.bean.ReturnJsonList;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by XuLF on 2017/1/8.
 */
public class AdressManageActivity extends AppCompatActivity{
  // private ListView lvAdress;
    private RecyclerView rvAdress;//地址列表转载
    private String flag="";//标记是否是下订单时选择地址，进入该页面

    private  List<Adress> adressList=null;//地址数据

    private AdressAdapter adressAdapter;//地址适配器

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();//网络请求

   private  App app=App.getInstance();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myadress);
       // boolean jj=app.myActivityManager.currentActivity() instanceof  AddAdressActivity;
      //  Log.i("eeeee",app.myActivityManager.currentActivity().getPackageName()+"||"+app.myActivityManager.currentActivity().toString()+jj);
        /*上下文入栈管理*/
        if(app.myActivityManager.currentActivity() instanceof  AddAdressActivity){
            app.myActivityManager.finishActivity(this);//添加Activity
        }else{
            app.myActivityManager.addActivity(this);//添加Activity
        }
        rvAdress=(RecyclerView) findViewById(R.id.rv_adress);//地址列表

        /*选择地址标识*/
        if(this.getIntent().getStringExtra("choose")!=null){
            flag=this.getIntent().getStringExtra("choose");
        }


        if(app.getUser()==null){

        }else{
            initAdressList();


        }
/*
        rvAdress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(flag.equals("chooseadress")) {
                    // startActivityForResult();
                    Intent intent = new Intent();
                    Adress adress=new Adress();
                    adress=adressList.get(i);
                    //对象传xa
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("adressResult", adress);
                    intent.putExtras(bundle);
                    setResult(0,intent);
                    finish();
                }
            }
        });*/


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.myActivityManager.finishActivity(this);//添加Activity
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAdressList();
    }

    private void initAdressList(){
        /*传入参数*/

        Map<String,Object> params = new HashMap<>(1);
        params.put("userName",app.getUser().getUserName());
        okHttpHelper.post(Contants.GETALLADRESS, params, new MyCallback<ReturnJsonList<Adress>>(getApplicationContext()) {
            @Override
            public void doRequestBefore(Request request) {

            }

            @Override
            public void onError(Response response) {

            }

            @Override
            public void onSuccess(Response response, ReturnJsonList<Adress> adressReturnJsonList) {
                String code=adressReturnJsonList.getCode();
                String msg=adressReturnJsonList.getMsg();
                if(code.equals("200")){
                    if(adressReturnJsonList.getData()!=null) {
                        adressList = adressReturnJsonList.getData();//地址列表
                        adressAdapter=new AdressAdapter(adressList,getApplicationContext());//地址适配
                        rvAdress.setAdapter(adressAdapter);
                        rvAdress.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        /*地址项点击*/
                        adressAdapter.setOnItemClickListener(new AdressAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                if(flag.equals("chooseadress")) {
                                    // startActivityForResult();
                                    Intent intent = new Intent();
                                    Adress adress=new Adress();
                                    adress=adressList.get(position);
                                    //对象传回下订单页面
                                    Bundle bundle=new Bundle();
                                    bundle.putSerializable("adressResult", adress);
                                    intent.putExtras(bundle);
                                    setResult(0,intent);
                                    finish();
                                }
                            }
                        });
                    }


                }else{
                    Toast.makeText(getApplicationContext(),"code:"+code+",msg:"+msg,Toast.LENGTH_SHORT).show();
                }

            }


        });


    }

    /*添加新地址*/
    public  void  addNewAdressClick(View view){

       Intent intent=new Intent(this,AddAdressActivity.class);
        startActivity(intent);
       // finish();

    }

}
