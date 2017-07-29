package com.vitek.jcoa.ui.activity.gw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.util.TimeUtil;
import com.misdk.views.pagersliding.CreatePagerSlidingTabStrip;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseAppCompatActivity;
import com.vitek.jcoa.ui.fragment.GAlreadyHandleFragment;
import com.vitek.jcoa.ui.fragment.GNoHandleFragment;

/**
 * 公文审批
 */
public class GDocumentApprovalActivity extends GBaseAppCompatActivity {
    private FrameLayout titleBack;
    private TextView tvTitle, tvTitleNowday;
    private FrameLayout titleRight;
    private ImageView img_title_right;

    private final String[] titles = {"未处理的公文", "已处理的公文"};
    private Fragment[] fragments = {
            GNoHandleFragment.newInstance(0,new String[]{Constant.STR_GW_TYPE_ZH})
            , GAlreadyHandleFragment.newInstance(1, new String[]{Constant.STR_GW_TYPE_FD, Constant.STR_GW_TYPE_GD})};

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
        tvTitle.setText("公文审批");
        titleRight = (FrameLayout) findViewById(R.id.titleRight);
        titleRight.setVisibility(View.VISIBLE);
        img_title_right = (ImageView) findViewById(R.id.img_title_right);
        img_title_right.setImageDrawable(getResources().getDrawable(R.drawable.new_gw));
        titleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GDocumentApprovalActivity.this, GStartFlowActivity.class);
                startActivity(intent);
            }
        });
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