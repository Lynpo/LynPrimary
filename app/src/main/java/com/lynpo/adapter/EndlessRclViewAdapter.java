package com.lynpo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lynpo.R;
import com.lynpo.viewholder.CommonViewHolder;
import com.lynpo.viewholder.LoadViewHolder;

import java.util.ArrayList;

/**
 * Created by fujw on 16-2-24.
 *
 * RecyclerView's endless adapter
 */
public abstract class EndlessRclViewAdapter<T> extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_LOAD = 0;

    protected Context mContext;
    private int mLayoutResId;
    private ArrayList<T> mData;

    private boolean mLoading = false;

    public EndlessRclViewAdapter(Context context, ArrayList<T> data, int layoutResId) {
        mContext = context;
        mLayoutResId = layoutResId;
        if (data != null) {
            mData = data;
        } else {
            mData = new ArrayList<>();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                View itemView = LayoutInflater.from(mContext).inflate(
                        mLayoutResId, parent, false);
                return new CommonViewHolder(itemView);
            case VIEW_TYPE_LOAD:
                View loadView = LayoutInflater.from(mContext).inflate(
                        R.layout.layout_progress_bar, parent, false);
                return new LoadViewHolder(loadView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            onItemBindViewHolder((CommonViewHolder) holder, position);
        } else if (getItemViewType(position) == VIEW_TYPE_LOAD) {
            if (mLoading) {
                ((LoadViewHolder) holder).mProgressBar.setVisibility(View.VISIBLE);
            } else {
                ((LoadViewHolder) holder).mProgressBar.setVisibility(View.GONE);
            }
        }
    }

    public abstract void onItemBindViewHolder(CommonViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mData.size()) {
            return VIEW_TYPE_ITEM;
        } else {
            return VIEW_TYPE_LOAD;
        }
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public int getBottomItemPosition() {
        return getItemCount() - 2;
    }

    public void refreshData(ArrayList<T> data) {
        ArrayList<T> newData = new ArrayList<>();
        newData.addAll(data);
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    public void loadData() {
        mLoading = true;
        this.notifyDataSetChanged();
    }

    public void stopLoad() {
        mLoading = false;
        this.notifyDataSetChanged();
    }
}
