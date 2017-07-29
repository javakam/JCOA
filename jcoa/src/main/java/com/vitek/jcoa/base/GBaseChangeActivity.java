package com.vitek.jcoa.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.net.ResponseListener;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.SharePreUtil;
import com.misdk.util.TimeUtil;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.R;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.entity.UpdateUserEntity;
import com.vitek.jcoa.net.entity.UserInfoEntity;
import com.vitek.jcoa.util.VLogUtil;

import java.util.Map;

/**
 * 改密码、改手机、改真名 的基类
 * <p>
 * Created by javakam on 2017/6/7 0007.
 */
public abstract class GBaseChangeActivity extends GBaseAppCompatActivity<UpdateUserEntity> {
    protected final String[] ACTION_NAME = new String[]{"修改密码", "修改手机", "修改真实姓名"};
    //Title
    private FrameLayout titleBack;
    private TextView tvTitle, tvTitleNowday;
    protected Button btConfirm;
    protected SharePreUtil sharePreUtil;

    protected abstract String getTileStr();

    @Override
    protected void initVariables() {
        sharePreUtil = new SharePreUtil(this, Constant.SPF_USER_INFO);
    }

    @Override
    protected void initPtr() {
        //关闭下拉刷新
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
        tvTitle.setText(getTileStr());
        tvTitleNowday = (TextView) findViewById(R.id.tvTitleNowday);
        tvTitleNowday.setText(TimeUtil.getTitleDate());
        btConfirm = (Button) findViewById(R.id.btConfirm);
        iniView();
    }

    protected abstract void iniView();

    protected abstract String getParams();

    @Override
    protected void getNetData() {
        //更新用户信息/jc_oa/up_user (POST))
        Map<String, String> params = null;
        if (getTileStr().equals(ACTION_NAME[0])) {
            params = JCOAApi.getUpUserMap(
                    sharePreUtil.getValue(Constant.SP_USER_ID),
                    sharePreUtil.getValue(Constant.SP_USER_NAME),
                    getParams(),
                    sharePreUtil.getValue(Constant.SP_USER_REALNAME),
                    sharePreUtil.getValue(Constant.SP_USER_DEPARTMENTID),
                    sharePreUtil.getValue(Constant.SP_USER_JOB),
                    sharePreUtil.getValue(Constant.SP_USER_JOBTYPE),
                    sharePreUtil.getValue(Constant.SP_USER_PHONE));

        } else if (getTileStr().equals(ACTION_NAME[1])) {
            params = JCOAApi.getUpUserMap(
                    sharePreUtil.getValue(Constant.SP_USER_ID),
                    sharePreUtil.getValue(Constant.SP_USER_NAME),
                    sharePreUtil.getValue(Constant.SP_USER_PWD),
                    sharePreUtil.getValue(Constant.SP_USER_REALNAME),
                    sharePreUtil.getValue(Constant.SP_USER_DEPARTMENTID),
                    sharePreUtil.getValue(Constant.SP_USER_JOB),
                    sharePreUtil.getValue(Constant.SP_USER_JOBTYPE),
                    getParams());
        } else if (getTileStr().equals(ACTION_NAME[2])) {
            params = JCOAApi.getUpUserMap(
                    sharePreUtil.getValue(Constant.SP_USER_ID),
                    sharePreUtil.getValue(Constant.SP_USER_NAME),
                    sharePreUtil.getValue(Constant.SP_USER_PWD),
                    getParams(),
                    sharePreUtil.getValue(Constant.SP_USER_DEPARTMENTID),
                    sharePreUtil.getValue(Constant.SP_USER_JOB),
                    sharePreUtil.getValue(Constant.SP_USER_JOBTYPE),
                    sharePreUtil.getValue(Constant.SP_USER_PHONE));
        }
        CloudPlatformUtil.getInstance().updateUserInfo(params, this);
    }

    @Override
    protected void onNetError(VolleyError error) {
        VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
        dismissDialog();
    }

    @Override
    protected void onNetSuccess(UpdateUserEntity response) {
        showShortToast(response.getDefaultMessage());
        if (response.ispass()) {
            //4.查询用户信息/jc_oa/my_info (POST))            UserInfoEntity    OK 2017-05-21
            CloudPlatformUtil.getInstance().getUserInfo(JCOAApi.getUserInfoMap()
                    , new ResponseListener<UserInfoEntity>() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
                            dismissDialog();
                        }

                        @Override
                        public void onResponse(UserInfoEntity response) {
                            UserInfoEntity.UserExtendsBean bean = response.getUserExtends().get(0);
                            SharePreUtil sharePreUtil = new SharePreUtil(GBaseChangeActivity.this, Constant.SPF_USER_INFO);
                            sharePreUtil.setValue(Constant.SP_USER_ID, bean.getId() + "");
                            sharePreUtil.setValue(Constant.SP_USER_NAME, bean.getUsername() + "");
                            sharePreUtil.setValue(Constant.SP_USER_PWD, bean.getPassword() + "");
                            sharePreUtil.setValue(Constant.SP_USER_REALNAME, bean.getRealname() + "");
                            sharePreUtil.setValue(Constant.SP_USER_DEPARTMENTID, bean.getDepartmentid() + "");
                            sharePreUtil.setValue(Constant.SP_USER_JOB, bean.getJob() + "");
                            sharePreUtil.setValue(Constant.SP_USER_JOBTYPE, bean.getJob_type() + "");
                            sharePreUtil.setValue(Constant.SP_USER_PHONE, bean.getPhone() + "");
                            sharePreUtil.setValue("rtime", bean.getRtime() + "");
                            sharePreUtil.setValue(Constant.SP_USER_DEPARTMENT, bean.getDepartment() + "");
                            sharePreUtil.setValue("role_name", bean.getRole_name() + "");
                            sharePreUtil.setValue(Constant.SP_USER_POWERID, bean.getPowerid() + "");
                            sharePreUtil.setValue(Constant.SP_USER_DEPARTMENTS, bean.getDepartments() + "");
                            sharePreUtil.setValue("date", bean.getDate() + "");
                            sharePreUtil.setValue("pdepartment", bean.getPdepartment() + "");
                            sharePreUtil.setValue("actiontime", bean.getActiontime() + "");
                            sharePreUtil.setValue("endtime", bean.getEndtime() + "");
                            sharePreUtil.setValue("lusername", bean.getLusername() + "");
                            sharePreUtil.setValue("log_actiontime", bean.getLog_actiontime() + "");
                            sharePreUtil.setValue("log_endtime", bean.getLog_endtime() + "");
                            sharePreUtil.commit();
                            dismissDialog();
                            finish();
                        }
                    });
        } else {
            dismissDialog();
        }
    }
}