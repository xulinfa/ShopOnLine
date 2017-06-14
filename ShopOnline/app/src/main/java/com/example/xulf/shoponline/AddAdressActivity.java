package com.example.xulf.shoponline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.xulf.shoponline.bean.ReturnJson;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * Created by XuLF on 2017/2/9.
 */
public class AddAdressActivity extends AppCompatActivity  implements View.OnClickListener{
    private TextView tvAdressP;//地址三级联动的文本，福建省福州市鼓楼区

    private EditText edName,edPhone,edAdressDetail,edtPostCode;//收获名、电话、地址详情、邮编

    private ImageView ivBack;//返回

    private Button btnSave;//添加按钮

    private LinearLayout lladress;//地址

    private App app=App.getInstance();//实例对象1

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();//网络请求


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addadress);
        getSupportActionBar().hide();//隐藏标题
        app.myActivityManager.addActivity(this);//添加Activity

        //对象获取
        tvAdressP=(TextView)findViewById(R.id.tv_addadress_adressprovice);//地址三级联动
        edAdressDetail=(EditText) findViewById(R.id.tv_addadress_detailadress);
        edName=(EditText) findViewById(R.id.tv_addadress_username);
        edPhone=(EditText) findViewById(R.id.tv_addadress_userphone);
        edPhone.setText(app.getUser().getUserName());
        ivBack=(ImageView)findViewById(R.id.addadress_back);
        btnSave=(Button)findViewById(R.id.btn_save_adress);
        lladress=(LinearLayout)findViewById(R.id.ll_adress_province);//三级联动
        edtPostCode=(EditText)findViewById(R.id.tv_addadress_postcode);

        /*点击监听事件*/
        ivBack.setOnClickListener(this);
        lladress.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.myActivityManager.finishActivity(this);//结束activity
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.ll_adress_province:
                //点击新增事件
                try {
                    ArrayList<Province> data = new ArrayList<Province>();
                    String json = ConvertUtils.toString(getAssets().open("city.json"));
                    data.addAll(JSON.parseArray(json, Province.class));
                    AddressPicker picker = new AddressPicker(this, data);
                    picker.setCycleDisable(true);
                    // picker.setHideProvince(true);
                    picker.setSelectedItem("福建", "福州", "鼓楼");
                    picker.setColumnWeight(2/8.0, 3/8.0, 3/8.0);//省级、地级和县级的比例为2:3:3
                    picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                        @Override
                        public void onAddressPicked(Province province, City city, County county) {
                            if (county == null) {
                                Toast.makeText(getApplicationContext(), "county:" + county.toString(), Toast.LENGTH_SHORT).show();
                                tvAdressP.setText(province.getAreaName()+city.getAreaName()+"");
                            } else {
                                tvAdressP.setText(province.getAreaName()+city.getAreaName()+county.getAreaName()+"");
                            }
                        }
                    });
                    picker.show();
                } catch (Exception e) {
                    Log.i("error",e.getMessage());
                    Toast.makeText(getApplicationContext(), "e:" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_save_adress:
                if(tvAdressP.getText().toString().equals("")||tvAdressP.getText()==null){
                    Toast.makeText(getApplicationContext(),"用户地址区域需选择！",Toast.LENGTH_SHORT).show();
                }else if(edName.getText().toString().equals("")||tvAdressP.getText()==null){
                    Toast.makeText(getApplicationContext(),"收货人名称不可为空！",Toast.LENGTH_SHORT).show();
                }else if(edPhone.getText().toString().equals("")||tvAdressP.getText()==null){
                    Toast.makeText(getApplicationContext(),"收货人号码不可为空！",Toast.LENGTH_SHORT).show();
                }else if(edAdressDetail.getText().toString().equals("")||tvAdressP.getText()==null){
                    Toast.makeText(getApplicationContext(),"收货人详细地址不可为空！",Toast.LENGTH_SHORT).show();
                }else{
                    //进行地址上送

                    Map<String,Object> params = new HashMap<>(4);
                    params.put("userName",app.getUser().getUserName());

                    String adress=tvAdressP.getText().toString()+edAdressDetail.getText().toString();
                    params.put("address",adress);

                    String recipient=edName.getText().toString();
                    params.put("recipients",recipient);

                    String postcode="";
                    if(edtPostCode.getText().toString().equals("")||edtPostCode.getText()==null) {
                        postcode = "";
                    }
                    postcode = edtPostCode.getText().toString();
                    params.put("postcode",postcode);

                    okHttpHelper.post(Contants.ADDADRESS, params, new MyCallback<ReturnJson<String>>(getApplication()) {
                        @Override
                        public void doRequestBefore(Request request) {

                        }

                        @Override
                        public void onError(Response response) {
                            Toast.makeText(getApplicationContext(),"请检查网络！",Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onSuccess(Response response, ReturnJson<String> stringReturnJson) {
                            String code=stringReturnJson.getCode();
                            String msg=stringReturnJson.getMsg();
                            if(code.equals("200")){
                                Toast.makeText(getApplicationContext(),"地址添加成功！",Toast.LENGTH_SHORT);
                                /*地址列表页面*/
                                Intent intent=new Intent(AddAdressActivity.this,AdressManageActivity.class);
                                startActivity(intent);
                               finish();//结束activity
                            }else{
                                Toast.makeText(getApplicationContext(),"code:"+code+",msg:"+msg,Toast.LENGTH_SHORT);
                            }
                        }
                    });

                }

                break;
            case R.id.addadress_back:
                //返回地址列表页
                Intent  intent=new Intent(AddAdressActivity.this,AdressManageActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
