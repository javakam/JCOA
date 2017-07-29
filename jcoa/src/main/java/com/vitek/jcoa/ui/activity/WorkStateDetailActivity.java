package com.vitek.jcoa.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.misdk.base.BaseAppCompatActivity;
import com.vitek.jcoa.R;
import com.vitek.jcoa.net.entity.FindDepartmentUserEntity;
import com.vitek.jcoa.net.entity.StatisticalPersonalEntity;

public class WorkStateDetailActivity extends BaseAppCompatActivity {
    private FrameLayout titleBack;
    private TextView tvTitle;

    private int flag;
    public static final String INTENT_STATEBEAN = "StateModelsBean";
    private StatisticalPersonalEntity.DstateModelBean.StateModelsBean stateModelsBean;//查询权限下用户的工作动态
    private FindDepartmentUserEntity.ModelsBean findDepartUserEntity;//查询某一部门的所有用户/jc_oa/find_department_user (POST))

    @Override
    protected void initVariables() {
        flag = getIntent().getFlags();
        if (flag == 999) {
            findDepartUserEntity =
                    (FindDepartmentUserEntity.ModelsBean) getIntent().getSerializableExtra(INTENT_STATEBEAN);
        } else {
            stateModelsBean =
                    (StatisticalPersonalEntity.DstateModelBean.StateModelsBean)
                            getIntent().getSerializableExtra(INTENT_STATEBEAN);
        }

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_work_state_detail);
        titleBack = (FrameLayout) findViewById(R.id.titleBack);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("工作状态详情");

        TextView tvWorkStateDetail = (TextView) findViewById(R.id.tvWorkStateDetail);

        if (flag == 999) {
            tvWorkStateDetail.setText(findDepartUserEntity.toString());
        } else {
            tvWorkStateDetail.setText(stateModelsBean.toString());
        }
    }
}
