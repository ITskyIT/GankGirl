package com.tian.gankgirl.base;

import com.tian.gankgirl.rx.RxManager;

import java.lang.ref.WeakReference;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jiujiu on 2016/11/2.
 */
public class BaseRx<T extends BaseView> implements BasePresenter<T>{
    protected T mView;
    protected WeakReference<T> mViewRef;
    //用来管理订阅和取消订阅  防止内存泄漏
    protected CompositeSubscription mCompositeSubscription;
    protected RxManager mRxManger=new RxManager();
    public void attachView(T view){
        this.mView = view;
        mViewRef=new WeakReference<T>(view);
        this.onstart();

    }

    public void onstart() {

    }

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }
    /**
            * 解除管理
    */
    public void detachView(){
        if (mViewRef!=null){
            mViewRef.clear();
            mViewRef=null;
        }
        this.mView=null;
        unSubscribe();
        mRxManger.clear();
    }
    protected T getView(){
        return mViewRef.get();
    }

}
