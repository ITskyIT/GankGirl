package com.tian.gankgirl.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tian.gankgirl.app.App;
import com.tian.gankgirl.rx.RxManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 无MVC的简单界面类
 * 作者：田
 * 日期：2016/11/5 11:42
 * 邮箱：18236110483@163.com
 */
public abstract class BaseActivity extends AppCompatActivity{
    private Unbinder unBinder;
    protected RxManager mRxManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
       // StatusBarCompat.translucentStatusBar(this);
        unBinder= ButterKnife.bind(this);
        mRxManager=new RxManager();
        init();
        //添加进入集合便于处理
        App.getInstance().addActivity(this);
    }
    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 布局ID
     * @return
     */
    protected abstract int getLayoutId();



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注解
        unBinder.unbind();
        //移除activity
        App.getInstance().removeActivity(this);
        mRxManager.clear();


    }
}
