package com.example.xulf.shoponline.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xulf.shoponline.DetailActivity;
import com.example.xulf.shoponline.R;
import com.example.xulf.shoponline.adapter.ProductAdapter;
import com.example.xulf.shoponline.bean.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by XuLF on 2017/2/25.
 * 商品排序
 */
public class ProductOrderByFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;

    private ProductAdapter productAdapter;

    private Product product;

    private static List<Product> list;
    private static List<Product> list0;
    private static List<Product> list1;
    private static List<Product> list2;
    private int pageIndex;

    public static final String ARG_PAGE = "ARG_PAGE";


    public static ProductOrderByFragment newInstance(int pager, List<Product> data) {

        list=data;
       // list=new ArrayList<>();
        list0=new ArrayList<>();
        list1 =new ArrayList<>();
        list2=new ArrayList<>();
        for (Product item:
             data) {
            list0.add(item);
            list1.add(item);
            list2.add(item);
        }
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pager);
        ProductOrderByFragment fragment = new ProductOrderByFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            pageIndex = getArguments().getInt(ARG_PAGE);
            Log.i("qqqqw", getArguments().getInt(ARG_PAGE) + "");
        } catch (Exception e) {
            Log.i("qqqqqy", e.getMessage() + "");
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.lv_product);
        listView.setOnItemClickListener(this);
        Log.i("qqqqq", pageIndex + "");
        switch (pageIndex) {
            case 0:
                list=list0;
                Log.i("list0",list.toString());
                initDate(list);
                //Toast.makeText(getActivity(), "13eer" + list.toString(), Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Collections.sort(list1, new Comparator<Product>() {
                    @Override
                    public int compare(Product product, Product t1) {
                        if (product.getPrice() < t1.getPrice()) {
                            return 1;
                        } else if (product.getPrice() == t1.getPrice()) {
                            return 0;
                        }
                        return -1;
                    }
                });
               // Toast.makeText(getActivity(), "12" + list.toString(), Toast.LENGTH_SHORT).show();
                list=list1;
                Log.i("list1",list.toString());
                initDate(list);
                break;
            case 2:
                list=list2;
                Log.i("list2",list.toString());
                initDate(list);
               // Toast.makeText(getActivity(), "14" + list.toString(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }

    }

    //数据适配
    private void initDate(List<Product> list) {
        if (list != null) {

            productAdapter = new ProductAdapter(list, getActivity());
            // recyclerView.setAdapter(productAdapter);
            listView.setAdapter(productAdapter);
           // productAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(pageIndex==1){
            list=list1;
        }else if(pageIndex==0){
            list=list0;
        }else if(pageIndex==2){
            list=list2;
        }
        product=list.get(i);
        Intent intentDetail=new Intent(getContext(), DetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("productDetail", product);
        intentDetail.putExtras(bundle);
        startActivity(intentDetail);
    }
}
