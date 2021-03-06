package com.tian.gankgirl.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tian.gankgirl.http.RetrofitHelper;
import com.tian.gankgirl.rx.RxManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * mvp fragment 基类
 * 作者：田
 * 日期：2016/11/5 12:14
 * 邮箱：18236110483@163.com
 */
public abstract class BaseMVPFragment<T extends BaseRx> extends Fragment implements BaseView{
    private Unbinder mUnBinder;
    protected View mView;
    protected T mPresenter;
    protected RetrofitHelper helper;
   // protected boolean isInited = false;
    protected RxManager mRxManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        helper=new RetrofitHelper();
        //创建 presenter

        mPresenter=createPresneter(helper);
        if (mPresenter != null)
            mPresenter.attachView(this);
        mUnBinder = ButterKnife.bind(this, mView);
        mRxManager=new RxManager();
        init();
        return mView;
    }

    protected abstract T createPresneter(RetrofitHelper helper);
   /* @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null)
            mPresenter.attachView(this);
        mUnBinder = ButterKnife.bind(this, view);
        if (savedInstanceState == null) {
            if (!isHidden()) {
                isInited = true;
                initEventAndData();
            }
        } else {

        }
    }*/

   // protected abstract void initEventAndData();

   /* @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isInited && !hidden) {
            isInited = true;
            initEventAndData();
        }
    }
*/
    protected abstract void init();

    protected abstract int getLayoutId();
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
        if (mPresenter != null) mPresenter.detachView();
        mRxManager.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void showMsg(String message) {
       // Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


}
