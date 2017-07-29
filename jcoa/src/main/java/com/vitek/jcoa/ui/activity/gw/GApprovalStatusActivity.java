package com.vitek.jcoa.ui.activity.gw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.util.TimeUtil;
import com.misdk.views.pagersliding.CreatePagerSlidingTabStrip;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseAppCompatActivity;
import com.vitek.jcoa.ui.fragment.GAlreadyFileFragment;
import com.vitek.jcoa.ui.fragment.GNoHandleFragment;

/**
 * 审批状态
 */
public class GApprovalStatusActivity extends GBaseAppCompatActivity {
    public static final String ACTIVITY_NAME_APPROVAL="GApprovalStatusActivity";
    private FrameLayout titleBack;
    private TextView tvTitle, tvTitleNowday;

    private final String[] titles = {"已归档的公文", "未归档的公文"};
    private Fragment[] fragments = {
            GAlreadyFileFragment.newInstance(0, new String[]{Constant.STR_GW_TYPE_GD}),
            GNoHandleFragment.newInstance(1,new String[]{Constant.STR_GW_TYPE_ZH, Constant.STR_GW_TYPE_FD})};

    @Override
    protected void initVariables() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gshow;
    }

    @Override
    protected void initPtr() {
//        super.initPtr();
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        titleBack = (FrameLayout) findViewById(R.id.titleBack);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("审批状态");
        tvTitleNowday = (TextView) findViewById(R.id.tvTitleNowday);
        tvTitleNowday.setText(TimeUtil.getTitleDate());

        CreatePagerSlidingTabStrip createPagerSlidingTabStrip
                = new CreatePagerSlidingTabStrip(this, titles, fragments);
    }

    @Override
    protected void getNetData() {

    }

    @Override
    protected void onNetError(VolleyError error) {

    }

    @Override
    protected void onNetSuccess(Object response) {

    }

}
