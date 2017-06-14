package com.example.xulf.shoponline.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.xulf.shoponline.bean.Product;
import com.example.xulf.shoponline.fragment.ProductOrderByFragment;

import java.util.List;

/**
 * Created by XuLF on 2017/2/25.
 * 产品页的ViewPage适配器
 */
public class ProductPageAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"默认","价格","综合"};
    private Context context;
   List<Fragment> list;
   // private List<Product> productList;

    public ProductPageAdapter(FragmentManager fm, Context context,List<Fragment> fragments) {
        super(fm);
        this.context=context;
        this.list=fragments;
        //this.productList=list;
    }


    @Override
    public Fragment getItem(int position) {
       return list.get(position);
      // return ProductOrderByFragment.newInstance(position,productList);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
