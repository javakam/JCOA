package com.vitek.jcoa.ui.activity.gw;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.SharePreUtil;
import com.misdk.util.TimeUtil;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseNetAppCompatActivity;
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
public abstract class GwBaseActivity extends GBaseNetAppCompatActivity<ShowTaskEntity> {
    private FrameLayout titleBack;
    private TextView tvTitle, tvTitleNowday;

    public List<ShowTaskEntity.ModelsBean> mDatas;
    public SharePreUtil spUtil;
    private String[] GW_TYPES = new String[]{};

    @Override
    protected void initVariables() {
        pageSize = 1000;
        mDatas = new ArrayList<>();
        spUtil = new SharePreUtil(this, Constant.SPF_USER_INFO);
        GW_TYPES = getGwType();
    }

    protected abstract String[] getGwType();

    protected abstract String getTitleName();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gshow_task;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        mPtrFrame.setBackgroundColor(getResources().getColor(R.color.white));
        mListView.setDivider(new ColorDrawable(getResources().getColor(R.color.bgColor)));
        mListView.setDividerHeight(1);

        titleBack = (FrameLayout) findViewById(R.id.titleBack);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getTitleName());
        tvTitleNowday = (TextView) findViewById(R.id.tvTitleNowday);
        tvTitleNowday.setText(TimeUtil.getTitleDate());
        getDialog();
        showDialog();
        initView(savedInstanceState);
    }

    protected abstract void initView(Bundle savedInstanceState);

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
            mAdapter = new ShowTaskAdapter(this, mDatas, mInflater);
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