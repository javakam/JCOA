package com.vitek.jcoa.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.misdk.base.BaseAppCompatActivity;
import com.misdk.base.BaseListAdapter;
import com.misdk.cube.loadmore.LoadMoreContainer;
import com.misdk.cube.loadmore.LoadMoreHandler;
import com.misdk.cube.loadmore.LoadMoreListViewContainer;
import com.misdk.net.ResponseListener;
import com.misdk.util.NetUtil;
import com.vitek.jcoa.R;
import com.vitek.jcoa.util.UltraUtil;

import java.util.concurrent.ConcurrentHashMap;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 下拉刷新+联网 基类
 * your view must contains  'R.layout.com_ultral_listivew'
 * Created by kam on 2016/6/14.
 */
public abstract class GBaseNetAppCompatActivity<T> extends BaseAppCompatActivity implements ResponseListener<T> {

    public PtrClassicFrameLayout mPtrFrame;
    public LoadMoreListViewContainer mLoadMoreContainer;
    public ListView mListView;

    public int pageSize = 6;
    public boolean mode = true;//default true , pull to refresh
    public BaseListAdapter mAdapter;
    public ConcurrentHashMap<Integer, Boolean> state = new ConcurrentHashMap<>();

    /**
     * your view must contains {@see R.layout.com_ultral_listivew}
     *
     * @return
     */
    protected abstract int getLayoutResId();

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(getLayoutResId());
        initPtr();
        findViews(savedInstanceState);
    }

    protected void initPtr()  {
            mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame_com);
            UltraUtil.initPtr(this, mPtrFrame, ptrHandler);
            mListView = (ListView) findViewById(R.id.lv_cus_com);
            // load more container
            mLoadMoreContainer = (LoadMoreListViewContainer)
                    findViewById(R.id.load_more_com);
            UltraUtil.initLm(mLoadMoreContainer, loadMoreHandler);
    }

    /**
     * 进入页面，自动刷新
     */
    protected void autoLoad(int delay) {
        // auto load data
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(false);
            }
        }, delay);
    }

    protected void updateData() {
        net(pageSize, true);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    protected abstract void findViews(Bundle savedInstanceState);

    protected abstract void getNetData(int pageSize, boolean mode);

    protected abstract void onNetError(VolleyError error);

    protected abstract void onNetSuccess(T response);

    protected void net(int pageSize, boolean mode) {
        this.mode = mode;
        if (!NetUtil.isAvailable(this)) {
            showShortToast("客户端没有网络连接");
            if (mPtrFrame != null) {
                mPtrFrame.refreshComplete();
            }
        } else {
            if (state.size() > 0) state.clear();
            getNetData(pageSize, mode);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mPtrFrame.refreshComplete();
//        int errorCode = 0;
//        String errorMessage = "加载失败，点击加载更多";
        mLoadMoreContainer.loadMoreError(0, error.getMessage());
        if (error == null) return;
        onNetError(error);
    }

    @Override
    public void onResponse(T response) {
        mPtrFrame.refreshComplete();
        if (response == null) return;
        onNetSuccess(response);
        if (mAdapter != null) {
            mLoadMoreContainer.loadMoreFinish(mAdapter.isEmpty(), hasMore(response));//hasMore
        }
    }

    protected abstract boolean hasMore(T response);

    private PtrHandler ptrHandler = new PtrHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            loadRefreshData(frame);
        }

        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
        }
    };

    private LoadMoreHandler loadMoreHandler = new LoadMoreHandler() {
        @Override
        public void onLoadMore(LoadMoreContainer loadMoreContainer) {
            loadMoreData(loadMoreContainer);
        }
    };

    private void loadRefreshData(PtrFrameLayout frame) {
        mode = true;  // 刷新
        if (mAdapter != null) {
            mAdapter.removeOldData();//
        }
        net(pageSize, mode);
    }

    private void loadMoreData(LoadMoreContainer loadMoreContainer) {
        mode = false;
        net(pageSize, mode);
    }

}
