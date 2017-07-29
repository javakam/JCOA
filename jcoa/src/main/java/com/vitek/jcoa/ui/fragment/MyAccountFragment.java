package com.vitek.jcoa.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.net.ResponseListener;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.SharePreUtil;
import com.misdk.util.TimeUtil;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseFragment;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.entity.LoginOutEntity;
import com.vitek.jcoa.net.entity.UpdateUserEntity;
import com.vitek.jcoa.ui.activity.ChangePhoneNum;
import com.vitek.jcoa.ui.activity.ChangePwdActivity;
import com.vitek.jcoa.ui.activity.ChangeTrueNameActivity;
import com.vitek.jcoa.util.VLogUtil;

/**
 * updateUserInfo
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends GBaseFragment<UpdateUserEntity> implements View.OnClickListener {
    public static final String ARG_MYACCOUNT = "MyAccountFragment";

    //Title
    private FrameLayout titleBack;
    private TextView tvTitle, tvTitleNowday;

    private ImageView imgHeader;
    private TextView tvName;
    private TextView tvDuty;
    private RelativeLayout relaChangePwd, relaChangePhone, relaChangeTrueName, relaChangeKefuNum,relaUpdate;
    private SharePreUtil sharePreUtil;

    @Override
    protected void initData(Bundle savedInstanceState) {
        sharePreUtil = new SharePreUtil(getActivity(), Constant.SPF_USER_INFO);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_account;
    }

    @Override
    protected void initPtr(View view) {
//        super.initPtr(view);
    }

    @Override
    protected void initView(View view, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        titleBack = (FrameLayout) view.findViewById(R.id.titleBack);
        titleBack.setVisibility(View.GONE);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText("我的账号");
        tvTitleNowday = (TextView) view.findViewById(R.id.tvTitleNowday);
        tvTitleNowday.setText(TimeUtil.getTitleDate());

        imgHeader = (ImageView) view.findViewById(R.id.imgHeader);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvDuty = (TextView) view.findViewById(R.id.tvDuty);
        relaChangePwd = (RelativeLayout) view.findViewById(R.id.relaChangePwd);
        relaChangePhone = (RelativeLayout) view.findViewById(R.id.relaChangePhone);
        relaChangeTrueName = (RelativeLayout) view.findViewById(R.id.relaChangeTrueName);
        relaChangeKefuNum = (RelativeLayout) view.findViewById(R.id.relaChangeKefuNum);
        relaUpdate = (RelativeLayout) view.findViewById(R.id.relaUpdate);

        relaChangePwd.setOnClickListener(this);
        relaChangePhone.setOnClickListener(this);
        relaChangeTrueName.setOnClickListener(this);
        relaChangeKefuNum.setOnClickListener(this);
        view.findViewById(R.id.btExitLogin).setOnClickListener(this);


        tvName.setText(sharePreUtil.getValue(Constant.SP_USER_NAME));
        tvDuty.setText(sharePreUtil.getValue(Constant.SP_USER_JOB));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void getNetData() {
        //更新用户信息/jc_oa/up_user (POST))          ok    2017-06-01
//        CloudPlatformUtil.getInstance().updateUserInfo(
//                JCOAApi.getUpUserMap(sharePreUtil.getValue(Constant.SP_USER_ID) + "",
//                        sharePreUtil.getValue(Constant.SP_USER_NAME),
//                        sharePreUtil.getValue(Constant.SP_USER_PWD),
//                        sharePreUtil.getValue(Constant.SP_USER_REALNAME),
//                        sharePreUtil.getValue(Constant.SP_USER_DEPARTMENTID),
//                        sharePreUtil.getValue(Constant.SP_USER_JOB),
//                        sharePreUtil.getValue(Constant.SP_USER_JOBTYPE)
//                ), this);
    }

    @Override
    protected void onNetError(VolleyError error) {
        VLogUtil.e("更新用户信息失败" + getClass().getSimpleName());
    }

    @Override
    protected void onNetSuccess(UpdateUserEntity entity) {
        VLogUtil.i(entity.toString());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relaChangePwd:
                Intent intent1 = new Intent(mContext, ChangePwdActivity.class);
                startActivity(intent1);
                break;
            case R.id.relaChangePhone:
                Intent intent2 = new Intent(mContext, ChangePhoneNum.class);
                startActivity(intent2);
                break;
            case R.id.relaChangeTrueName:
                Intent intent3 = new Intent(mContext, ChangeTrueNameActivity.class);
                startActivity(intent3);
                break;
            case R.id.relaChangeKefuNum:
                //跳转到手机通讯录 phone
//                Intent intent4 = new Intent(mContext, ChangeTrueNameActivity.class);
//                startActivity(intent4);


                break;
            case R.id.relaUpdate:
                //弹出 提示最新版本的Dialog

                break;
            case R.id.btExitLogin:
                //3.退出登录/jc_oa/login_out (POST))-----------   LoginOutEntity    OK 2017-05-21
                CloudPlatformUtil.getInstance().loginOut(JCOAApi.getLoginOutMap()
                        , new ResponseListener<LoginOutEntity>() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
                            }

                            @Override
                            public void onResponse(LoginOutEntity response) {
                                //注销   调用此法
//                                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//
//                                    @Override
//                                    public void run() {
//                                        // clear sp
//                                        Intent intent = new Intent();
//                                        intent.setClass(mContext, LoginActivity.class);
//                                        startActivity(intent);
//                                    }
//                                }));
//                                System.exit(0);
                                getActivity().finish();

                            }
                        });
                break;
        }
    }
}