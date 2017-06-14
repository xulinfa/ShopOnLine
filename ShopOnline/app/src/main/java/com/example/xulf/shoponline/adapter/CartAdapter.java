package com.example.xulf.shoponline.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.bean.Cart;
import com.example.xulf.shoponline.bean.OrderProduct;
import com.example.xulf.shoponline.com.loopj.android.image.SmartImageView;
import com.example.xulf.shoponline.fragment.CartFragment;
import com.example.xulf.shoponline.service.CartService;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by XuLF on 2016/11/17.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Cart> list;//数据
    private Context context;
    private CartService cartService;//购物车
    private OnItemClickListener onItemClickListener;//监听

    //===========监听isChenked更新总价
    private  TextView tvTotalPrice;
    private CheckBox checkBox;//全选复选框
    public void setTextView(TextView tvTotalPrice){
        this.tvTotalPrice=tvTotalPrice;
    }
    public void setCheckBox(final CheckBox checkBox){
        this.checkBox=checkBox;

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAll_Yes_No(checkBox.isChecked());//传入状态
                getTotalPrice();//重新计算价格
            }
        });
    }


    public CartAdapter(List<Cart> list, Context context, TextView tvTotalPrice, CheckBox checkBox) {
        this.list = list;
        this.context = context;
        setCheckBox(checkBox);
        setTextView(tvTotalPrice);
        cartService=new CartService(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(list.get(position).getProductImg()!=null&&!list.get(position).getProductImg().equals("")) {
            holder.smartImageView.setImageURI(Uri.parse(list.get(position).getProductImg()));//产品图片
        }
        holder.itemCheckBox.setChecked(list.get(position).isChecked());//是否选中
        holder.tvDes.setText(list.get(position).getProductName()+"  "+list.get(position).getProductDetail());//描述
        holder.tvNum.setText(String.valueOf(list.get(position).getBuyNumber()));
        String price=String.valueOf(list.get(position).getPrice());
        holder.tvPrice.setText("￥"+price);//价格String.valueOf(cart.getPrice())
        getTotalPrice();
        //数量加按钮点击监听事件
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int num=list.get(position).getBuyNumber()+1;//更改数量
                list.get(position).setBuyNumber(num);
                holder.tvNum.setText(String.valueOf(num));//界面更改数量
                cartService.update(list.get(position));//更新购物车的内容
                getTotalPrice();
               // Log.i("数量修改日志","Current NUM："+list.get(position).getCount());
            }
        });
        //数量减按钮点击监听事件
        holder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).getBuyNumber()==1){
                    //最少一件
                    Toast.makeText(context,"min:1",Toast.LENGTH_SHORT).show();
                }else if(list.get(position).getBuyNumber()>1){
                    int num=list.get(position).getBuyNumber()-1;//更改数量
                    list.get(position).setBuyNumber(num);//设置新数量
                    holder.tvNum.setText(String.valueOf(num));//界面更改数量
                    cartService.update(list.get(position));//更新购物车的内容
                    getTotalPrice();
                }

            }
        });

        //选中与否
        holder.itemCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart  cart=list.get(position);
                cart.setChecked(!cart.isChecked());//取反
                notifyItemChanged(position);
                cartService.update(cart);
                getTotalPrice();
                checkAllState();
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
        SimpleDraweeView smartImageView;
        CheckBox itemCheckBox;
        TextView tvDes,tvPrice,tvAdd,tvDel,tvNum;

        public ViewHolder(View itemView) {
            super(itemView);
             smartImageView=(SimpleDraweeView)itemView.findViewById(R.id.sdv_productImg);//产品图片
             itemCheckBox=(CheckBox)itemView.findViewById(R.id.cb_checkproduct);//是否选中
             tvDes=(TextView)itemView.findViewById(R.id.tv_cartProductDes);//描述
             tvPrice=(TextView)itemView.findViewById(R.id.tv_cartProductPrice);//价格
             tvAdd=(TextView)itemView.findViewById(R.id.tv_add);//添加按钮
             tvDel=(TextView)itemView.findViewById(R.id.tv_del);//减按钮
             tvNum=(TextView)itemView.findViewById(R.id.tv_num);//件数

        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener!=null){
                onItemClickListener.onItemClick(view,getPosition());
            }
        }
    }
    //计总价
    public void  getTotalPrice(){
        double total=0;//总价格
        if(list!=null&&list.size()>0){
            //有物品
            for (Cart item:list) {
                if(item.isChecked()){
                    total+=item.getBuyNumber()*item.getPrice();//计算总价
                }

            }
        }
        tvTotalPrice.setText("总计："+total+"");
    }
    //全选按钮
public void checkAll_Yes_No(boolean tag){
    if(list!=null&&list.size()>0){
        int i=0;
        for (Cart item: list) {
            item.setChecked(tag);
            notifyItemChanged(i);
            i++;
        }
    }
}
    //判断全选按钮的状态
    public void checkAllState(){
        int count=0;//购物车条数
        int num=0;//被选中的个数
        if (list!=null){
            //购物车有内容时
            count=list.size();
            for (Cart item:list) {
                //如果已经被选中
                if(item.isChecked()){
                    num++;
                }else {
                    //不全选
                    checkBox.setChecked(false);
                    break;
                }
            }
            //判断是否为全选
            if(count==num){
                checkBox.setChecked(true);
            }
        }
    }

/*
    public Cart getItem(int position){
        if(position>=list.size()){
            return null;
        }else {
            return list.get(position);
        }

    }
*/

    //清空购物车
    public  void clear(){
        if(list==null||list.size()<0){
            //没有数据的时候
            return ;
        }else{
            //迭代清除
            for(Iterator item=list.iterator();item.hasNext();){
                Cart cart= (Cart) item.next();
                int position=list.indexOf(cart);//获取位置
                item.remove();
               // cartService.delete(cart);
                notifyItemRemoved(position);
            }
        }
    }

    private List<OrderProduct> orderProducts=new ArrayList<>();
    private OrderProduct orderProduct=null;
    /*获取被选中的数据*/
    public  List<OrderProduct> getCheckOrderProduct(){

        if(list!=null&&list.size()>0){
            //有物品
            for (Cart item:list) {
                if(item.isChecked()){
                    orderProduct=new OrderProduct();
                    orderProduct.setProductID(item.getProductID());
                    orderProduct.setBuy_number(item.getBuyNumber());
                    orderProduct.setProductImg(item.getProductImg());
                    orderProduct.setProductState(item.getProductState());
                    orderProduct.setPrice(item.getPrice());
                    orderProduct.setCategorys(item.getCategorys());
                    orderProduct.setCategorysImg(item.getCategorysImg());
                    orderProduct.setProductName(item.getProductName());
                    orderProduct.setProductDetail(item.getProductDetail());
                    orderProducts.add(orderProduct);
                }

            }
        }

        return orderProducts;
    }

    //删除选中商品
    public void delCartProduct(){
        if(list==null||list.size()<0){
            return;
        }else {
            for(Iterator iterator=list.iterator();iterator.hasNext();){
                Cart cart= (Cart) iterator.next();
                if(cart.isChecked()){
                 //选中则清除
                    int position =list.indexOf(cart);
                    iterator.remove();
                    cartService.delete(cart);
                    notifyItemRemoved(position);
                }
            }
        }
    }
    //添加
    public void addData(List<Cart> cartList){
        if(cartList!=null&&cartList.size()>0){
            for (Cart cart:cartList){
                list.add(0,cart);
                notifyItemInserted(0);
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

}
