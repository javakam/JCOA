package com.vitek.jcoa.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.misdk.net.ResponseListener;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.SharePreUtil;
import com.misdk.util.StringUtils;
import com.misdk.util.TimeUtil;
import com.misdk.util.ToastUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseNetFragment;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.entity.DepartmentEntity;
import com.vitek.jcoa.net.entity.FindDepartmentUserEntity;
import com.vitek.jcoa.net.entity.GetleveUserEntity;
import com.vitek.jcoa.net.entity.JobEntity;
import com.vitek.jcoa.net.entity.StatisticalPersonalEntity;
import com.vitek.jcoa.ui.activity.JobSummaryActivity;
import com.vitek.jcoa.ui.adapter.DepartmentUsersAdapter;
import com.vitek.jcoa.ui.adapter.WorkTrendsAdapter;
import com.vitek.jcoa.ui.view.MyCalendarView;
import com.vitek.jcoa.util.VLogUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.vitek.jcoa.Constant.STR_DEPARTMENTS;

/**
 * 工作动态
 * <p>
 * 默认使用接口：“查询权限下用户的工作动态/jc_oa/Statistical_personal.action(POST))”
 * 查询当前账号权限下所有用户的信息    三个Spinner 1.工作状态，跟新Adapter 3 职务更新Adapter
 * <p>
 * 接口调用 findDepartmentUser 实体： FindDepartmentUserEntity
 * A simple {@link Fragment} subclass.
 */
public class WorkStateFragment extends GBaseNetFragment<StatisticalPersonalEntity> {
    public static final String ARG_WORKSTATE = "WorkStateFragment";
    //区别：0普通用户（科员）只能查询自己写的日志，1 科长、副大队长可以查看分管部门所有人的，2 大队长、行政主管、管理员可以看全部的
    //工作动态汇总的是  科长及以上级别  的人员日志
    private int USER_RANGE = 0;
    private FrameLayout titleBack;
    private TextView tvTitle, tvTitleNowday;
    private ImageView imgJobSummary;//负责人动态汇总

    private RelativeLayout relaStartTime, relaEndTime;//开始时间&结束时间
    private TextView tvStartTime, tvEndTime;//日期
    private MyCalendarView myCalendarView;//日历控件
    private String startTime = TimeUtil.getFirstDayOfThisMonth();
    private String endTime = TimeUtil.getStringDateyyyyMMdd2();
    private boolean isStart = true;
    //查询权限下用户信息/jc_oa/get_leve_user (POST))
    private List<GetleveUserEntity.UserExtendsBean> leveUserExtends = new ArrayList<>();
    //工作动态、单位办公、科室、职务
    private MaterialSpinner spinnerWorkState, spinnerRoom, spinnerJob;
    //    private List<DepartmentEntity.ModelsBean> rooms = new ArrayList<>();
//    private List<JobEntity.ModelsBean> jobs = new ArrayList<>();
//部门和职务 应该在 login的时候就调用。。 后期改动
    private List<DepartmentEntity.ModelsBean> departments = new ArrayList<>();
    private List<JobEntity.ModelsBean> jobs = new ArrayList<>();
    private ArrayList<String> roomsArrayList = new ArrayList<>();
    private ArrayList<String> jobsArrayList = new ArrayList<>();

    private List<StatisticalPersonalEntity.DstateModelBean.StateModelsBean> datas;
    private List<FindDepartmentUserEntity.ModelsBean> findDepartUserEntitys;


    private TextView tvZGRS, tvYDRS, tvDWBG, tvQT;
    private SharePreUtil sharePreUtil;
    private String userNameLong = "";
    private String[] stringsNull = new String[]{""};

    @Override
    protected void initData(Bundle savedInstanceState) {
//        rooms = DBOperation.getInstance().getDepartmentList();
//        jobs = DBOperation.getInstance().getJobList();
        roomsArrayList.add(0, "科室");
        jobsArrayList.add(0, "职务");
        // TODO: 2017/5/31 0031  将问题总结成文档  发到群里
        sharePreUtil = new SharePreUtil(mContext, Constant.SPF_USER_INFO);
        String jobName = sharePreUtil.getValue(Constant.SP_USER_JOB);
        if (jobName.equals(Constant.STR_JOBS[3])) {//科员
            USER_RANGE = 0;
        } else if (jobName.equals(Constant.STR_JOBS[1]) || jobName.equals("科长")) {//副队长  +  科长
            USER_RANGE = 1;
        } else if (jobName.equals(Constant.STR_JOBS[0])
                || jobName.equals(Constant.STR_JOBS[2]) || jobName.equals("管理员")) {//大队长 + 行政主任
            USER_RANGE = 2;
        }
        userNameLong = sharePreUtil.getValue(Constant.SP_USER_NAME);
        pageSize = 10000;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_workstate;
    }

    @Override
    protected void initView(View view, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPtrFrame.setBackgroundColor(getResources().getColor(R.color.white));
        titleBack = (FrameLayout) view.findViewById(R.id.titleBack);
        titleBack.setVisibility(View.GONE);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText("工作动态");
        tvTitleNowday = (TextView) view.findViewById(R.id.tvTitleNowday);
        tvTitleNowday.setText(TimeUtil.getTitleDate());

        relaStartTime = (RelativeLayout) view.findViewById(R.id.relaStartTime);
        relaEndTime = (RelativeLayout) view.findViewById(R.id.relaEndTime);
        tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);
        tvEndTime = (TextView) view.findViewById(R.id.tvEndTime);
        tvStartTime.setText(startTime);
        tvEndTime.setText(endTime);
        myCalendarView = new MyCalendarView(mContext);
        myCalendarView.initCalendarView(2017, 0, 1);
        relaStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = true;
                myCalendarView.showCalendarViewAsDropDown(v);
            }
        });
        relaEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = false;
                myCalendarView.showCalendarViewAsDropDown(v);
            }
        });
        myCalendarView.materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                clickDate(date.getDate(), isStart);
            }
        });
        imgJobSummary = (ImageView) view.findViewById(R.id.img_title_right);
        FrameLayout frameTitleRight = (FrameLayout) imgJobSummary.getParent();
        frameTitleRight.setVisibility(View.VISIBLE);
        frameTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JobSummaryActivity.class));
            }
        });
        spinnerWorkState = (MaterialSpinner) view.findViewById(R.id.spinnerWorkState);
        spinnerWorkState.setItems(Constant.STR_WORK_STATE);
        spinnerRoom = (MaterialSpinner) view.findViewById(R.id.spinnerRoom);
        spinnerJob = (MaterialSpinner) view.findViewById(R.id.spinnerJob);
        spinnerJob.setVisibility(View.GONE);
        tvZGRS = (TextView) view.findViewById(R.id.tvZGRS);
        getDialog().setCancelable(false);
        showDialog();
//        String departmentName = sharePreUtil.getValue(Constant.SP_USER_DEPARTMENT);
//        spinnerRoom.setItems(new String[]{departmentName});

        if (USER_RANGE == 0) {
            spinnerRoom.setVisibility(View.GONE);
            frameTitleRight.setVisibility(View.GONE);
        } else if (USER_RANGE == 1) {
            initMaterialSpinner();
            roomsArrayList.clear();
            spinnerRoom.setItems(sharePreUtil.getValue(Constant.SP_USER_DEPARTMENT));
        } else if (USER_RANGE == 2) {
            initMaterialSpinner();
            getDepartments();
        }
        net(pageSize, true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            net(pageSize, true);
        }
    }

    private void initMaterialSpinner() {
        spinnerWorkState.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                //全部，在岗，不在岗
//                net(pageSize, true);
            }
        });
        spinnerRoom.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                String department = (String) item;
                getDialog().setCancelable(false);
                showDialog();
                //13.查询某一部门的所有用户/jc_oa/find_department_user (POST))  FindDepartmentUserEntity   OK  5.22
                getListByDepartmentName(department);
            }
        });
//        spinnerJob.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//
//            }
//        });
    }

    private void getDepartments() {
        //1.部门查询/jc_oa/find_department (POST))        DepartmentEntity   OK
        //传入"0"，获得全部部门
        CloudPlatformUtil.getInstance().getDepartments(
                JCOAApi.getDepartmentsMap("0")
                , new ResponseListener<DepartmentEntity>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
                        //取出最近最新的部门  ---  数据库缓存
//                        departments = DBOperation.getInstance().getDepartmentList();
                        dismissDialog();
                    }

                    @Override
                    public void onResponse(DepartmentEntity entity) {
                        departments = entity.getModels();
                        roomsArrayList.clear();
                        spinnerRoom.setItems(stringsNull);
                        for (DepartmentEntity.ModelsBean bean : departments) {
                            roomsArrayList.add(bean.getDepartment());
                        }
                        spinnerRoom.setItems(roomsArrayList);
//                        getListByDepartmentName(roomsArrayList.get(1));//
//                        getJobs();
                        //插入最近最新的部门
//                        DBOperation.getInstance().insertDeparmentList(departments);
                    }
                });
    }

    private void getJobs() {
        //2.职务查询/jc_oa/find_role (POST))              JobEntity         OK 2017-05-21
        CloudPlatformUtil.getInstance().getJobs(
                JCOAApi.getJobsMap(), new ResponseListener<JobEntity>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
//                        jobs = DBOperation.getInstance().getJobList();
                        dismissDialog();
                    }

                    @Override
                    public void onResponse(JobEntity response) {
                        jobs = response.getModels();
//                        DBOperation.getInstance().insertJobList(jobs);
                        for (JobEntity.ModelsBean bean : jobs) {
                            jobsArrayList.add(bean.getRole_name());
                        }
                        spinnerJob.setItems(jobsArrayList);
//                        net(pageSize, true);
//                        getListByDepartmentName(roomsArrayList.get(1));//
                    }
                });
    }

    private void clickDate(Date selectedDay, final boolean isStart) {
        if (myCalendarView.mPopupWindow != null && myCalendarView.mPopupWindow.isShowing()) {
            myCalendarView.mPopupWindow.dismiss();
            myCalendarView.mPopupWindow = null;
        }
        String date = TimeUtil.formatTime7(selectedDay);//formatTime6  ？？？

        if (isStart) {
            if (!myCalendarView.verifyBigDate(date, endTime)) {
                ToastUtil.shortToast(getActivity(), "开始日期应小于截止日期");
                return;
            }
            startTime = date;
            tvStartTime.setText(startTime);
//            tvStartTime.setText(MyCalendarView.formatDateToyyyyNMMYddR(selectedDay));
        } else {
            if (!myCalendarView.verifyBigDate(startTime, date)) {
                ToastUtil.shortToast(getActivity(), "截止日期应大于开始日期");
                return;
            }
            endTime = date;
            tvEndTime.setText(endTime);
        }
//        autoLoad(500); //设置完日期  ,  刷新
        getDialog().setCancelable(false);
        showDialog();
        net(pageSize, true);
//        lastDate = selectedDay.getDate();//记录上一次日期
    }

    /**
     * "models": [
     * {"departmentid": 1,"department": "大队长","powerid": 5},
     * {"departmentid": 2,"department": "副队长","powerid": 3},
     * {"departmentid": 3,"department": "稽查管理科","powerid": 2},
     * {"departmentid": 4,"department": "稽查督查科","powerid": 2},
     * {"departmentid": 5,"department": "建筑市场稽查科","powerid": 2},
     * {"departmentid": 7,"department": "房地产市场稽查科","powerid": 2},
     * {"departmentid": 8,"department": "行政主任","powerid": 9}]
     *
     * @param department
     */
    private void getListByDepartmentName(String department) {
        String departmentid = sharePreUtil.getValue(Constant.SP_USER_DEPARTMENTID);//for test
        String[] departments = STR_DEPARTMENTS;
        if (department.equals("部门")) {
            return;
        }
        if (department.equals(departments[0])) {
            departmentid = "1";
        } else if (department.equals(departments[1])) {
            departmentid = "2";
        } else if (department.equals(departments[2])) {
            departmentid = "3";
        } else if (department.equals(departments[3])) {
            departmentid = "4";
        } else if (department.equals(departments[4])) {
            departmentid = "5";
        } else if (department.equals(departments[5])) {
            departmentid = "7";
        } else if (department.equals(departments[6])) {
            departmentid = "8";
        }
        //13.查询某一部门的所有用户/jc_oa/find_department_user (POST))  FindDepartmentUserEntity   OK  5.22
        CloudPlatformUtil.getInstance().findDepartmentUser(
                JCOAApi.getFindDepartmentUserMap(departmentid)
                , new ResponseListener<FindDepartmentUserEntity>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissDialog();
                        VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
                    }

                    @Override
                    public void onResponse(FindDepartmentUserEntity entity) {
//                        VLogUtil.v(entity.toString());
//                        updateData();//重新查询后，加载新数据
                        // TODO: 2017/6/7 0007 youwenti
                        if (entity.getModels() == null) return;
                        findDepartUserEntitys = entity.getModels();
                        if (mAdapter == null || mAdapter instanceof WorkTrendsAdapter) {
                            mAdapter = new DepartmentUsersAdapter(mContext, findDepartUserEntitys, mInflater);
                            mListView.setAdapter(mAdapter);
                        } else {
                            mAdapter.autoSetData(findDepartUserEntitys, mode);
                        }
                        dismissDialog();
                    }
                });
    }

    @Override
    protected void getNetData(int pageSize, boolean mode) {
        //查询权限下用户信息/jc_oa/get_leve_user (POST))
        CloudPlatformUtil.getInstance().getLeveUser(JCOAApi.getLeveUserMap()
                , new ResponseListener<GetleveUserEntity>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
                        dismissDialog();
                    }

                    @Override
                    public void onResponse(GetleveUserEntity entity) {
//                        VLogUtil.w("查询权限下用户信息 ---   " + entity.toString());
                        if (entity != null && entity.getUserExtends() != null) {
                            leveUserExtends = entity.getUserExtends();
//                            tvZGRS.setText("在岗人数 " + leveUserExtends.size());
                            StringBuilder stringBuilder = new StringBuilder();
                            for (GetleveUserEntity.UserExtendsBean user : leveUserExtends) {
                                stringBuilder.append(user.getUsername() + ",");
                            }
                            userNameLong = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
                            if (StringUtils.isBlank(startTime)) {
                                showShortToast("请选择开始时间");
                                return;
                            }
                            if (StringUtils.isBlank(endTime)) {
                                showShortToast("请选择结束时间");
                                return;
                            }
                            if (StringUtils.isBlank(userNameLong)) {
                                showShortToast("获取信息失败");
                                return;
                            }
                            //14.查询权限下用户的工作动态/jc_oa/Statistical_personal.action(POST))  StatisticalPersonalEntity  OK 5.22
                            // 在岗，     分为：单位办公 、外出 、其他
                            // 不在岗，   分为：事假、病假、特殊休假（工伤）、外调、年假、其他
//                            VLogUtil.d("STIME---" + startTime + "---ENDTIME---" +
//                                    endTime + "---用户名们 ：  " + userNameLong);
                            CloudPlatformUtil.getInstance().findStatisticalPersonal(
                                    JCOAApi.getStatisticalPersonalMap(startTime//"2017.2.2"
                                            , endTime
                                            , userNameLong)//"admin,weixiao,麻培,李炳州,赵琼"
                                    , WorkStateFragment.this);
                        }
                    }
                });
    }

    @Override
    protected void onNetError(VolleyError error) {
        dismissDialog();
        VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
    }

    @Override
    protected void onNetSuccess(StatisticalPersonalEntity entity) {
        /*
    "dstateModel": {
        "d_workday": 1,
        "d_not_workday": 0,
        "stateModels": [
            {
                "username": "weixiao",
                "work_day": 1,
                "notwork_day": 0,
                "shijia": 0,
                "bingjia": 0,
                "waichu": 0,
                "gongshang": 0,
                "danweibangong": 1,
                "teshuxiujia": 0,
                "waidiao": 0        }
         */
        if (entity.getDstateModel() == null) return;
        datas = entity.getDstateModel().getStateModels();
        spinnerWorkState.setItems(stringsNull);
        spinnerWorkState.setItems(new String[]{"在岗（天）" + "\t" + entity.getDstateModel().getD_workday()
                , "不在岗（天）" + "\t" + entity.getDstateModel().getD_not_workday()});
        if (mAdapter == null || mAdapter instanceof DepartmentUsersAdapter) {
//            VLogUtil.e("11111" + entity.toString());
            mAdapter = new WorkTrendsAdapter(mContext, entity, datas, mInflater);
            mListView.setAdapter(mAdapter);
        } else {
//            VLogUtil.e("22222"+ entity.toString());
            mAdapter.autoSetData(datas, true);
        }
        dismissDialog();
    }

    @Override
    protected boolean hasMore(StatisticalPersonalEntity response) {
        return false;
    }
}