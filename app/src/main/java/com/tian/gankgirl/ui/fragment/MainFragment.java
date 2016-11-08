package com.tian.gankgirl.ui.fragment;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tian.gankgirl.R;
import com.tian.gankgirl.adapter.BaseRecyclerViewAdapter;
import com.tian.gankgirl.adapter.MainAdapter;
import com.tian.gankgirl.base.BaseMVPFragment;
import com.tian.gankgirl.bean.MainBean;
import com.tian.gankgirl.http.RetrofitHelper;
import com.tian.gankgirl.mvp.presenter.MainPresenter;
import com.tian.gankgirl.mvp.view.MainView;
import com.tian.gankgirl.ui.activity.GirlPhotoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseMVPFragment<MainPresenter> implements MainView{
    public static final String MAIN_FRAGMENT="main";
    private  int positionIndex;
    @BindView(R.id.xv)
    XRecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private MainAdapter adapter;
    private int page=1;
    private int action=3;
    List<MainBean.MainData> list=new ArrayList<>();
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

        positionIndex=getArguments().getInt(MAIN_FRAGMENT);
        mPresenter.getData(positionIndex,1);
        initRecy();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });

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
                action = 0;
                mPresenter.getData(positionIndex, 1);
            }

            @Override
            public void onLoadMore() {
                page++;
                action = 1;
                mPresenter.getData(positionIndex, page);
            }
        });
        adapter = new MainAdapter(getActivity(), list,positionIndex);
        recyclerView.setAdapter(adapter);
        /**
         * item点击
         */
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<MainBean.MainData>() {
            @Override
            public void onItemClick(View view, int position, MainBean.MainData mode) {
                Toast.makeText(getActivity(), "妹子item", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position, MainBean.MainData mode) {

            }
        });
        /**
         * 图片点击
         */
        adapter.setmOnItemSimpleListener(new BaseRecyclerViewAdapter.OnItemSimpleListener<MainBean.MainData>() {
            @Override
            public void onItemClick(View view, int position) {
                String imgUrl=list.get(position).getUrl();
                Intent intent=new Intent(getActivity(), GirlPhotoActivity.class);
                intent.putExtra("girl",imgUrl);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation
                            (getActivity(), view.findViewById(R.id.img), "girlgirl").toBundle());
                } else {
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }


    @Override
    public void showData(MainBean data) {
        List<MainBean.MainData> li=new ArrayList<>();
        li=data.getResults();
        if (action==0){
            list.clear();
            list.addAll(li);
        }else{
        list.addAll(data.getResults());
        }
        adapter.setDatas(list);
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();

    }

    @Override
    public void showProgress() {
        Toast.makeText(getActivity(), "努力加载中...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {

    }
}
