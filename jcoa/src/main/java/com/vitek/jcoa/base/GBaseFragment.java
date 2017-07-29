package com.vitek.jcoa.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.misdk.base.BaseFragment;
import com.misdk.net.ResponseListener;
import com.misdk.util.NetUtil;
import com.vitek.jcoa.R;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.util.UltraUtil;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * UltralPtr + Volley
 * <p>
 * 1. if you use ptr , your xml layout must have 'PtrClassicFrameLayout' ;
 * 2. if you don't use ptr , just override initPtr() before findView() and comment out its super(...) .
 * Created by kam on 2017/5/18.
 */
public abstract class GBaseFragment<T> extends BaseFragment implements ResponseListener<T> {
    public PtrClassicFrameLayout mPtrFrame;
    public CloudPlatformUtil mCloud;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCloud = CloudPlatformUtil.getInstance();
        initData(savedInstanceState);
    }

    protected abstract void initData(Bundle savedInstanceState);

    /**
     * your view must contains {@see R.layout.com_ultral_listivew}
     *
     * @return
     */
    protected abstract int getLayoutResId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        initPtr(view);
        initView(view, inflater, container, savedInstanceState);
        return view;
    }

    /**
     * Init pull to refresh
     *
     * @param view
     */
    protected void initPtr(View view) {
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame_com);
        UltraUtil.initPtr(mContext, mPtrFrame, ptrHandler);
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


    protected abstract void initView(View view, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void getNetData();

    protected abstract void onNetError(VolleyError error);

    protected abstract void onNetSuccess(T response);

    protected void net() {
        if (!NetUtil.isAvailable(mContext)) {
            showShortToast("客户端没有网络连接");
            if (mPtrFrame != null) {
                mPtrFrame.refreshComplete();
            }
        } else {
            getNetData();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (mPtrFrame != null) {
            mPtrFrame.refreshComplete();
        }
        int errorCode = 0;
        String errorMessage = "加载失败，点击加载更多";
        if (error == null) return;
        onNetError(error);
    }

    @Override
    public void onResponse(T response) {
        if (mPtrFrame != null) {
            mPtrFrame.refreshComplete();
        }
        if (response == null) return;
        onNetSuccess(response);
        //  {code='1001', msg='没有查询到数据', data=null}
    }


    private PtrHandler ptrHandler = new PtrHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            net();
        }

        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        }
    };

}
