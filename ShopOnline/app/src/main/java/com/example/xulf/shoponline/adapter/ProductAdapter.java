package com.example.xulf.shoponline.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.App;
import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.bean.Product;
import com.example.xulf.shoponline.bean.ReturnJson;
import com.example.xulf.shoponline.bean.ReturnJsonList;
import com.example.xulf.shoponline.com.loopj.android.image.SmartImageView;
import com.example.xulf.shoponline.service.CartService;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XuLF on 2016/11/12.
 */
public class ProductAdapter extends BaseAdapter{

private  List<Product> list;
    private Context context;

    ////////////////
    private LayoutInflater mInflater;
    /////////////


    private OkHttpHelper  okHttpHelper=OkHttpHelper.getInstance();
    //购物车
    private CartService cartService;
   // Product wares=null;

    public ProductAdapter(List<Product> list, Context context) {
        this.list = list;
        this.context = context;
        cartService=new CartService(this.context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView( final  int i, View view, ViewGroup viewGroup) {
        View view1=null;
   /*     if(view!=null){
            view1=view;
        }else{*/
            mInflater= LayoutInflater.from(viewGroup.getContext());
            view1=mInflater.inflate(R.layout.item_hot,null);
          // SimpleDraweeView simpleDraweeView=(SimpleDraweeView)view1.findViewById(R.id.sdv_imageofproducr);
            SmartImageView imageView=(SmartImageView) view1.findViewById(R.id.imv_hot);
           // ImageView imageView=(ImageView)view1.findViewById(R.id.imv_hot);
            TextView tvdescribe=(TextView)view1.findViewById(R.id.tv_describeofproduct);
            TextView tvprice=(TextView)view1.findViewById(R.id.tv_priceofproduct);
            TextView tvProductName=(TextView)view1.findViewById(R.id.tv_productName);
            Button btnAdd=(Button)view1.findViewById(R.id.btn_addToCarHot);//添加按钮



          // simpleDraweeView.setImageURI(Uri.parse(wares.getImgUrl()));
            imageView.setImageUrl( list.get(i).getProductImg());
            tvdescribe.setText( list.get(i).getProductDetail());
            String money= String.valueOf( list.get(i).getPrice());
            tvprice.setText("￥"+money);
            tvProductName.setText( list.get(i).getProductName());

            //点击添加按钮
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App app=App.getInstance();
                    if(app.getUser()!=null){
                        cartService.putIntoCart(list.get(i));
                        try{

                            Map<String,Object> params = new HashMap<>(2);
                            params.put("userName",app.getUser().getUserName());
                            params.put("productID",list.get(i).getProductID());
                            Log.i("addcartPId",list.get(i).getProductID()+"");
                            params.put("buyNumber",1);
                            okHttpHelper.post(Contants.ADDCART, params, new MyCallback<ReturnJson<String>>(context) {
                                @Override
                                public void doRequestBefore(Request request) {

                                }

                                @Override
                                public void onError(Response response) {

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

                        }
                        Toast.makeText(view.getContext(),"成功加入购物车",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(view.getContext(),"请先到个人中心进行登陆！",Toast.LENGTH_SHORT).show();
                    }


                }
            });
      /*  }*/
        return view1;
    }

}
