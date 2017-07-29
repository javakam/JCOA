package com.vitek.jcoa.ui.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.SharePreUtil;
import com.misdk.util.TimeUtil;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseFragment;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.entity.BeanRecycler;
import com.vitek.jcoa.net.entity.UserInfoEntity;
import com.vitek.jcoa.ui.adapter.HomeRecyclerAdapter;
import com.vitek.jcoa.util.UltraUtil;
import com.vitek.jcoa.util.VLogUtil;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 首页
 * <p>
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends GBaseFragment<UserInfoEntity> {

    public static final String ARG_HOMEFRAGMENT = "HomeFragment";
    public static final int TAG_HOMEFRAG = 0x001;
    private TextView tvName, tvDuty, tvData, tvRemind;

    private ArrayList<BeanRecycler> beans;
    private RecyclerView recycler;
    private HomeRecyclerAdapter adapter;

    @Override
    protected void initData(Bundle savedInstanceState) {
        beans = new ArrayList<>();
        beans.add(new BeanRecycler(0, "我的日志", R.mipmap.h1));
        beans.add(new BeanRecycler(1, "工作记录", R.mipmap.h2));
        beans.add(new BeanRecycler(2, "草稿箱", R.mipmap.h3));
        beans.add(new BeanRecycler(3, "退件箱", R.mipmap.h4));
        beans.add(new BeanRecycler(4, "公文审批", R.mipmap.h5));
        beans.add(new BeanRecycler(5, "待办公文", R.mipmap.h6));
        beans.add(new BeanRecycler(6, "审批状态", R.mipmap.h7));
        beans.add(new BeanRecycler(7, "公文归档", R.mipmap.h8));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initPtr(View view) {
//        super.initPtr(view);  //关闭下拉刷新
    }

    @Override
    protected void initView(View view, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.home_ptr_frame_com);
        UltraUtil.initPtr(mContext, mPtrFrame, new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                net();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return super.checkCanDoRefresh(frame, content, header);
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);

            }
        });

        tvName = (TextView) view.findViewById(R.id.tvName);
        tvDuty = (TextView) view.findViewById(R.id.tvDuty);
        tvData = (TextView) view.findViewById(R.id.tvData);
        tvRemind = (TextView) view.findViewById(R.id.tvRemind);

        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(mContext, 4));
        recycler.setItemAnimator(new DefaultItemAnimator());

        adapter = new HomeRecyclerAdapter(mContext, beans);
        recycler.setAdapter(adapter);

        net();//😭😭😭😭😭😭
//        getDialog().setCancelable(false);
//        showDialog();
    }

    @Override
    protected void getNetData() {
        //4.查询用户信息/jc_oa/my_info (POST))            UserInfoEntity    OK 2017-05-21
        CloudPlatformUtil.getInstance().getUserInfo(JCOAApi.getUserInfoMap(), this);
    }


    @Override
    protected void onNetError(VolleyError error) {
        dismissDialog();
        VLogUtil.e("HomeFragment --- " + VolleyErrorManager.getErrorMsg(error));
    }

    @Override
    protected void onNetSuccess(UserInfoEntity entity) {
        getHandler().sendMessage(getHandler().obtainMessage(TAG_HOMEFRAG, entity));
    }

    @Override
    public void onMsgObtain(Message msg) {
        super.onMsgObtain(msg);
        if (msg.what == TAG_HOMEFRAG) {
            //用户信息
            /**
             * id : 120
             * username : weixiao
             * password : password
             * realname : wei    * departmentid : 8
             * job : 行政主任
             * job_type :
             * phone : null
             * rtime : 1493887395000
             * department : 行政主任
             * role_name : null
             * powerid : 2
             * departments : null
             * date : null
             * pdepartment : null
             * actiontime : null
             * endtime : null
             * lusername : null
             * log_actiontime : null
             * log_endtime : null
             */
            UserInfoEntity entity = (UserInfoEntity) msg.obj;
            UserInfoEntity.UserExtendsBean bean = entity.getUserExtends().get(0);
            tvName.setText(bean.getUsername());
            tvDuty.setText(bean.getJob());
            tvData.setText(TimeUtil.longToDate7(bean.getRtime()));
            SharePreUtil sharePreUtil = new SharePreUtil(getActivity(), Constant.SPF_USER_INFO);
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
        }
    }
}
