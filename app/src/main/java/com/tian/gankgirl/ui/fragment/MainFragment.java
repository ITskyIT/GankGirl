package com.tian.gankgirl.ui.fragment;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

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
import com.tian.gankgirl.ui.activity.VideoPlayActivity;
import com.tian.gankgirl.view.DialogProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseMVPFragment<MainPresenter> implements MainView{
    public static final String MAIN_FRAGMENT="main";
    private  int positionIndex;
    @BindView(R.id.xv)
    XRecyclerView recyclerView;

    private MainAdapter adapter;
    private int page=1;
    private int action=3;
    public DialogProgressBar mDialogProgressBar;
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
        mDialogProgressBar=new DialogProgressBar(getActivity());
        positionIndex=getArguments().getInt(MAIN_FRAGMENT);
        mPresenter.getData(positionIndex, 1);

        initRecy();


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
                if (positionIndex == 4) {
                    startActivity(new Intent(getActivity(), VideoPlayActivity.class)
                            .putExtra("videourl", mode.getUrl()));

                } else {

                }
            }

            @Override
            public void onItemLongClick(View view, int position, MainBean.MainData mode) {

            }
        });

       /* recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (positionIndex==4){
                    Log.e("dddddddddddd",positionIndex+"");
                    if (JCVideoPlayerManager.getFirst()!= null) {
                        JCVideoPlayer videoPlayer = (JCVideoPlayer) JCVideoPlayerManager.getFirst();
                        if (((ViewGroup) view).indexOfChild(videoPlayer) != -1 && videoPlayer.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                            JCVideoPlayer.releaseAllVideos();
                        }
                    }
                }
            }
        });*/
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
    public void onStop() {
        super.onStop();
        JCVideoPlayer.releaseAllVideos();
    }
    @Override
    public void showData(MainBean data) {
        List<MainBean.MainData> li=new ArrayList<>();
        li=data.getResults();
        if (action==0){
            list.clear();
            list.addAll(li);
        }else{
        list.addAll(li);
        }
        adapter.setDatas(list);
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();

    }

    @Override
    public void scrolltoTop() {
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void showProgress() {
        Log.e("666666","lll");
        mDialogProgressBar.show();
    }

    @Override
    public void hideProgress() {
        mDialogProgressBar.dismiss();
    }
}
