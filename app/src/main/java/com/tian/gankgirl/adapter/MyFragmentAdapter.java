package com.tian.gankgirl.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tian.gankgirl.ui.fragment.MainFragment;

/**
 * Created by jiujiu on 2016/6/1.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter{

    private String[] titles = new String[]{"全部", "福利", "Android", "IOS", "休息视频", "前端"};
    final int COUNT = titles.length;
    private Context context;
    public MyFragmentAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
