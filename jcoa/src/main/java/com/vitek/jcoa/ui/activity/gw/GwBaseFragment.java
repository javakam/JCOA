package com.vitek.jcoa.ui.activity.gw;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.SharePreUtil;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseNetFragment;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.entity.ShowTaskEntity;
import com.vitek.jcoa.ui.adapter.ShowTaskAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Your child must contain layout {@link com.misdk.R.layout.com_ultral_listivew }
 * <p>
 * Created by javakam on 2017/6/2 0002.
 */
public abstract class GwBaseFragment extends GBaseNetFragment<ShowTaskEntity> {
    public static final String APR_POSITION = "position";
    private int position;

    public List<ShowTaskEntity.ModelsBean> mDatas;
    public SharePreUtil spUtil;
    private String[] GW_TYPES = new String[]{};

    @Override
    protected void initData(Bundle savedInstanceState) {
        this.position = getArguments().getInt(APR_POSITION);
        pageSize = 1000;
        mDatas = new ArrayList<>();
        spUtil = new SharePreUtil(mContext, Constant.SPF_USER_INFO);
        GW_TYPES = getGwType();
    }

    protected abstract String[] getGwType();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_gshow_task;
    }

    @Override
    protected void initView(View view, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPtrFrame.setBackgroundColor(getResources().getColor(R.color.white));
        mListView.setDivider(new ColorDrawable(getResources().getColor(R.color.bgColor)));
        mListView.setDividerHeight(1);
        getDialog();
        showDialog();
        initViews(view, inflater, container, savedInstanceState);
    }

    protected abstract void initViews(View view, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    protected void getNetData(int pageSize, boolean mode) {
        //获取公文列表
        //assignee   username
        CloudPlatformUtil.getInstance().getShowTask(
                JCOAApi.getShowTask(spUtil.getValue(Constant.SP_USER_NAME))
                , this);
    }

    @Override
    protected void onNetError(VolleyError error) {
        dismissDialog();
        showShortToast(VolleyErrorManager.getErrorMsg(error));
    }

    @Override
    protected void onNetSuccess(ShowTaskEntity entity) {
        dismissDialog();
        if (entity == null) return;
        if (entity.getModels() == null) return;
        mDatas.clear();
        if (GW_TYPES.length == 1) {
            for (ShowTaskEntity.ModelsBean bean : entity.getModels()) {
                if (bean.getMytask().getName().equals(GW_TYPES[0])) {
                    mDatas.add(bean);
                }
            }
        } else if (GW_TYPES.length == 2) {
            for (ShowTaskEntity.ModelsBean bean : entity.getModels()) {
                if (bean.getMytask().getName().equals(GW_TYPES[0])
                        || bean.getMytask().getName().equals(GW_TYPES[1])) {
                    mDatas.add(bean);
                }
            }
        }

        if (mAdapter == null) {
            mAdapter = new ShowTaskAdapter(mContext, mDatas, mInflater);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.autoSetData(mDatas, hasMore(entity));
        }
    }

    @Override
    protected boolean hasMore(ShowTaskEntity entity) {
        return entity.ispass();
    }
}
