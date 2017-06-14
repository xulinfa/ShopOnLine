package com.example.xulf.shoponline;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.adapter.PinglunAdapter;
import com.example.xulf.shoponline.bean.OrderProduct;
import com.example.xulf.shoponline.bean.Pinglun;
import com.example.xulf.shoponline.bean.Product;
import com.example.xulf.shoponline.bean.ReturnJson;
import com.example.xulf.shoponline.bean.ReturnJsonList;
import com.example.xulf.shoponline.com.loopj.android.image.SmartImage;
import com.example.xulf.shoponline.com.loopj.android.image.SmartImageView;
import com.example.xulf.shoponline.service.CartService;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.MyListView;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by XuLF on 2017/2/20.
 * 详情页
 */
public class DetailActivity extends AppCompatActivity{
    private TextView tvno;//没有评论

    private SmartImageView smimg_pic;//商品详情图片
    private TextView tvdetailNameOrDrc;//商品名称或者商品描述
    private TextView tvDetailPrice;//价格
    private static   Product product;
    private static   List<OrderProduct> productList;//下单时所需产品
    private OrderProduct orderProduct;

    private CartService cartService;//购物车

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();//网络

    //==评论
    private MyListView ltv_pinlun;//评论列表

    private List<Pinglun> pinglunList;//评论数据

    private PinglunAdapter pinglunAdapter;//评论适配器

    private String proId;//商品id

    private App app=App.getInstance();


    private LinearLayout relativeLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        app.myActivityManager.addActivity(this);//添加Activity

        getSupportActionBar().hide();//标题隐藏

        ShareSDK.initSDK(this);//启动分享
        //duixaxing
        smimg_pic=(SmartImageView)findViewById(R.id.smimg_pic);
        tvdetailNameOrDrc=(TextView)findViewById(R.id.tv_detail_proName);
        tvDetailPrice=(TextView)findViewById(R.id.tv_detail_proPrice);
        ltv_pinlun=(MyListView)findViewById(R.id.ltv_pinlun);
        tvno=(TextView)findViewById(R.id.tv_nopinglun);//没有评论

        //=======================================================
       /* relativeLayout=(LinearLayout)findViewById(R.id.rl_detail);
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            float FirstDistance;
            float LastDistance=-1;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        Log.i("222222222","dow");
                        break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("----放开----");

                        break;
                    case MotionEvent.ACTION_MOVE:


                        if (motionEvent.getPointerCount() ==2) {

                            finish();
                        }
                        break;

                    default:
                        break;

                }





                        return true;
            }
        });*/
        //========================================================


        cartService=new CartService(getApplicationContext());//初始化购物车操作

        Intent intent=this.getIntent();
        product= (Product) intent.getSerializableExtra("productDetail");
        proId=product.getProductID()+"";//商品id
        smimg_pic.setImageUrl(product.getProductImg());
        tvdetailNameOrDrc.setText(product.getProductName()+" "+product.getProductDetail()+"  ");
        tvDetailPrice.setText("￥"+product.getPrice()+" ");
        initPinglun();//评论

       //ltv_pinlun.setSelection(ListView.FOCUS_DOWN);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);//停止分享
        app.myActivityManager.finishActivity(this);//添加Activity
    }

    /*初始化评论*/
    private void initPinglun(){

        Map<String,Object> params = new HashMap<>(1);
        params.put("productID",proId);
        okHttpHelper.post(Contants.GETPRODUCTPINGLUN, params, new MyCallback<ReturnJsonList<Pinglun>>(getApplicationContext()) {
            @Override
            public void doRequestBefore(Request request) {

            }

            @Override
            public void onError(Response response) {

            }

            @Override
            public void onSuccess(Response response, ReturnJsonList<Pinglun> pinglunReturnJsonList) {
                String code=pinglunReturnJsonList.getCode();
                String msg=pinglunReturnJsonList.getMsg();
                if (code.equals("200")){

                    pinglunList=pinglunReturnJsonList.getData();
                    if(pinglunList.size()==0||pinglunList==null){
                        tvno.setVisibility(View.VISIBLE);
                    }else{
                        tvno.setVisibility(View.GONE);
                    }
                    //适配
                    pinglunAdapter=new PinglunAdapter(pinglunList);
                    ltv_pinlun.setAdapter(pinglunAdapter);
                  //  pinglunAdapter.notifyDataSetChanged();
                  //  fixListViewHeight(ltv_pinlun);//重新定义LIstView的高度
                  //  ltv_pinlun.setFocusable(false);//解决其实位置不是顶端问题
                }else{
                   // tvno.setVisibility(View.VISIBLE);
                    Log.i("pinlungeterr","code:"+code+",msg:"+msg);
                }
            }

        });

      /*  pinglunList=new ArrayList<>();
        //假数据设定
        for(int i=0;i<6;i++){
            Pinglun pinglun=new Pinglun();
            pinglun.setCommentContent("首次网购，机器满意！"+i);
            pinglun.setCommentTime("2017-02-01");
            pinglun.setUserName("18459159891");
            pinglunList.add(pinglun);
        }*/

    }

    /*为ListView重新布局*/
    public void fixListViewHeight(ListView listView) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int i = 0, len = listAdapter.getCount(); i <len; i++) {
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

    /*立即购买*/
    public void buyNowClick(View view){

        /*转为订单产品*/
        orderProduct=new OrderProduct();
        orderProduct.setBuy_number(1);
        orderProduct.setProductName(product.getProductName());
        orderProduct.setProductID(product.getProductID());
        orderProduct.setProductDetail(product.getProductDetail());
        orderProduct.setProductImg(product.getProductImg());
        orderProduct.setCategorys(product.getCategorys());
        orderProduct.setCategorysImg(product.getCategorysImg());
        orderProduct.setPrice(product.getPrice());
        orderProduct.setProductState(product.getProductState());
        orderProduct.setProductImg(product.getProductImg());

        /*从底部弹出的对话框*/
        final Dialog dialog=new Dialog(this,R.style.my_dialog);
        RelativeLayout root=(RelativeLayout) LayoutInflater.from(this).inflate(R.layout.dialog_buynow,null);
        SmartImageView smartImage= (SmartImageView) root.findViewById(R.id.smv_dialog_product_pic);//图
        smartImage.setImageUrl(orderProduct.getProductImg());
        TextView tvName= (TextView) root.findViewById(R.id.tv_dialog_proName);//名字
        tvName.setText(orderProduct.getProductName());
        final TextView tvPrice= (TextView) root.findViewById(R.id.tv_dialog_proPrice);//价格
        tvPrice.setText("￥"+product.getPrice()+"");
        ImageView imageView=(ImageView) root.findViewById(R.id.img_cancle);//取消
        TextView tvadd=(TextView)root.findViewById(R.id.tv_dialog_add);//添加
        TextView tvDel=(TextView)root.findViewById(R.id.tv_dialog_del);//减
        final TextView tvNum=(TextView)root.findViewById(R.id.tv_dialog_num);//件数
        Button btnSure= (Button)root.findViewById(R.id.btn_dialog_sure);//确定按钮
        /*添加点击监听事件*/
        tvadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=Integer.parseInt(tvNum.getText().toString())+1;
                tvNum.setText(num+"");
                double price=orderProduct.getPrice()*num;
                tvPrice.setText("￥"+price+"");
            }
        });
        /*减少点击事件*/
        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=1;
                if(Integer.parseInt(tvNum.getText().toString())<=1){
                    num=1;
                }else{
                    num=Integer.parseInt(tvNum.getText().toString())-1;
                }
                tvNum.setText(num+"");
                double price=orderProduct.getPrice()*num;
                tvPrice.setText("￥"+price+"");
            }
        });
        /*确定点击事件*/
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productList=new ArrayList<>();
                orderProduct.setBuy_number(Integer.parseInt(tvNum.getText().toString()));
                productList.add(orderProduct);
               dialog.dismiss();
             /*   调到订单确认页*/
                Intent intent=new Intent(getApplicationContext(),CreateOrderActivity.class);
                intent.putExtra("orderProduct", (Serializable) productList);
                startActivity(intent);
                finish();
                //Toast.makeText(getApplicationContext(),"立即购买",Toast.LENGTH_SHORT).show();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(root);

        Window window=dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);//底部
        window.setWindowAnimations(R.style.dialogstyle);//动画
        WindowManager.LayoutParams lp = window.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -20; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
//      lp.alpha = 9f; // 透明度
        root.measure(0, 0);
       // lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        window.setAttributes(lp);
        dialog.show();



    }

    /*加入购物车*/
    public void addCarCLick(View view){
        App app=App.getInstance();
        if(app.getUser()!=null){
                cartService.putIntoCart(product);
                try{
                    Map<String,Object> params = new HashMap<>(2);
                    params.put("userName",app.getUser().getUserName());
                    params.put("productID",product.getProductID());
                    params.put("buyNumber",1);
                    okHttpHelper.post(Contants.ADDCART, params, new MyCallback<ReturnJson<String>>(getApplicationContext()) {
                        @Override
                        public void doRequestBefore(Request request) {

                        }

                        @Override
                        public void onError(Response response) {
                            Log.i("AddCarterror","网络请求失败");
                        }

                        @Override
                        public void onSuccess(Response response, ReturnJson<String> stringReturnJson) {
                            if(stringReturnJson.getCode().equals("200")){
                                Log.i("addcart","success");
                            }else{
                                Log.i("addcartfail","code:"+stringReturnJson.getCode()+",msg:"+stringReturnJson.getMsg());
                            }

                        }
                    });
                }catch (Exception e){
                    Log.i("ExpectionAddCart","加入购物车失败");
                }
                Toast.makeText(view.getContext(),"成功加入购物车",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getApplicationContext(),"请先到个人中心进行登陆！",Toast.LENGTH_SHORT).show();
        }

    }

    /*backHome返回首页*/
    public void backHome(View view){
       // Toast.makeText(getApplicationContext(),"返回首页",Toast.LENGTH_SHORT).show();
        Intent intentHome=new Intent(DetailActivity.this,MainActivity.class);
        startActivity(intentHome);
        finish();
    }

    /*分享*/
    public void shareCLick(View view){
        showShare();
        Toast.makeText(getApplicationContext(),"分享",Toast.LENGTH_SHORT).show();
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("安卓网上便利店分享");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("安卓网上便利店"+product.getProductName());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
