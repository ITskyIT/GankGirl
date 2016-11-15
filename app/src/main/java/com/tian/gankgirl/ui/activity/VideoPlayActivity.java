package com.tian.gankgirl.ui.activity;

import android.content.Intent;
import android.text.TextUtils;

import com.squareup.picasso.Picasso;
import com.tian.gankgirl.R;
import com.tian.gankgirl.base.BaseActivity;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 视频播放
 */
public class VideoPlayActivity extends BaseActivity {
    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard playerStandard;
   private String vedio_url;

    @Override
    protected void init() {
        Intent intent=getIntent();
       // vedio_url = intent.getStringExtra("videourl");
        //Log.d( "init: ",vedio_url);
        vedio_url="http://flv2.bn.netease.com/videolib3/1609/06/UnuGW1312/SD/UnuGW1312-mobile.mp4";
        boolean setUp = playerStandard.setUp(
                vedio_url, JCVideoPlayer.SCREEN_LAYOUT_LIST,
                TextUtils.isEmpty("测试视频")?"视频标题":"视频描述");
        if (setUp) {
            Picasso.with(this).load(R.mipmap.ic_launcher)
                    .error(R.mipmap.app)
                    .into(playerStandard.thumbImageView);
        }
        /*if (JCVideoPlayerManager.listener() != null) {
            JCVideoPlayer videoPlayer = (JCVideoPlayer) JCVideoPlayerManager.listener();
            if (((ViewGroup) view).indexOfChild(videoPlayer) != -1 && videoPlayer.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                JCVideoPlayer.releaseAllVideos();
            }
        }*/
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vedio_play;
    }


    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
