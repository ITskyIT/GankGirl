package com.tian.gankgirl.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tian.gankgirl.R;
import com.tian.gankgirl.adapter.MainAdapter;
import com.tian.gankgirl.base.BaseMVPFragment;
import com.tian.gankgirl.bean.MainBean;
import com.tian.gankgirl.http.RetrofitHelper;
import com.tian.gankgirl.mvp.presenter.MainPresenter;
import com.tian.gankgirl.mvp.view.MainView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseMVPFragment<MainPresenter> implements MainView{
    public static final String MAIN_FRAGMENT="main";
    private  int positionIndex;
    @BindView(R.id.xv)
    XRecyclerView recyclerView;
    private MainAdapter adapter;
    public static MainFragment newInstance(int position) {
        
        Bundle args = new Bundle();
        args.putInt(MAIN_FRAGMENT,position);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MainPresenter createPresneter(RetrofitHelper helper) {

        return new MainPresenter(helper);
    }

    @Override
    protected void init() {
        initRecy();
        positionIndex=getArguments().getInt(MAIN_FRAGMENT);
        mPresenter.getData(positionIndex,1);

    }
    private void initRecy() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        recyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });

    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }


    @Override
    public void showData(MainBean data) {
        adapter = new MainAdapter(getActivity(), data.getResults(),positionIndex);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showProgress() {
        Toast.makeText(getActivity(), "努力加载中...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {

    }
}
