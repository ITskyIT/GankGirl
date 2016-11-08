package com.tian.gankgirl.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.tian.gankgirl.R;
import com.tian.gankgirl.base.BaseActivity;
import com.tian.gankgirl.util.MyUtils;
import com.tian.gankgirl.util.StatusBarCompat;
import com.tian.gankgirl.util.SystemUiVisibilityUtil;
import com.tian.gankgirl.view.PullBackLayout;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 妹纸
 */
public class GirlPhotoActivity extends BaseActivity implements PullBackLayout.Callback{
    @BindView(R.id.pull_back_layout)
    PullBackLayout pullBackLayout;
    @BindView(R.id.background)
    RelativeLayout background;
    private boolean mIsToolBarHidden;
    private boolean mIsStatusBarHidden;
    private ColorDrawable mBackground;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.photo_touch_iv)
    PhotoView photoView;
    private String imgurl;
    @Override
    protected void init() {
        StatusBarCompat.translucentStatusBar(this);
        Intent intent=getIntent();
        imgurl = intent.getStringExtra("girl");
        pullBackLayout.setCallback(this);
        toolBarFadeIn();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initBackground();
        Picasso.with(this).load(imgurl).into(photoView);
        setPhotoViewClickEvent();
    }
    private void setPhotoViewClickEvent() {
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                hideOrShowToolbar();
                hideOrShowStatusBar();
            }
        });
    }

    private void initBackground() {
        mBackground = new ColorDrawable(Color.BLACK);
        MyUtils.getRootView(this).setBackgroundDrawable(mBackground);
    }


    protected void hideOrShowToolbar() {
        toolbar.animate()
                .alpha(mIsToolBarHidden ? 1.0f : 0.0f)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsToolBarHidden = !mIsToolBarHidden;
    }

    private void hideOrShowStatusBar() {
        if (mIsStatusBarHidden) {
            SystemUiVisibilityUtil.enter(this);
        } else {
            SystemUiVisibilityUtil.exit(this);
        }
        mIsStatusBarHidden = !mIsStatusBarHidden;
    }

    private void toolBarFadeIn() {
        mIsToolBarHidden = true;
        hideOrShowToolbar();
    }

    @Override
    public void onPullStart() {
        toolBarFadeOut();

        mIsStatusBarHidden = true;
        hideOrShowStatusBar();
    }

    private void toolBarFadeOut() {
        mIsToolBarHidden = false;
        hideOrShowToolbar();
    }

    @Override
    public void onPull(float progress) {
        progress = Math.min(1f, progress * 3f);
        mBackground.setAlpha((int) (0xff/*255*/ * (1f - progress)));
    }

    @Override
    public void onPullCancel() {
        toolBarFadeIn();
    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_girl_photo;
    }
}
