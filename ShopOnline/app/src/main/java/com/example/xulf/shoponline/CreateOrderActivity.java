package com.example.xulf.shoponline;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.adapter.CreateOrderAdapter;
import com.example.xulf.shoponline.bean.Adress;
import com.example.xulf.shoponline.bean.CreateOrderShort;
import com.example.xulf.shoponline.bean.CreateOrderShortNum;
import com.example.xulf.shoponline.bean.OrderProduct;
import com.example.xulf.shoponline.bean.ReturnJson;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XuLF on 2016/12/28.
 */
public class CreateOrderActivity extends AppCompatActivity {
    private RelativeLayout rlAdress;//地址栏
    private TextView tvUserName;//收件人名称
    private TextView tvAdress;//住址
   // private RecyclerView rcvProductItem;//订单项

    private ListView rcvProductItem;//订单项
    private TextView tvNum;//记录商品件数
    private TextView tvMoney;//记录此次商品的总加个
    private Button btnSubmit;//提交订单

    private   Adress adress=new Adress();//地址

    private List<OrderProduct> productList=null;

    private CreateOrderAdapter createOrderAdapter;//确认订单列表适配器

    private double price;//总金额

    private int count;//总件数

    private RelativeLayout rl_apliy,rl_weixin,rl_null;//支付方式

    private RadioButton rb_apliy,rb_weixin,rb_null;

    private  App app=App.getInstance();

  private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();//网络请求
    private String jsonString;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        app.myActivityManager.addActivity(this);//添加Activity

        rlAdress=(RelativeLayout)findViewById(R.id.rl_adress);//地址总栏
        tvUserName=(TextView)findViewById(R.id.tv_nameandnum);//收件人
        tvAdress=(TextView)findViewById(R.id.tv_adress);//住址
        rcvProductItem=(ListView) findViewById(R.id.rlv_orderItem);//订单商品项
        tvNum=(TextView)findViewById(R.id.tv_totalNum);//jianshu
        tvMoney=(TextView)findViewById(R.id.tv_allMoney);//总金额
        rl_apliy=(RelativeLayout)findViewById(R.id.rl_apliy);//支付宝
        rl_weixin=(RelativeLayout)findViewById(R.id.rl_weixin);//微信
        rl_null=(RelativeLayout)findViewById(R.id.rl_null) ;//活到付款
        rb_apliy=(RadioButton)findViewById(R.id.rb_apliy);
        rb_weixin=(RadioButton)findViewById(R.id.rb_weixin);
        rb_null=(RadioButton)findViewById(R.id.rb_null);

        rl_apliy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_apliy.setChecked(true);
                rb_weixin.setChecked(false);
                rb_null.setChecked(false);
            }
        });

        rl_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_apliy.setChecked(false);
                rb_weixin.setChecked(true);
                rb_null.setChecked(false);
            }
        });

        rl_null.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_apliy.setChecked(false);
                rb_weixin.setChecked(false);
                rb_null.setChecked(true);
            }
        });


        btnSubmit=(Button)findViewById(R.id.btn_handOrder);//提交订单按钮
        /*确认订单*/
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adress.getAddressID()!=null){
                //提交订单按钮触发监听事件
                List<CreateOrderShortNum> createOrderShortNumList=new ArrayList<CreateOrderShortNum>();
                final CreateOrderShort createOrderShort=new CreateOrderShort();
                createOrderShort.setAddressID(Integer.parseInt(adress.getAddressID()));//地址
                createOrderShort.setUserName(app.getUser().getUserName());//姓名
                for (OrderProduct item:
                        productList ) {
                    CreateOrderShortNum createOrderShortNum=new CreateOrderShortNum();//ID  And  BuyNum
                    createOrderShortNum.setBuyNumber(item.getBuy_number());
                    createOrderShortNum.setProductID(item.getProductID()+"");
                    createOrderShortNumList.add(createOrderShortNum);
                }
                createOrderShort.setData(createOrderShortNumList);
                Gson gson=new Gson();
               jsonString= gson.toJson(createOrderShort);
                Map<String,Object> params = new HashMap<>(1);
                params.put("jsonString",jsonString);
                okHttpHelper.post(Contants.CREATEORDER, params, new MyCallback<ReturnJson<CreateOrderShort>>(getApplicationContext()) {
                    @Override
                    public void doRequestBefore(Request request) {


                    }
                    @Override
                    public void onError(Response response) {
                        Log.i("createorder1","success"+response.toString());
                    }

                    @Override
                    public void onSuccess(Response response, ReturnJson<CreateOrderShort> createOrderShortReturnJson) {
                        String code=createOrderShortReturnJson.getCode();
                        String msg=createOrderShortReturnJson.getMsg();
                        String kk=createOrderShortReturnJson.getData().toString();
                        Log.i("gg",kk);
                        if(code.equals("200")){
                            Log.i("createorder","success");
                            CreateOrderShort createOrderShort1=createOrderShortReturnJson.getData();
                            Log.i("sss",createOrderShort1.toString());
                        }else {
                            Log.i("createordererr","code:"+code+",msg:"+msg);
                        }
                    }



                });

                Log.i("333",jsonString);
                Intent intent=new Intent(CreateOrderActivity.this,BuyResultActivity.class);
                startActivity(intent);
                finish();

            }else{
                    Toast.makeText(getApplicationContext(),"请选择收获地址",Toast.LENGTH_SHORT).show();
                }
        }
        });


        /*获取欲购买数据*/
        final Intent intent=this.getIntent();
        productList= (List<OrderProduct>) intent.getSerializableExtra("orderProduct");
        createOrderAdapter=new CreateOrderAdapter(productList);
        rcvProductItem.setAdapter(createOrderAdapter);
        fixListViewHeight(rcvProductItem);//重新计算ListView
        rcvProductItem.setFocusable(false);//解决其实位置不是顶端问题

        countMoneyAndCount();//计算金额、数量
        tvNum.setText(count+"");
        tvMoney.setText("￥"+price+"");

        //选择收获地址管理
        rlAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getApplicationContext(),AdressManageActivity.class);
                intent1.putExtra("choose","chooseadress");
                startActivityForResult(intent1,0);

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.myActivityManager.finishActivity(this);//添加Activity
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateOrderActivity.this);
            //    设置Title的图标
            builder.setIcon(R.mipmap.icon_fangqi);
            //    设置Title的内容
            builder.setTitle("放弃交易");
            //    设置Content来显示一个信息
            builder.setMessage("确定放弃吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   Intent intent=new Intent(CreateOrderActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
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
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //  super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                /*地址修改*/
                if(data!=null) {
                    adress = (Adress) data.getSerializableExtra("adressResult");
                    tvUserName.setText(adress.getRecipients() + "(" + adress.getUserName() + ")");
                    tvAdress.setText(adress.getAddress());//地址
                }
              //  Toast.makeText(getApplicationContext(),"哈哈",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");


    public static String post(String url, String json) throws IOException {

       OkHttpClient client=new OkHttpClient();
               RequestBody body = RequestBody.create(JSON, json);
       // RequestBody requestBody=Re
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Log.i("request55",request.toString()+body.toString());
        Log.i("response55",response.toString());
        if (response.isSuccessful()) {
            Log.i("12355",response.body().string()+"");
            return response.body().string();

        } else {
            Log.i("1233355","555");
            throw new IOException("Unexpected code " + response);
        }
    }

    /*计算金额*/
    public void countMoneyAndCount(){
        if(productList.size()!=0&&productList!=null) {
            for (OrderProduct item :
                    productList) {
                price += item.getPrice();
                count += item.getBuy_number();

            }
        }else{
            price=0.0;
            count=0;
        }
    }

}
