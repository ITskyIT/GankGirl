package com.tian.gankgirl.mvp.view;

import com.tian.gankgirl.base.BaseView;
import com.tian.gankgirl.bean.MainBean;

/**
 * 作者：田
 * 日期：2016/11/7 11:06
 * 邮箱：18236110483@163.com
 */
public interface MainView extends BaseView{
    void showData(MainBean data);
    void scrolltoTop();
}
