package com.lynpo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lynpo.viewholder.CommonViewHolder;
import com.lynpo.viewholder.FootViewHolder;
import com.lynpo.viewholder.HeadViewHolder;

import java.util.ArrayList;

/**
 * Created by fujw on 15-12-24.
 *
 * 通用适配器
 */
public abstract class UniversalRclViewAdapter<T> extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_HEAD = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_FOOT = 2;

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<T> mData;

    private int mItemLayoutResId;
    private int mHeadLayoutResId;
    private int mFootLayoutResId;

    private boolean mHasHead = false;
    private boolean mHasFoot = false;

    private boolean mHideFoot = true;

//    private LinearLayout mHeaderLayout;
//    private LinearLayout mFooterLayout;

//    public UniversalRclViewAdapter(Context context, ArrayList<T> data, int layoutResId) {
//        this.mContext = context;
//        this.mInflater = LayoutInflater.from(mContext);
//        this.mItemLayoutResId = layoutResId;
//        if (data != null) {
//            this.mData = data;
//        } else {
//            this.mData = new ArrayList<>();
//        }
//    }

    public UniversalRclViewAdapter(Context context, ArrayList<T> data,
                                   int itemLayoutResId, int headLayoutResId, int footLayoutResId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);

        this.mItemLayoutResId = itemLayoutResId;
        this.mHeadLayoutResId = headLayoutResId;
        this.mFootLayoutResId = footLayoutResId;

        this.mHasHead = true;
        this.mHasFoot = true;

        if (data != null) {
            this.mData = data;
        } else {
            this.mData = new ArrayList<>();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEAD:
                View mHeadView = mInflater.inflate(mHeadLayoutResId, parent, false);
                return new HeadViewHolder(mHeadView);
            case VIEW_TYPE_FOOT:
                View mFootView = mInflater.inflate(mFootLayoutResId, parent, false);
                return new FootViewHolder(mFootView);
            case VIEW_TYPE_ITEM:
                View itemView = LayoutInflater.from(mContext).inflate(mItemLayoutResId, parent, false);
                return new CommonViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_HEAD:
                onHeadBindViewHolder((HeadViewHolder)holder);
                break;
            case VIEW_TYPE_ITEM:
                onItemBindViewHolder((CommonViewHolder) holder, position - getHeaderCount());
                break;
            case VIEW_TYPE_FOOT:
                onFootBindViewHolder((FootViewHolder) holder);
                break;
        }
    }

    protected void onHeadBindViewHolder(HeadViewHolder holder){}

    protected abstract void onItemBindViewHolder(CommonViewHolder holder, int position);

    private void onFootBindViewHolder(FootViewHolder holder){
        if (mHideFoot) {
            holder.itemView.setVisibility(View.GONE);
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return getDataItemCount() + getHeaderCount() + getFooterCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (headShowing() && getHeaderCount() > 0 && position < getHeaderCount()) {
            return VIEW_TYPE_HEAD;
        } else if (position >= getHeaderCount() + getDataItemCount()) {
            return VIEW_TYPE_FOOT;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    /**
     * 获取 position 位置的元素（对象）
     * @param position 给定位置，实际为数据集合 mData 中具体对象的位置
     * @return 对应位置的对象（元素）
     */
    public T getItem(int position) {
        return mData.get(position);
    }

    /**
     * 刷新数据
     * @param data 新数据
     */
    public void refreshData(ArrayList<T> data) {
        ArrayList<T> newData = new ArrayList<>();
        newData.addAll(data);

        this.mData.clear();
        this.mData.addAll(newData);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param data 新数据
     */
    protected void addData(ArrayList<T> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 隐藏头部视图
     */
    public void hideHead() {
        mHasHead = false;
        notifyDataSetChanged();
    }

    /**
     * 显示头部视图
     */
    public void showHead() {
        mHasHead = true;
        notifyDataSetChanged();
    }

    protected boolean headShowing() {
        return mHasHead;
    }
    /**
     * 隐藏尾部视图（如：加载进度）
     */
    public void hideFoot() {
        mHideFoot = true;
        notifyDataSetChanged();
    }

    /**
     * 显示尾部视图
     */
    public void showFoot() {
        mHideFoot = false;
        notifyDataSetChanged();
    }

    // 获取最后一个列表元素位置：即尾部视图上一个位置
    public int getBottomItemPosition() {
        return getItemCount() - 2;
    }

    /**
     * 清除数据
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

//    public void addHeaderView(View headView) {
//        this.mHeadView = headView;
//    }
//
//    public void addFooterView(View footView) {
//        this.mFootView = footView;
//    }
//
//    public void removeHeaderView() {
//        this.mHeadView = null;
//    }
//
//    public void removeFooterView() {
//        this.mFootView = null;
//    }

    // 数据集合元素个数
    private int getDataItemCount() {
        return mData.size();
    }

    // 头部元素个数： 1 个
    private int getHeaderCount() {
        return mHasHead ? 1 : 0;
    }

    // 尾部元素个数： 1 个
    private int getFooterCount() {
        return mHasFoot ? 1: 0;
    }

}
