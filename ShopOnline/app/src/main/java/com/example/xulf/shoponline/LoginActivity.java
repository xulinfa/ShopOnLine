package com.example.xulf.shoponline;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.xulf.shoponline.bean.Banner;
        import com.example.xulf.shoponline.bean.Cart;
        import com.example.xulf.shoponline.bean.Product;
        import com.example.xulf.shoponline.bean.RegisterReturn;
        import com.example.xulf.shoponline.bean.ReturnJson;
        import com.example.xulf.shoponline.bean.ReturnJsonList;
        import com.example.xulf.shoponline.bean.TextJson;
        import com.example.xulf.shoponline.bean.User;
        import com.example.xulf.shoponline.fragment.MineFragment;
        import com.example.xulf.shoponline.service.CartService;
        import com.example.xulf.shoponline.tool.Contants;
        import com.example.xulf.shoponline.tool.DES;
        import com.example.xulf.shoponline.tool.MyCallback;
        import com.example.xulf.shoponline.tool.OkHttpHelper;
        import com.squareup.okhttp.Request;
        import com.squareup.okhttp.Response;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import cn.smssdk.EventHandler;
        import cn.smssdk.SMSSDK;
        import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserName;
    private EditText edtPassword;
    private Button btnLogin;
    private ImageView imglook;
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    private String encryptionString;//明文
    private String decryptionString;//miwen
    private String enKey="12345678";//密钥

   private Button btnLoginOut;//tuichu

    private   App app=App.getInstance();//app

    private static final int PASSWORD_MINGWEN = 0x90;//明文样式id
    private static final int PASSWORD_MIWEN = 0x81;//密文样式

    /*写入购物车*/
    private CartService cartService;
    private List<Product> productList;

    private List<TextJson> list;

    private static int flag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        app.myActivityManager.addActivity(this);//添加Activity
        getSupportActionBar().hide();
        SMSSDK.initSDK(this, "1a85dc9952a60", "5d1f6904f159b4e113c866ed5e0031c4");

        imglook=(ImageView)findViewById(R.id.img_lookpassword);
        edtUserName=(EditText)findViewById(R.id.edt_loginname);
        edtPassword=(EditText)findViewById(R.id.edt_password);
        btnLogin=(Button)findViewById(R.id.btn_login);
        btnLoginOut=(Button)findViewById(R.id.btn_loginout);//退出登陆

        cartService=new CartService(this.getApplicationContext());

        /*按钮样式设计*/

        if(app.getUser()!=null){
            btnLoginOut.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
        }else{
            btnLoginOut.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
        }


        imglook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1){
                    edtPassword.setInputType(PASSWORD_MIWEN);
                    imglook.setImageResource(R.mipmap.switch_in_hide);
                    flag=2;
                }else {
                    edtPassword.setInputType(PASSWORD_MINGWEN);
                    imglook.setImageResource(R.mipmap.switch_in_show);
                    flag=1;
                }

            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name=edtUserName.getText().toString().trim();
                final String password=edtPassword.getText().toString().trim();
                if(name==null||name==""){
                    Toast.makeText(getApplicationContext(),"用户名不可以为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password==null||password==""){
                    Toast.makeText(getApplicationContext(),"请输入密码！",Toast.LENGTH_SHORT).show();
                }
                //**************************************加密解密*********************************

            /*    *//*加密*//*
                try {
                    decryptionString= DES.encryptDES(name,enKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                *//*解密*//*
                try {
                    encryptionString=DES.decryptDES(decryptionString,enKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                //*************************************加密解密结束********************************************
                Map<String,Object> params = new HashMap<>(2);
                params.put("userName",name);
                params.put("password",password);

                //网络请求  待修改部分
               okHttpHelper.post(Contants.LOGIN, params, new MyCallback<ReturnJson<User>>(getApplicationContext()) {
                    @Override
                    public void doRequestBefore(Request request) {

                    }

                    @Override
                    public void onError(Response response) {
                        Toast.makeText(getApplicationContext(),"网络请求失败！",Toast.LENGTH_SHORT).show();
                    }

                   @Override
                   public void onSuccess(Response response, ReturnJson<User> userReturnJson) {
                        User user=new User();
                       String code=userReturnJson.getCode();
                       if(code.equals("200")){
                           Toast.makeText(getApplicationContext(),"登陆成功！！",Toast.LENGTH_SHORT).show();

                           if (userReturnJson.getData()!=null){
                               user=userReturnJson.getData();
                           }else{
                               user.setUserName(name);
                               user.setPassword(password);
                           }
                           //写入缓存sp
                           App app=App.getInstance();
                           app.putUser(user);

                           /*购物车初始化*/
                           initShopCart(name);

                           //跳转到mine
                           Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                          // intent.putExtra("loginsucee",1);
                           startActivity(intent);
                           finish();

                       }else if(code.equals("201")){
                           Toast.makeText(getApplicationContext(),"密码错误！",Toast.LENGTH_SHORT).show();
                       }else if(code.equals("202")){
                           Toast.makeText(getApplicationContext(),"无此用户！",Toast.LENGTH_SHORT).show();
                       }else {
                           Toast.makeText(getApplicationContext(),"异常码："+code,Toast.LENGTH_SHORT).show();
                       }

                   }



                });
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.myActivityManager.finishActivity(this);//添加Activity
    }

    /*退出登陆*/
    public void loginoutClick(View view){

        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        //    设置Title的图标
        builder.setIcon(R.mipmap.icon_outlogin);
        //    设置Title的内容
        builder.setTitle("退出登陆");
        //    设置Content来显示一个信息
        builder.setMessage("确定退出吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                app.clearUser();
                CartService cartService=new CartService(getApplicationContext());
                cartService.clearAll();//清除
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
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

    /*忘记密码*/
    public  void forgetPwdClick(View view){

       /* App app=App.getInstance();
        if(app.getUser()!=null) {*/
            //忘记密码
            RegisterPage registerPage = new RegisterPage();
            registerPage.setRegisterCallback(new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    // 解析注册结果
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        @SuppressWarnings("unchecked")
                        HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                       // String country = (String) phoneMap.get("country");
                        String phone = (String) phoneMap.get("phone");

                        // 提交用户信息
                        // registerUser(country, phone);
                        Intent intent=new Intent();
                        intent.putExtra("phone", phone);
                        intent.setClass(LoginActivity.this,UpdatePwdActivity.class);
                        startActivity(intent);
                    }
                }
            });
            registerPage.show(this);
 /*       }*/

    }

    //新用户注册
    public void regClick(View view){

        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    // 提交用户信息
                   // registerUser(country, phone);
                    Intent intent1=new Intent();
                    intent1.putExtra("country",country);
                    intent1.putExtra("phone",phone);
                    intent1.setClass(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent1);
                }
            }
        });
        registerPage.show(this);
            }

    /*登陆的时候将狗去车内容写到本地*/
    private  void initShopCart(String userPhone){
        Map<String,Object> params = new HashMap<>(2);
        params.put("userName",userPhone);
        okHttpHelper.post(Contants.GETCARTLIST, params, new MyCallback<ReturnJsonList<Product>>(getApplicationContext()) {
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
                    if(productReturnJsonList.getData()!=null){
                        productList=productReturnJsonList.getData();
                        Log.i("productidtex22t","12");
                        Log.i("productidtex22ssst",productList.size()+"");
                        for (Product item:
                                productList) {

                            Log.i("productidtext",item.getProductID()+"");
                            cartService.putIntoCart(item);//放入本地
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"code:"+code+",msg:"+msg,Toast.LENGTH_SHORT).show();
                }
            }


    });

    }

}
