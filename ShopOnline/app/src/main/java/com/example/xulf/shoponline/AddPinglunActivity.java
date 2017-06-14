package com.example.xulf.shoponline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.bean.ReturnJson;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XuLF on 2017/3/2.
 * 添加评论
 */
public class AddPinglunActivity extends AppCompatActivity{

    private RatingBar ratingBarFuwu,ratingBarMiaoshu,ratingBarTime;//服务态度，描述，送货时间

    private EditText edtPinglunContent;//评价内容

    private  String  strPinglun="";//最终评论

    private static int productid;

    private App app=App.getInstance();

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinglun);
        app.myActivityManager.addActivity(this);//添加Activity
        getSupportActionBar().hide();//隐藏标题

        Intent intent=this.getIntent();
        productid=Integer.parseInt(intent.getStringExtra("proID"));


        ratingBarFuwu=(RatingBar)findViewById(R.id.rtb_fuwu);
        ratingBarMiaoshu=(RatingBar)findViewById(R.id.rtb_miaoshu);
        ratingBarTime=(RatingBar)findViewById(R.id.rtb_time);
        edtPinglunContent=(EditText)findViewById(R.id.edt_pinglun);

        /*点击事件星星*/
        ratingBarFuwu.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                strPinglun+="服务态度："+v+"星"+"  ";
            }
        });
        ratingBarMiaoshu.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                strPinglun+="描述相符："+v+"星"+"  ";
            }
        });
        ratingBarTime.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                strPinglun+="送达时间："+v+"星"+"  ";
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.myActivityManager.finishActivity(this);
    }

    /*提交评价*/
    public void pinglunClick(View view){
        strPinglun+="\n"+edtPinglunContent.getText().toString().trim();
        Map<String,Object> params = new HashMap<>(3);
        params.put("userName",app.getUser().getUserName());
        params.put("commentContent",strPinglun);
        params.put("productID",productid);
        okHttpHelper.post(Contants.ADDPINGLUN, params, new MyCallback<ReturnJson<String>>(getApplicationContext()) {
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
                    Log.i("addpinglunsuccess","success");
                    Intent intent=new Intent(getApplicationContext(),MyOrderActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Log.i("addpinglunerr","code:"+code+",msg:"+msg);
                    Toast.makeText(getApplicationContext(),"失败，code："+code+",msg:"+msg,Toast.LENGTH_SHORT).show();
                }

            }

        });
       // Toast.makeText(getApplicationContext(),strPinglun,Toast.LENGTH_SHORT).show();
    }


}
