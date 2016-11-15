package com.tian.gankgirl.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tian.gankgirl.R;
import com.tian.gankgirl.adapter.MyFragmentAdapter;
import com.tian.gankgirl.app.AppConst;
import com.tian.gankgirl.base.BaseActivity;

import butterknife.BindView;
import cn.hugeterry.updatefun.UpdateFunGO;
import cn.hugeterry.updatefun.config.UpdateKey;

public class MainActivity extends BaseActivity{
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.vps)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    MyFragmentAdapter adapter;
    @BindView(R.id.fab)
    FloatingActionButton fab;
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布消息
                mRxManager.post("toptop", "");
            }
        });
        initBanben();
    }

    private void initBanben() {
        //此处填上在http://fir.im/注册账号后获得的API_TOKEN以及APP的应用ID
        UpdateKey.API_TOKEN = AppConst.API_FIRE_TOKEN;
        UpdateKey.APP_ID = AppConst.APP_FIRE_ID;
        //如果你想通过Dialog来进行下载，可以如下设置
        UpdateKey.DialogOrNotification=UpdateKey.WITH_DIALOG;
        UpdateFunGO.init(this);
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
    @Override
    protected void onResume() {
        super.onResume();
        UpdateFunGO.onResume(this);
    }

}
