package com.example.xulf.shoponline.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.bean.Pinglun;

import java.sql.Date;
import java.util.List;

/**
 * Created by XuLF on 2017/2/25.
 * 评论适配器
 */
public class PinglunAdapter extends BaseAdapter{
    private List<Pinglun> list;

    public PinglunAdapter(List<Pinglun> list) {
        this.list = list;
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
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pinglun,null);
            TextView tvname=(TextView)view.findViewById(R.id.tv_pinglun_name);
            TextView tvDel=(TextView)view.findViewById(R.id.tv_pinglun_detail);
            TextView tvTime=(TextView)view.findViewById(R.id.tv_pinflun_time);
            tvDel.setText(list.get(i).getCommentContent());
            tvname.setText(list.get(i).getUserName());
            tvTime.setText(list.get(i).getCommentTime());

        }

        return view;
    }
   /* private List<Pinglun> list;


// extends RecyclerView.Adapter<PinglunAdapter.ViewHolder>
    public PinglunAdapter(List<Pinglun> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_pinglun, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvDel.setText(list.get(position).getComment_content());
        holder.tvname.setText(list.get(position).getUser_name());
        holder.tvTime.setText(list.get(position).getComment_time());
    }

    @Override
    public int getItemCount() {
        if(list!=null) {
            return list.size();
        }else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvname;
        TextView tvDel;
        TextView tvTime;
        public ViewHolder(View itemView) {
            super(itemView);
            tvname=(TextView)itemView.findViewById(R.id.tv_pinglun_name);
            tvDel=(TextView)itemView.findViewById(R.id.tv_pinglun_detail);
            tvTime=(TextView)itemView.findViewById(R.id.tv_pinflun_time);
        }
    }*/

}
