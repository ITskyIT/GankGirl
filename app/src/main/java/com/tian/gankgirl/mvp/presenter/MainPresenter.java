package com.tian.gankgirl.mvp.presenter;

import com.tian.gankgirl.base.BaseRx;
import com.tian.gankgirl.bean.MainBean;
import com.tian.gankgirl.http.RetrofitHelper;
import com.tian.gankgirl.mvp.view.MainView;
import com.tian.gankgirl.rx.RxUtil;

import rx.Subscription;
import rx.functions.Action1;

/**
 * 作者：田
 * 日期：2016/11/7 11:21
 * 邮箱：18236110483@163.com
 */
public class MainPresenter extends BaseRx<MainView> {
    private RetrofitHelper mRetrofitHelper;

    public MainPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    public void getData(int position, int page) {
        switch (position) {
            case 0:
                Subscription rxSubscription = mRetrofitHelper.getAppData("all", page)
                        .compose(RxUtil.<MainBean>rxSchedulerHelper())
                        .subscribe(new Action1<MainBean>() {
                            @Override
                            public void call(MainBean commentBean) {
                                mView.showData(commentBean);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.showMsg("加载失败");
                            }
                        });
                addSubscrebe(rxSubscription);
                break;
            case 1:
                Subscription rxSubscription1 = mRetrofitHelper.getAppData("福利", page)
                        .compose(RxUtil.<MainBean>rxSchedulerHelper())
                        .subscribe(new Action1<MainBean>() {
                            @Override
                            public void call(MainBean commentBean) {
                                mView.showData(commentBean);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.showMsg("加载失败");
                            }
                        });
                addSubscrebe(rxSubscription1);
                break;
            case 2:
                Subscription rxSubscription2 = mRetrofitHelper.getAppData("Android", page)
                        .compose(RxUtil.<MainBean>rxSchedulerHelper())
                        .subscribe(new Action1<MainBean>() {
                            @Override
                            public void call(MainBean commentBean) {
                                mView.showData(commentBean);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.showMsg("加载失败");
                            }
                        });
                addSubscrebe(rxSubscription2);
                break;
            case 3:
                Subscription rxSubscription3 = mRetrofitHelper.getAppData("iOS", page)
                        .compose(RxUtil.<MainBean>rxSchedulerHelper())
                        .subscribe(new Action1<MainBean>() {
                            @Override
                            public void call(MainBean commentBean) {
                                mView.showData(commentBean);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.showMsg("加载失败");
                            }
                        });
                addSubscrebe(rxSubscription3);
                break;
            case 4:
                Subscription rxSubscription4 = mRetrofitHelper.getAppData("休息视频", page)
                        .compose(RxUtil.<MainBean>rxSchedulerHelper())
                        .subscribe(new Action1<MainBean>() {
                            @Override
                            public void call(MainBean commentBean) {
                                mView.showData(commentBean);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.showMsg("加载失败");
                            }
                        });
                addSubscrebe(rxSubscription4);
                break;
            case 5:
                Subscription rxSubscription5 = mRetrofitHelper.getAppData("前端", page)
                        .compose(RxUtil.<MainBean>rxSchedulerHelper())
                        .subscribe(new Action1<MainBean>() {
                            @Override
                            public void call(MainBean commentBean) {
                                mView.showData(commentBean);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.showMsg("加载失败");
                            }
                        });
                addSubscrebe(rxSubscription5);
                break;

        }

    }
}
