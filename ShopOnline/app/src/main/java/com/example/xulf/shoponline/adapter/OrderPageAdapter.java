package com.example.xulf.shoponline.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by XuLF on 2017/2/25.
 * 订单页的ViewPage适配器
 */
public class OrderPageAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"全部","交易中","已完成"};
    private Context context;
   List<Fragment> list;
    public OrderPageAdapter(FragmentManager fm, Context context, List<Fragment> fragments) {
        super(fm);
        this.context=context;
        this.list=fragments;
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
