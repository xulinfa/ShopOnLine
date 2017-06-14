package com.example.xulf.shoponline.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.AdressManageActivity;
import com.example.xulf.shoponline.App;
import com.example.xulf.shoponline.LoginActivity;
import com.example.xulf.shoponline.MyOrderActivity;
import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.SettingActivity;
import com.example.xulf.shoponline.ZxingWebViewActivity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import de.hdodenhof.circleimageview.CircleImageView;

/*个人中心*/
public class MineFragment extends Fragment implements View.OnClickListener{

    private LinearLayout llorderAll;//订单
    private LinearLayout llMyAdress;//地址
    private LinearLayout llChangeMessage;//修改信息
    private LinearLayout llSaoyisao;//扫一扫
    private LinearLayout llContasUs;//联系我们


    private CircleImageView imgHead;//登陆头像

    private TextView tvUserName;

    private static  int REQUEST_CODE=1;

    private App app=App.getInstance();//App


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_mine, container, false);
        llorderAll=(LinearLayout)view.findViewById(R.id.llorder);//查看全部订单
        llMyAdress=(LinearLayout)view.findViewById(R.id.ll_myReceiverAdress);//我的收获地址
        llChangeMessage=(LinearLayout)view.findViewById(R.id.ll_change_personmessage);//修改个人资料
        llSaoyisao=(LinearLayout)view.findViewById(R.id.ll_saoyisao);//二维码扫描
        llContasUs=(LinearLayout)view.findViewById(R.id.ll_contas_us);//联系我们
        imgHead=(CircleImageView)view.findViewById(R.id.img_mine);//登陆头像

        tvUserName=(TextView)view.findViewById(R.id.tv_username);

        ZXingLibrary.initDisplayOpinion(getActivity());//二维码扫描

        App app=App.getInstance();
        if(app.getUser()!=null) {
            if (app.getUser().getUserName() != null) {
                tvUserName.setText(app.getUser().getUserName());
            } else {
                tvUserName.setText("");
            }
        }else{
            tvUserName.setText("点击头像进行登陆");//未登陆则提示可以点击头像进行登陆
        }
        llorderAll.setOnClickListener(this);
        llMyAdress.setOnClickListener(this);
        llChangeMessage.setOnClickListener(this);
        llSaoyisao.setOnClickListener(this);
        llContasUs.setOnClickListener(this);
        imgHead.setOnClickListener(this);

        return view;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llorder:
                Log.i("Look","查看订单");
                if(app.getUser()!=null){
                    Intent intent=new Intent(getContext(), MyOrderActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }

                break;

            case R.id.ll_myReceiverAdress:
                Log.i("mineAdress","管理收获地址！");
                if(app.getUser()!=null) {
                    Intent addressintent = new Intent(getContext(), AdressManageActivity.class);
                    startActivity(addressintent);
                }else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.img_mine:
                //登陆头像点击
                Log.i("Login","点击登陆头像");
                Intent intent1=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_contas_us:
                //联系我们
                //uri:统一资源标示符（更广）
                Intent intentPhone = new Intent(Intent.ACTION_CALL,Uri.parse("tel:18459159891"));
                startActivity(intentPhone);
                break;
            case R.id.ll_saoyisao:
                //saiyisao
                Intent intentaoyisao = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intentaoyisao, REQUEST_CODE);
                break;

            case R.id.ll_change_personmessage:
                //设置
               Intent intent2=new Intent(getActivity(), SettingActivity.class);
                startActivity(intent2);
                break;
            default:
                Toast.makeText(getContext(),"ff！",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if(result.startsWith("http")){
                        Intent intent=new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(result);
                        intent.setData(content_url);
                        startActivity(intent);
                       /* */
                    }else{
                        Intent intentZXing=new Intent(getActivity(), ZxingWebViewActivity.class);
                        intentZXing.putExtra("zxingurl",result);
                        startActivity(intentZXing);
                        Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }


    }
}
