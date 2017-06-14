package com.example.xulf.shoponline.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.xulf.shoponline.fragment.WelcomeBaseFragment;

import java.util.List;

/**
 * Created by XuLF on 2017/1/20.
 * * 欢迎界面的Fragment适配器
 */
public class WelcomeAdapter extends FragmentStatePagerAdapter{

    private List<WelcomeBaseFragment> list;



    public WelcomeAdapter(FragmentManager fm, List<WelcomeBaseFragment> list) {
        super(fm);
        this.list = list;
    }
    public WelcomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
