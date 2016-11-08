package com.tian.gankgirl.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tian.gankgirl.R;
import com.tian.gankgirl.adapter.MyFragmentAdapter;
import com.tian.gankgirl.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity{
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.vps)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    MyFragmentAdapter adapter;
    @Override
    protected void init() {
        //setToolBar(toolbar,"GankGirl");
        toolbar.setTitle("GankGirl");
        setSupportActionBar(toolbar);
        adapter=new MyFragmentAdapter(getSupportFragmentManager(),this);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }
}
