package com.tian.gankgirl.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tian.gankgirl.app.App;
import com.tian.gankgirl.http.RetrofitHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * mvp activity 基类
 */
public abstract class BaseMVPActivity<T extends BaseRx> extends AppCompatActivity implements BaseView{
    protected T mPresenter;
    protected RetrofitHelper helper;
    private Unbinder unBinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
       unBinder= ButterKnife.bind(this);
        init();
        helper=new RetrofitHelper();
        //创建 presenter

        mPresenter=createPresneter(helper);
        //关联view
        if (mPresenter != null)
            mPresenter.attachView(this);
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

    protected abstract T createPresneter(RetrofitHelper helper);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除
        if (mPresenter != null)
            mPresenter.detachView();
        //解除注解
        unBinder.unbind();
        //移除activity
        App.getInstance().removeActivity(this);


    }

}
