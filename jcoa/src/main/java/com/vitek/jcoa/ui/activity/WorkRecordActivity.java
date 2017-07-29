package com.vitek.jcoa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.ObserverListener;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.SharePreUtil;
import com.misdk.util.StringUtils;
import com.misdk.util.TimeUtil;
import com.misdk.util.ToastUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.JCOAApplication;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseNetAppCompatActivity;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.entity.FindlogEntity;
import com.vitek.jcoa.ui.adapter.WorkRecordAdapter;
import com.vitek.jcoa.ui.view.MyCalendarView;
import com.vitek.jcoa.util.VLogUtil;

import java.util.Date;
import java.util.List;

/**
 * 工作记录
 * <p>
 * Created by javakam on 2017-05-18 .
 */
public class WorkRecordActivity extends GBaseNetAppCompatActivity<FindlogEntity> {

    //    public static int RECORD_STATE = 0;//0 startTime , 1 endTime
    private FrameLayout titleBack;
    private TextView tvTitle, tvTitleNowday;

    private RelativeLayout relaStartTime, relaEndTime;//开始时间&结束时间
    private TextView tvStartTime, tvEndTime;//日期
    private MyCalendarView myCalendarView;//日历控件
    private String startTime = TimeUtil.getFirstDayOfThisMonth();
    private String endTime = TimeUtil.getStringDateyyyyMMdd2();
    private boolean isStart = true;
    private Date lastDate = new Date();//上一次的日期，默认今日防止null

    private List<FindlogEntity.LogExtendsBean> datas;
    private SharePreUtil sharePreUtil;

    @Override
    protected void initVariables() {
        setTranslucentStatus(R.color.titleColor);
        sharePreUtil = new SharePreUtil(this, Constant.SPF_USER_INFO);
        pageSize = 1000;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_work_record;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        mPtrFrame.setBackgroundColor(getResources().getColor(R.color.white));
        titleBack = (FrameLayout) findViewById(R.id.titleBack);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("工作记录");
        /*
        测试用
         */
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkRecordActivity.this, ApiTestActivity.class));
            }
        });
        tvTitleNowday = (TextView) findViewById(R.id.tvTitleNowday);
        tvTitleNowday.setText(TimeUtil.getTitleDate());

        relaStartTime = (RelativeLayout) findViewById(R.id.relaStartTime);
        relaEndTime = (RelativeLayout) findViewById(R.id.relaEndTime);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        tvStartTime.setText(startTime);
        tvEndTime.setText(endTime);
        myCalendarView = new MyCalendarView(this);
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

        net(pageSize, true);
        JCOAApplication.getInstance().registerObserver(JCOAApplication.UPDATE_LOG, new ObserverListener() {
            @Override
            public void notifyChange(Bundle bundle, Object object) {
                getDialog();
                showDialog();
                net(pageSize, true);
            }
        });
    }


    private void clickDate(Date selectedDay, final boolean isStart) {
        if (myCalendarView.mPopupWindow != null && myCalendarView.mPopupWindow.isShowing()) {
            myCalendarView.mPopupWindow.dismiss();
            myCalendarView.mPopupWindow = null;
        }
        String date = TimeUtil.formatTime7(selectedDay);

        if (isStart) {
            if (!myCalendarView.verifyBigDate(date, endTime)) {
                ToastUtil.shortToast(this, "开始日期应小于截止日期");
                return;
            }
            startTime = date;
            tvStartTime.setText(startTime);
//            tvStartTime.setText(MyCalendarView.formatDateToyyyyNMMYddR(selectedDay));
        } else {
            if (!myCalendarView.verifyBigDate(startTime, date)) {
                ToastUtil.shortToast(this, "截止日期应大于开始日期");
                return;
            }
            endTime = date;
            tvEndTime.setText(endTime);
        }
        autoLoad(500); //设置完日期  ,  刷新
//        lastDate = selectedDay.getDate();//记录上一次日期
    }

    @Override
    protected void getNetData(int pageSize, boolean mode) {
        //6.查询日志/jc_oa/findlog.action (POST))     FindlogEntity
        /*
         * @   log_actiontime 日期区间-起
         * @   log_endtime    日期区间-止
         *                       注：三者传一个（分别为按职务查询，按用户查询和按部门查询），
         *                       如果参数为空，查询权限下所有的日志
         * @   job            职务字符串   行政主任
         * @   username       查询的用户     weixiao
         * @   department     部门的名字   行政主任
         */
        String job = sharePreUtil.getValue(Constant.SP_USER_JOB);
        String username = sharePreUtil.getValue(Constant.SP_USER_NAME);
        String department = sharePreUtil.getValue(Constant.SP_USER_DEPARTMENT);
        String[] args = new String[]{job, "", ""};
        //默认今日
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            startTime = TimeUtil.getStringDateyyyyMMdd2();
            endTime = TimeUtil.getStringDateyyyyMMdd2();
        }
        /*
         *                注：三者传一个（分别为按职务查询，按用户查询和按部门查询），
         *                如果参数为空，查询权限下所有的日志
         * @param job            职务字符串
         * @param username       查询的用户username
         * @param department     部门的名称
         */
        VLogUtil.i("工作记录参数  ---  " + startTime + " --- " + endTime + " --- "
                + "username" + args[1] + " --- " + "department : " + args[2] + " --- ");
        // TODO: 2017/6/13 0013   "job" 可以， "weixiao"  无效，"系统管理员" 无效
        CloudPlatformUtil.getInstance().findLogs(JCOAApi.getFindLogMap(
                startTime  //log_actiontime  startTime  "2017.4.30"
                , endTime //log_actiontime   错误格式： "2017-5-21"
                , args)
                , this);
    }

    @Override
    protected void onNetError(VolleyError error) {
        VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
    }

    @Override
    protected void onNetSuccess(FindlogEntity entity) {
        VLogUtil.i("工作记录  ===   " + entity.toString());
        if (entity.getLogExtends() == null) return;
        datas = entity.getLogExtends();
        if (mAdapter == null) {
            mAdapter = new WorkRecordAdapter(this, datas, mInflater);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.autoSetData(datas, mode);
        }
    }

    @Override
    protected boolean hasMore(FindlogEntity response) {
        return false;
    }

    @Override
    protected void onDestroy() {
        JCOAApplication.getInstance().unRegisterObserver(JCOAApplication.UPDATE_LOG);
        super.onDestroy();
    }
}
