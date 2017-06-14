package com.example.xulf.shoponline.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.xulf.shoponline.CategoryByTypeHide;
import com.example.xulf.shoponline.DetailActivity;
import com.example.xulf.shoponline.MainActivity;
import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.SearchProResultActivity;
import com.example.xulf.shoponline.adapter.CategoryProListAdapter;
import com.example.xulf.shoponline.adapter.HomeGVAdapter;
import com.example.xulf.shoponline.adapter.HomeHotAdapter;
import com.example.xulf.shoponline.bean.Banner;
import com.example.xulf.shoponline.bean.Product;
import com.example.xulf.shoponline.bean.ReturnJsonList;
import com.example.xulf.shoponline.bean.Type;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.nineoldandroids.animation.ObjectAnimator;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.List;

/*首页*/
public class HomeFragment extends Fragment {

    private SliderLayout sliderLayout;//轮播
    private List<Banner> bannerList;//轮播对象
    private OkHttpHelper okHttpHelper;//okhttp对象
    private ProgressDialog pdWaitDlg=null;//加载狂

    private GridView gridView;//类别网格视图
    private GridView griViewHot;//热搜网格试图
    private List<Product> listHomeHot=null;//热搜商品
    private List<Type> typeList;//类型列表
    private HomeHotAdapter homeHotAdapter;//热搜适配器
    private HomeGVAdapter homeGVAdapter;//类别适配器

    private EditText edtSearch;//搜索框内容
    private TextView tvSearch;//文字搜索按钮


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

/*解决软键盘问题*/
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    //对象获取
        sliderLayout=(SliderLayout)view.findViewById(R.id.slider);//轮播
        gridView=(GridView) view.findViewById(R.id.gv_home);//类别容器
        griViewHot=(GridView)view.findViewById(R.id.gv_home_hot);//热搜
        edtSearch=(EditText)view.findViewById(R.id.edt_home_search);//搜索编辑框
        tvSearch=(TextView)view.findViewById(R.id.tv_home_search);//文字搜索按钮
        /*搜索按钮点击事件,跳转到*/
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SearchProResultActivity.class);
                intent.putExtra("searchname",edtSearch.getText().toString());
                startActivity(intent);
            }
        });

 /*       //==================加载框
        pdWaitDlg = new ProgressDialog(getActivity());
        pdWaitDlg.setTitle("提示信息");
        pdWaitDlg.setMessage("正在加载...");
        pdWaitDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);//STYLE_HORIZONTAL条形进度条
        pdWaitDlg.setCancelable(false);//点击ProgressDialog以外的区域不会关闭ProgressDialog
        pdWaitDlg.show();
        //==================加载狂结束*/
        okHttpHelper=OkHttpHelper.getInstance();//okhttp的实例化
        bannerData();//轮播数据
        initGridView();//初始化类别数据
        initHomeHot();//热搜

        //动画设置，类型的
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //第一个参数为 view对象，第二个参数为 动画改变的类型，第三，第四个参数依次是开始透明度和结束透明度。
                ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "rotationY", 0f, 360f);
                alpha.setDuration(200);//设置动画时间
                alpha.setInterpolator(new DecelerateInterpolator());//设置动画插入器，减速
                alpha.setRepeatCount(1);//设置动画重复次数，这里-1代表无限
                alpha.setRepeatMode(Animation.REVERSE);//设置动画循环模式。
                alpha.start();//启动动画。

                /*点击跳转到分类页面*/
                Intent  intent=new Intent(getActivity(), CategoryByTypeHide.class);
                intent.putExtra("intentCategory",typeList.get(i).getCategorys());
                startActivity(intent);

            }
        });
          /*热搜项点击事件*/
        griViewHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //第一个参数为 view对象，第二个参数为 动画改变的类型，第三，第四个参数依次是开始透明度和结束透明度。
                ObjectAnimator alpha = ObjectAnimator.ofFloat(view,"translationY", 0, 200, -100,0);
                alpha.setDuration(600);//设置动画时间
                alpha.setInterpolator(new DecelerateInterpolator());//设置动画插入器，减速
                alpha.setRepeatCount(1);//设置动画重复次数，这里-1代表无限
                alpha.setRepeatMode(Animation.REVERSE);//设置动画循环模式。
                alpha.start();//启动动画。
                Product product=new Product();
                product=listHomeHot.get(i);
                /*传送数据对象*/
                Intent intentDetail=new Intent(getContext(), DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("productDetail", product);
                intentDetail.putExtras(bundle);
                startActivity(intentDetail);
                //Toast.makeText(getContext(),"你点中了："+typeList.get(i).getCategorys_name(),Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


    /*将griView设置为水平*/
    public void horizontal_layout(){
        int size = typeList.size();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int allWidth = (int) (110 * size * density);
        int itemWidth = (int) (100 * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                allWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params);// 设置GirdView布局参数
        gridView.setColumnWidth(itemWidth);// 列表项宽
        gridView.setHorizontalSpacing(15);// 列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size);//总长度

    }



    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    //网格试图初始化
    private void initGridView(){
        okHttpHelper.get(Contants.PRODUCTCATEGORY, new MyCallback<ReturnJsonList<Type>>(getActivity()) {
            @Override
            public void doRequestBefore(Request request) {

            }

            @Override
            public void onError(Response response) {

            }

            @Override
            public void onSuccess(Response response, ReturnJsonList<Type> typeReturnJsonList) {
                String code=typeReturnJsonList.getCode();
                String msg=typeReturnJsonList.getMsg();
                if (code.equals("200")){
                    typeList=typeReturnJsonList.getData();
                    if(typeList!=null){
                        horizontal_layout();
                        homeGVAdapter=new HomeGVAdapter(typeList);
                        gridView.setAdapter(homeGVAdapter);
                    }
                }else{
                    Toast.makeText(getActivity(),"code:"+code+",msg:"+msg,Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    /*hot热搜*/
    private void initHomeHot(){
        okHttpHelper.get(Contants.HOTPRODUCT, new MyCallback<ReturnJsonList<Product>>(getActivity()) {
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
                if (code.equals("200")){
                    listHomeHot=productReturnJsonList.getData();
                    for (Product item:listHomeHot){
                        Log.i("ss:",item.getProductImg());
                    }
                    homeHotAdapter=new HomeHotAdapter(listHomeHot);
                    griViewHot.setAdapter(homeHotAdapter);
                    griViewHot.deferNotifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(),"失败，code:"+code+",msg:"+msg,Toast.LENGTH_SHORT).show();
                }

            }


        });
    }

    //轮播数据初始化
    private void bannerData(){
        bannerList=new ArrayList<>();
        Banner banner1=new Banner();
        banner1.setId(1);
        banner1.setImgUrl(R.drawable.banneritem1);
        banner1.setName("海之蓝");
        banner1.setDescription("经典海洋");

        Banner banner2=new Banner();
        banner2.setId(2);
        banner2.setImgUrl(R.drawable.banneritem2);
        banner2.setName("剃须刀");
        banner2.setDescription("吉利剃须刀");

        Banner banner3=new Banner();
        banner3.setId(3);
        banner3.setImgUrl(R.drawable.banneritem3);
        banner3.setName("新农哥");
        banner3.setDescription("新农哥大核桃");

        Banner banner4=new Banner();
        banner4.setId(4);
        banner4.setImgUrl(R.drawable.banneritem4);
        banner4.setName("零食榜");
        banner4.setDescription("banner4");


        bannerList.add(banner1);
        bannerList.add(banner2);
        bannerList.add(banner3);
        bannerList.add(banner4);



        if(bannerList!=null){
            //遍历添加
            for (Banner banner:bannerList) {

                TextSliderView view=new TextSliderView(this.getActivity());
                //添加图片url，可以是本地的，也可以是网络上的
                view.image(banner.getImgUrl()).description(banner.getName());
                //添加到sliderLayout中去
                sliderLayout.addSlider(view);
            }
        }
        //设置过滤效果为向下旋转
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateDown);
        //设置动画效果
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        //设置持续时间2秒
        sliderLayout.setDuration(2000);

    }

    @Override
    public void onStop() {
        super.onStop();
        sliderLayout.stopAutoCycle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sliderLayout.stopAutoCycle();
    }
}
