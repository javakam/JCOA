package com.misdk.base;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.misdk.util.ToastUtil;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kam on 2016/6/25.
 */
public abstract class BaseListAdapter<T> extends android.widget.BaseAdapter {
    public Context mContext;
    public ListView mListView;
    public List<T> mList;
    public ConcurrentHashMap<Integer, Boolean> mState;
    public LayoutInflater mInflater;
    public Handler mHandler;

    public BaseListAdapter(Context mContext, List<T> mList, LayoutInflater mInflater) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater = mInflater;
        this.mHandler = getHandler();
    }

    /**
     * 带有CheckBox
     *
     * @param mContext
     * @param mList
     * @param mInflater
     */
    public BaseListAdapter(Context mContext, List<T> mList, ConcurrentHashMap<Integer, Boolean> state, LayoutInflater mInflater) {
        this.mContext = mContext;
        this.mList = mList;
        this.mState = state;
        this.mInflater = mInflater;
        this.mHandler = getHandler();
    }

    /**
     * 用于加入item动画
     *
     * @param mContext
     * @param mListView
     * @param mList
     * @param mInflater
     */
    public BaseListAdapter(Context mContext, ListView mListView, List<T> mList, LayoutInflater mInflater) {
        this.mContext = mContext;
        this.mListView = mListView;
        this.mList = mList;
        this.mInflater = mInflater;
        this.mHandler = getHandler();
    }

    /**
     * 判断集合是否为空
     *
     * @return mList == null || mList.size() == 0
     */
    public boolean isEmpty() {
        return mList == null || mList.size() == 0;
    }

//    /**
//     * 是否有更多数据
//     *
//     * @param lastSize
//     * @return true  hasMore
//     */
//    public boolean hasMore(int lastSize) {
//        mHasMore = lastSize < mList.size();
//        return mHasMore;//mList == null || mHasMore
//    }

    /**
     * 获取集合
     *
     * @return
     */
    public List<T> getList() {
        return this.mList;
    }

    /**
     * 删除旧数据
     */
    public void removeOldData() {
        if (this.mList != null) {
            this.mList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 删除旧数据
     */
    public void removeOldDataNoNotify() {
        if (this.mList != null) {
            this.mList.clear();
        }
    }

    /**
     * 重新给集合填充数据
     *
     * @param mList
     */
    public synchronized void setData(List mList) {
        if (this.mList == null) return;
//        this.mList.clear();
//        notifyDataSetChanged();
        if (mList != null) {
            this.mList = mList;
        }
        notifyDataSetChanged();
    }

    /**
     * 批量添加数据
     *
     * @param mList
     */
    public void addData(List mList) {
        if (mList != null) {
            this.mList.addAll(mList);
            notifyDataSetChanged();
        }
    }

    /**
     * 根据下拉和上推动作，自适应加载数据方案
     *
     * @param mList
     * @param mode  true  下拉刷新  false  加载更多
     */
    public void autoSetData(List mList, boolean mode) {//List<? extends T>
        if (mode) {//下拉刷新
            setData(mList);
        } else {//加载更多
            addData(mList);
        }
    }

    /**
     * 添加一项记录
     *
     * @param item
     */
    public void add(T item) {
        this.mList.add(item);
        notifyDataSetChanged();
    }

    public void addAtFirst(T item) {
        this.mList.add(0, item);
        notifyDataSetChanged();
    }

    /**
     * 移除一项
     *
     * @param position
     */
    public synchronized void remove(int position) {
        if (position < getCount() && position > -1) {
            mList.remove(position);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        if (mList == null || mList.size() == 0) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void onMsgObtain(Message msg) {
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            onMsgObtain(msg);
        }
    };

    /**
     * @return the handler
     */
    public Handler getHandler() {
        return handler;
    }

    public void goIntent(Context context, Class<?> cla) {
        Intent intent = new Intent(context, cla);
        context.startActivity(intent);
    }

    public Intent getIntentNoStart(Context context, Class<?> cla) {
        Intent intent = new Intent(context, cla);
        return intent;
    }

    public void showShortToast(String string) {
        ToastUtil.shortToast(mContext, string);
    }

    public void showLongToast(String string) {
        ToastUtil.longToast(mContext, string);
    }
}
