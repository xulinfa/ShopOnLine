package com.example.xulf.shoponline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.bean.ReturnJson;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XuLF on 2017/2/17.
 * 修改密码
 */
public class UpdatePwdActivity extends AppCompatActivity {
    private TextView tvPhone;//用户名

    private EditText edNewPwd,edCheckNewPwd;//新密码

    private ImageView imgLookNewPwd,imgLookNewPwdAgain;//查看密码

    private Button btnUpdatePwd;//修改密码按钮

    private static final int PASSWORD_MINGWEN = 0x90;//明文样式id
    private static final int PASSWORD_MIWEN = 0x81;//密文样式

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();//网络请求

    private App app=App.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);
        app.myActivityManager.addActivity(this);//添加Activity
        getSupportActionBar().hide();//隐藏原有标题

        //对象获取
        tvPhone=(TextView)findViewById(R.id.tv_newpwd_phone);
        edNewPwd=(EditText)findViewById(R.id.edt_new_password);
        edCheckNewPwd=(EditText)findViewById(R.id.edt_new_check_password);
        imgLookNewPwd=(ImageView)findViewById(R.id.img_new_lookPassword);
        imgLookNewPwdAgain=(ImageView)findViewById(R.id.img_new_check_lookPassword);
        btnUpdatePwd=(Button)findViewById(R.id.btn_changge_pwd);


        Intent intent=getIntent();
        String  userphone=intent.getStringExtra("phone");
        tvPhone.setText(userphone);

        //查看密码
        imgLookNewPwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==(MotionEvent.ACTION_DOWN)){
                    edNewPwd.setInputType(PASSWORD_MINGWEN);
                    imgLookNewPwd.setImageResource(R.mipmap.switch_in_show);
                }else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    edNewPwd.setInputType(PASSWORD_MIWEN);
                    imgLookNewPwd.setImageResource(R.mipmap.switch_in_hide);
                }
                return true;
            }
        });

        //查看确认密码
        imgLookNewPwdAgain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==(MotionEvent.ACTION_DOWN)){
                    edCheckNewPwd.setInputType(PASSWORD_MINGWEN);//密码明文
                    imgLookNewPwdAgain.setImageResource(R.mipmap.switch_in_show);
                }else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    edCheckNewPwd.setInputType(PASSWORD_MIWEN);//密码密文状态
                    imgLookNewPwdAgain.setImageResource(R.mipmap.switch_in_hide);
                }
                return true;
            }
        });


    }

    /*修改密码*/
    public void changePwdClick(View view){
        String phone=tvPhone.getText().toString().trim();
        String password=edNewPwd.getText().toString().trim();
        String passwordCheck=edCheckNewPwd.getText().toString().trim();

        if (password.equals(passwordCheck)){
            Map<String,Object> params = new HashMap<>(3);
            params.put("userName",phone);
            params.put("newPassword",password);

            okHttpHelper.post(Contants.FORGETPWD, params, new MyCallback<ReturnJson<String>>(getApplicationContext()) {
                @Override
                public void doRequestBefore(Request request) {

                }

                @Override
                public void onError(Response response) {

                }

                @Override
                public void onSuccess(Response response, ReturnJson<String> stringReturnJson) {
                    String code=stringReturnJson.getCode();
                    String msg=stringReturnJson.getMsg();
                    String data=stringReturnJson.getData();
                    if(code.equals("200")){
                        Toast.makeText(getApplicationContext(),"修改密码成功！",Toast.LENGTH_SHORT).show();
                        //清除缓存数据
                        App app=App.getInstance();
                        app.clearUser();
                        //跳转到登陆页面
                        Intent intent=new Intent(UpdatePwdActivity.this,LoginActivity.class);
                        startActivity(intent);

                    }else if(code.equals("201")){
                        Toast.makeText(getApplicationContext(),"code:201"+msg,Toast.LENGTH_SHORT).show();
                    }else if(code.equals("202")){
                        Toast.makeText(getApplicationContext(),"code:202"+msg,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"code:"+code,Toast.LENGTH_SHORT).show();
                    }

                }


            });

        }else{
            Toast.makeText(getApplicationContext(),"密码与确认密码不同",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.myActivityManager.finishActivity(this);//添加Activity
    }
}
