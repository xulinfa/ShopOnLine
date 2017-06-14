package com.example.xulf.shoponline;

import android.content.Intent;
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

import com.example.xulf.shoponline.bean.RegisterReturn;
import com.example.xulf.shoponline.bean.TextJson;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private TextView tvCode;//城市代码
    private TextView tvPhoneNum;//电话号码
    private EditText edtPassword;//密码
    private EditText edtCheckPassword;//确认密码
    private ImageView imgLookPassword;
    private ImageView imgLookCheckPassword;//查看确认密码
    private static final int PASSWORD_MINGWEN = 0x90;//明文样式id
    private static final int PASSWORD_MIWEN = 0x81;//密文样式

    private static String  country="";//城市代码
    private static String phone="";//电话号码
    private static String password="";//密码
    private static  String checkPassword="";//确认密码

    private List<RegisterReturn> list;//待改测试

    private RegisterReturn registerReturnType;
    private App app=App.getInstance();

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();//网络请求

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        app.myActivityManager.addActivity(this);//添加Activity
        //短信验证参数
        Intent intent=getIntent();
        country=intent.getStringExtra("country");
        phone=intent.getStringExtra("phone");

        tvCode=(TextView)findViewById(R.id.tv_code);//城市代码
        tvPhoneNum=(TextView)findViewById(R.id.tv_phone);//电话号码
        edtPassword=(EditText)findViewById(R.id.edt_reg_password);//密码
        edtCheckPassword=(EditText)findViewById(R.id.edt_reg_check_password);//确认密码
        imgLookPassword=(ImageView)findViewById(R.id.img_reg_lookPassword);//密码查看
        imgLookCheckPassword=(ImageView)findViewById(R.id.img_reg_check_lookPassword);//查看确认密码



        tvCode.setText(country);
        tvPhoneNum.setText(phone);

        //查看密码
        imgLookPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==(MotionEvent.ACTION_DOWN)){
                    edtPassword.setInputType(PASSWORD_MINGWEN);
                    imgLookPassword.setImageResource(R.mipmap.switch_in_show);
                }else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    edtPassword.setInputType(PASSWORD_MIWEN);
                    imgLookPassword.setImageResource(R.mipmap.switch_in_hide);
                }
                return true;
            }
        });

        //查看确认密码
        imgLookCheckPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==(MotionEvent.ACTION_DOWN)){
                    edtCheckPassword.setInputType(PASSWORD_MINGWEN);
                    imgLookCheckPassword.setImageResource(R.mipmap.switch_in_show);
                }else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    edtCheckPassword.setInputType(PASSWORD_MIWEN);
                    imgLookCheckPassword.setImageResource(R.mipmap.switch_in_hide);
                }
                return true;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.myActivityManager.finishActivity(this);//添加Activity
    }

    /*
        * 注册
        * */
    public void regUserClick(View view){
        password=edtPassword.getText().toString().trim();
        checkPassword=edtCheckPassword.getText().toString().trim();
    /*    if(password.equals(checkPassword)){
            Map<String,Object> params = new HashMap<>(3);
            params.put("user_name","13232");
            params.put("password",password);
           params.put("action",register);
            okHttpHelper.post(Contants.REGISTER, params, new MyCallback<TextJson>(getApplicationContext()) {
                @Override
                public void doRequestBefore(Request request) {

                }

                @Override
                public void onError(Response response) {
                    Log.i("REGERROR","网络连接失败REG");
                    Toast.makeText(getApplicationContext(),"注册失败！",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(Response response, TextJson registerReturnTextJson) {
                    Log.i("2222222:",""+registerReturnTextJson.toString());
                    String h=registerReturnTextJson.getCode();
                    registerReturnType=registerReturnTextJson.getData();
                    if(h.equals("200")){
                        Toast.makeText(getApplicationContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"注册成功，写入失败",Toast.LENGTH_SHORT).show();
                    }
                }


            });*/
        if(password.equals(checkPassword)){
        Map<String,Object> params = new HashMap<>(3);
        params.put("userName",phone);
        params.put("password",password);
        okHttpHelper.post(Contants.REGISTER, params, new MyCallback<TextJson>(getApplicationContext()) {
            @Override
            public void doRequestBefore(Request request) {

            }

            @Override
            public void onError(Response response) {
                Log.i("REGERROR", "网络连接失败REG");
                Toast.makeText(getApplicationContext(), "注册失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Response response, TextJson textJson) {
                String h = textJson.getCode();
                registerReturnType = textJson.getData();
                if (h.equals("200")) {
                    Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "连接网络成功，写入失败，+返回码："+textJson.getCode(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }else{
            Toast.makeText(getApplicationContext(),"密码与确认密码不同",Toast.LENGTH_SHORT).show();
        }

    }
}
