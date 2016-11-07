package com.tian.gankgirl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nafuture on 2016/7/14.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    protected Context mContext;
    protected LayoutInflater mInflater = null;
    protected List<T> mDatas;
    protected Picasso mPicasso;

    public OnItemClickListener<T> mOnItemClickListener;
    public OnItemSimpleListener<T> mOnItemSimpleListener;

    public BaseRecyclerViewAdapter(Context context, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mPicasso = Picasso.with(context);
        setDatas(datas);
    }

    public void setDatas(List<T> datas) {
        if (datas != null) {
            mDatas = datas;
        } else {
            mDatas = new ArrayList<T>();
        }
        notifyDataSetChanged();
    }

    public void addItemLast(List<T> datas) {
        mDatas.addAll(datas);
    }

    public void addItemTop(List<T> datas) {
        mDatas = datas;
    }

    public void insertItem(T t, int position) {
        mDatas.add(position, t);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mDatas.remove(position);
        notifyItemChanged(position);
    }

    public void removeAll() {
        mDatas.clear();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mDatas.size() > 0) {
            count = mDatas.size();
        }
        return count;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        bindViewHodler(holder, position);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHoldeHolder(parent, viewType);
    }

    protected abstract void bindViewHodler(BaseRecyclerViewHolder holder, int position);

    /***
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract BaseRecyclerViewHolder onCreateViewHoldeHolder(ViewGroup parent, int viewType);


    /**
     * 点击事件接口
     * @param <T>
     */
    public interface OnItemClickListener<T> {

        void onItemClick(View view, int position, T mode);

        void onItemLongClick(View view, int position, T mode);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        if (listener != null) {
            this.mOnItemClickListener = listener;
        }
    }
    public interface OnItemSimpleListener<T>{
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setmOnItemSimpleListener(OnItemSimpleListener<T> mOnItemSimpleListener) {
        if (mOnItemSimpleListener != null) {
            this.mOnItemSimpleListener = mOnItemSimpleListener;
        }
    }
}
