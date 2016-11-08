package com.tian.gankgirl.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tian.gankgirl.R;
import com.tian.gankgirl.bean.MainBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：田
 * 日期：2016/11/7 12:22
 * 邮箱：18236110483@163.com
 */
public class MainAdapter extends BaseRecyclerViewAdapter<MainBean.MainData>{

    private int index;
    public MainAdapter(Context context, List<MainBean.MainData> datas, int positionIndex) {
        super(context, datas);
        index=positionIndex;
    }

    @Override
    protected void bindViewHodler(BaseRecyclerViewHolder holder, final int position) {
        final MainHolder hold= (MainHolder) holder;
        hold.desc.setText(mDatas.get(position).getDesc());
        hold.type.setText(mDatas.get(position).getType());
        switch (index){
            case 1:
                mPicasso.load(mDatas.get(position).getUrl()).error(R.mipmap.app).into(hold.iv);
                break;
            case 0:
            case 2:
            case 3:
            case 5:
                if (mDatas.get(position).getImages()==null){
                    if (mDatas.get(position).getUrl().endsWith(".jpg")) {
                        mPicasso.load(mDatas.get(position).getUrl()).error(R.mipmap.app).into(hold.iv);
                    }else {
                        mPicasso.load(R.mipmap.app).error(R.mipmap.app).into(hold.iv);
                    }
                }else {
                    mPicasso.load(mDatas.get(position).getImages().get(0)).error(R.mipmap.app).into(hold.iv);
                }
           break;
        }
        if (mOnItemClickListener!=null){
            hold.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(hold.itemView,position,mDatas.get(position));
                }
            });
            hold.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDatas.get(position).getType().equals("福利")) {
                        mOnItemSimpleListener.onItemClick(hold.iv, position);
                    }
                }
            });
        }
    }

    @Override
    protected BaseRecyclerViewHolder onCreateViewHoldeHolder(ViewGroup parent, int viewType) {
        View view=null;
        if (index==1) {
            view = mInflater.inflate(R.layout.main_item_layout, parent, false);
        }else if (index==4){
            view = mInflater.inflate(R.layout.vedio_item_layout, parent, false);
        }else {
            view = mInflater.inflate(R.layout.main_item_layout, parent, false);
        }
        return new MainHolder(view);
    }

    class MainHolder extends BaseRecyclerViewHolder{
        @BindView(R.id.img)
        ImageView iv;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.typee)
        TextView type;
        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
