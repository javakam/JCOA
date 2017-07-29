package com.vitek.jcoa.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.base.BaseAppCompatActivity;
import com.misdk.net.ResponseListener;
import com.misdk.net.VolleyErrorManager;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.JCOAApplication;
import com.vitek.jcoa.R;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.entity.FindIsPublishedEntity;
import com.vitek.jcoa.net.entity.FindReturnLogEntity;
import com.vitek.jcoa.net.entity.FindlogEntity;
import com.vitek.jcoa.net.entity.ReturnLogEntity;
import com.vitek.jcoa.util.VLogUtil;

public class DairyDetailActivity extends BaseAppCompatActivity implements ResponseListener<ReturnLogEntity> {
    public static final String INTENT_CONTENT = "content";
    private FrameLayout titleBack;
    private TextView tvTitle;
    private TextView tvState, tvDairyContent;
    private Button btReturnBack;

    private int flag;
    private FindlogEntity.LogExtendsBean beanFindLog;//工作记录
    private FindIsPublishedEntity.ModelsBean beanDraft;//草稿箱
    private FindReturnLogEntity.ModelsBean beanReturn;//退件箱
    private String workState;
    private String content;

    @Override
    protected void initVariables() {
        flag = getIntent().getFlags();
        Object object = getIntent().getSerializableExtra(INTENT_CONTENT);
        if (flag == Constant.FLAG_WORK_RECORD) {//工作记录
            beanFindLog = (FindlogEntity.LogExtendsBean) object;
            workState = beanFindLog.getWorkstatus();
            content = beanFindLog.getContent();
        } else if (flag == Constant.FLAG_DRFT) {//草稿箱
            beanDraft = (FindIsPublishedEntity.ModelsBean) object;
            workState = beanDraft.getWorkstatus();
            content = beanDraft.getContent();
        } else if (flag == Constant.FLAG_DRFT_RETURN) {//退件箱
            beanReturn = (FindReturnLogEntity.ModelsBean) object;
            workState = beanReturn.getWorkstatus();
            content = beanReturn.getContent();
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dairy_detail);
        titleBack = (FrameLayout) findViewById(R.id.titleBack);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("内容详情");
        tvState = (TextView) findViewById(R.id.tvState);
        tvDairyContent = (TextView) findViewById(R.id.tvDairyContent);
        btReturnBack = (Button) findViewById(R.id.btReturnBack);

        tvState.setText(workState);
        tvDairyContent.setText(content);
        if (flag == Constant.FLAG_WORK_RECORD) {//工作记录  beanFindLog
            btReturnBack.setVisibility(View.VISIBLE);
            btReturnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().setCanceledOnTouchOutside(false);
                    showDialog();
                    VLogUtil.w("上传实体数据 --- " + beanFindLog.toString());
                    //退回日志   beanFindLog  失误： ispublished   3  固定值 ！！！！！！
                    // TODO: 2017/6/13 0013  6.14 不能退回
                    CloudPlatformUtil.getInstance().returnLog(
                            JCOAApi.getReturnLogMap(beanFindLog.getId() + "", beanFindLog.getDate() + "", beanFindLog.getUsername()
                                    , beanFindLog.getContent(), Constant.INT_RETURNLOG + "", beanFindLog.getWorkstatus())
                            , DairyDetailActivity.this);
                }
            });
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        VLogUtil.e("日志退回失败   " + VolleyErrorManager.getErrorMsg(error));
        dismissDialog();
    }

    @Override
    public void onResponse(ReturnLogEntity response) {
        if (response == null) return;
        dismissDialog();
        JCOAApplication.getInstance().notifyDataChange(JCOAApplication.UPDATE_LOG, null, null);//刷新工作记录页面
        finish();
    }
}
