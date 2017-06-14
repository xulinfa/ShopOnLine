package com.example.xulf.shoponline.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.AdressManageActivity;
import com.example.xulf.shoponline.App;
import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.bean.Adress;
import com.example.xulf.shoponline.bean.Pinglun;
import com.example.xulf.shoponline.bean.ReturnJson;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XuLF on 2017/3/09.
 * 地址列表适配器
 */
public class AdressAdapter  extends RecyclerView.Adapter<AdressAdapter.ViewHolder>{

    private List<Adress> list;//地址列表

    private Context context;//上下文

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();//网络请求

    private App app=App.getInstance();//app

    private OnItemClickListener onItemClickListener;//监听


    public AdressAdapter(List<Adress> list, Context context) {
        this.list = list;
        this.context=context;
    }

    @Override
    public AdressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_adress, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(final  AdressAdapter.ViewHolder holder, final int position) {
        holder.tvReceiverName.setText(list.get(position).getRecipients());
        holder.tvAdress.setText(list.get(position).getAddress());
        holder.tvpostcode.setText(list.get(position).getPostcode());
        holder.tvReceiverNum.setText(list.get(position).getUserName());
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(app.getUser()!=null) {
                    /*参数设置*/
                    Map<String, Object> params = new HashMap<>(2);
                    params.put("userName", app.getUser().getUserName());
                    params.put("addressID", list.get(position).getAddressID());
                    /*网络请求*/
                    okHttpHelper.post(Contants.DELADRESS, params, new MyCallback<ReturnJson<String>>(context) {
                        @Override
                        public void doRequestBefore(Request request) {

                        }

                        @Override
                        public void onError(Response response) {
                            Toast.makeText(context, "请检查网络！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(Response response, ReturnJson<String> stringReturnJson) {
                            String code = stringReturnJson.getCode();
                            String msg = stringReturnJson.getMsg();
                            if (code.equals("200")) {
                                Log.i("delAdress", "成功");
                                list.remove(position);
                                notifyItemRemoved(position);//界面删除



                            } else {
                                Toast.makeText(context, "删除失败：code：" + code + ",msg:" + msg, Toast.LENGTH_SHORT).show();
                                Log.d("deladresserror", "删除失败：code：" + code + ",msg:" + msg);
                            }

                        }

                    });
                }



            }
        });
    }

    @Override
    public int getItemCount() {
        if(list!=null) {
            return list.size();
        }else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvReceiverName;//收货人姓名
        TextView tvReceiverNum;//收货人电话
        TextView tvpostcode;//收货人邮箱
        TextView tvAdress;//收获地址
        Button btnDel;//删除按钮

        public ViewHolder(View itemView) {
            super(itemView);
            tvReceiverName=(TextView)itemView.findViewById(R.id.tv_receiverName);//收货人姓名
            tvReceiverNum=(TextView)itemView.findViewById(R.id.tv_receiverNum);//收货人电话
            tvpostcode=(TextView)itemView.findViewById(R.id.tv_postcode);//收货人邮箱
            tvAdress=(TextView)itemView.findViewById(R.id.tv_receiverAdress);//收获地址
            btnDel=(Button)itemView.findViewById(R.id.btn_deleteAdress);//删除按钮
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener!=null){
                onItemClickListener.onItemClick(view,getPosition());

            }
        }
    }

    //定义Item点击接口
    public interface  OnItemClickListener{
        public void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
/*    private List<Adress> list;//地址列表

    private Context context;//上下文

    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();//网络请求

    private App app=App.getInstance();//app

    public AdressAdapter(List<Adress> list, Context context) {
        this.list = list;
        this.context=context;
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
    public View getView(final int i, View view1, ViewGroup viewGroup) {
        View view=null;
        if(view1!=null){
            view=view1;
        }else{
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_adress,null);
            TextView tvReceiverName=(TextView)view.findViewById(R.id.tv_receiverName);//收货人姓名
            TextView tvReceiverNum=(TextView)view.findViewById(R.id.tv_receiverNum);//收货人电话
            TextView tvpostcode=(TextView)view.findViewById(R.id.tv_receiverNum);//收货人邮箱
            TextView tvAdress=(TextView)view.findViewById(R.id.tv_receiverAdress);//收获地址
            Button btnDel=(Button)view.findViewById(R.id.btn_deleteAdress);//删除按钮

            tvReceiverName.setText(list.get(i).getRecipients());
            tvAdress.setText(list.get(i).getAddress());
            tvpostcode.setText(list.get(i).getAddress());
            tvReceiverNum.setText(list.get(i).getUserName());

            *//*伤处地址事件*//*
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(app.getUser()!=null){
                        *//*参数设置*//*
                        Map<String,Object> params = new HashMap<>(2);
                        params.put("userName",app.getUser().getUserName());
                        params.put("addressID",list.get(i).getAddressID());
                        *//*网络请求*//*
                        okHttpHelper.post(Contants.DELADRESS, params, new MyCallback<ReturnJson<String>>(context) {
                            @Override
                            public void doRequestBefore(Request request) {

                            }

                            @Override
                            public void onError(Response response) {
                                Toast.makeText(context,"请检查网络！",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(Response response, ReturnJson<String> stringReturnJson) {
                                String code=stringReturnJson.getCode();
                                String msg=stringReturnJson.getMsg();
                                if(code.equals("200")){
                                    Log.i("delAdress","成功");


                                }else{
                                    Toast.makeText(context,"删除失败：code："+code+",msg:"+msg,Toast.LENGTH_SHORT).show();
                                    Log.d("deladresserror", "删除失败：code："+code+",msg:"+msg);
                                }

                            }

                        });
                    }
                }
            });
        }

        return view;
    }*/


}
