package com.example.xulf.shoponline;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.bean.ReturnJson;
import com.example.xulf.shoponline.service.CartService;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XuLF on 2017/2/18.
 */
public class SettingActivity extends AppCompatActivity {

    private EditText edtPhone,edtnickname;

    private TextView tvSxe;

    private Button btnLoginOut,btnSave;


    private String sexString="";

    private String userPhone;

   private  App app=App.getInstance();

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();//隐藏原有标题

        //对象获取
        edtnickname=(EditText)findViewById(R.id.edt_nickname);
        edtPhone=(EditText)findViewById(R.id.edt_phone);
        tvSxe=(TextView) findViewById(R.id.tv_sex);
       // btnLoginOut=(Button)findViewById(R.id.btn_loginout);
        btnSave=(Button)findViewById(R.id.btn_savedetail);
        edtPhone.setEnabled(false);

        /*点击进行修改选择性别*/
        tvSxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(SettingActivity.this);
                builder.setIcon(R.drawable.icon_sex);
                builder.setTitle("请选择性别");
                final  String[] sex={"男","女"};
                /**
                 * 第一个参数指定我们要显示的一组下拉单选框的数据集合
                 * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
                 * 第三个参数给每一个单选项绑定一个监听器
                 */
                builder.setSingleChoiceItems(sex, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("sexChoose",sex[i]);
                        sexString=sex[i];
                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i("sexChoose1",i+"");
                        tvSxe.setText(sexString);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

        /*初始化个人信息*/
        initUserMsg();
    }

    /*初始化个人信息*/
    private void initUserMsg(){

        if(app.getUser()!=null){
            //btnLoginOut.setVisibility(View.VISIBLE);
            edtPhone.setText(app.getUser().getUserName());//电话
            edtnickname.setText(app.getUser().getNickname());//昵称
            tvSxe.setText(app.getUser().getSex());//性别
        }else{
           // btnLoginOut.setVisibility(View.GONE);
        }
    }

    /*网络请求修改个人信息*/
    private void alertUserMsg(){

        Map<String,Object> params = new HashMap<>(3);
        params.put("userName",app.getUser().getUserName());
        params.put("nickname",edtnickname.getText().toString().trim());
        params.put("sex",tvSxe.getText().toString().trim());
        okHttpHelper.post(Contants.ALERTUSERMSG, params, new MyCallback<ReturnJson<String>>(getApplicationContext()) {
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
                if(code.equals("200")){
                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                    app.getUser().setNickname(edtnickname.getText().toString().trim());
                    app.getUser().setSex(tvSxe.getText().toString().trim());
                }

            }
        });

    }


    /*保存*/
    public void savaClick(View view){
        //网络请求，修改个人资料
        alertUserMsg();
    }






}
