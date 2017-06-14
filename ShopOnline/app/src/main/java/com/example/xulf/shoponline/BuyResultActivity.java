package com.example.xulf.shoponline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by XuLF on 2017/3/7.
 */
public class BuyResultActivity extends AppCompatActivity {
    private ImageView imgResult;//支付结果

    private Button btnGoHome;//继续购物

    private TextView tvorder;

    private App app=App.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_result);
        getSupportActionBar().hide();//隐藏标题
        app.myActivityManager.addActivity(this);//添加Activity
        imgResult=(ImageView)findViewById(R.id.img_result_ofpay);//支付结果图片
        btnGoHome=(Button)findViewById(R.id.btn_result_goHome);//继续购物
        tvorder=(TextView)findViewById(R.id.tv_goorder);
        tvorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BuyResultActivity.this,MyOrderActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.myActivityManager.finishActivity(this);
    }

    //继续购物放回主页
    public void shopingAgainClick(View view){
        Intent intent=new Intent(BuyResultActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
