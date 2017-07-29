package com.vitek.jcoa.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.net.VolleyErrorManager;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseAppCompatActivity;
import com.vitek.jcoa.util.VLogUtil;

/**
 * 测试用Acticity
 */
public class ApiTestActivity extends GBaseAppCompatActivity<Object> {
    private TextView textView;
    private Object entity;

    public static final String NET_RESPONSE = "response";


    @Override
    protected void initVariables() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_api_test;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        textView = (TextView) findViewById(R.id.tvTestBackData);
        net();
    }

    //   "weixiao", "password"
    //   admin      jcoaAdmin
    @Override
    protected void getNetData() {
        //http://192.168.213.1:8080/login/   loginName  password


//--------------------------------------------登录--------------------------------------------------------//

        //1.部门查询/jc_oa/find_department (POST))        DepartmentEntity   OK
        //传入"0"，获得全部部门
//        CloudPlatformUtil.getInstance().getDepartments(JCOAApi.getDepartmentsMap("0"), this);

        //2.职务查询/jc_oa/find_role (POST))              JobEntity         OK 2017-05-21
//        CloudPlatformUtil.getInstance().getJobs(JCOAApi.getJobsMap(), this);

        //3.退出登录/jc_oa/login_out (POST))-----------   LoginOutEntity    OK 2017-05-21
//        CloudPlatformUtil.getInstance().loginOut(JCOAApi.getLoginOutMap(), this);
//--------------------------------------------日志--------------------------------------------------------//

        //4.查询用户信息/jc_oa/my_info (POST))            UserInfoEntity    OK 2017-05-21
//        CloudPlatformUtil.getInstance().getUserInfo(JCOAApi.getUserInfoMap(), this );

        //5.发布日志/jc_oa/addlog (POST))                PublishLogEntity
// {"ispass":true,"defaultMessage":"添加成功","models":null,"logExtends":null}

        // "username": "weixiao",  "password": "password",
//        CloudPlatformUtil.getInstance().publishLog(JCOAApi.getPublishLogMap(
//                "weixiao",
//                "我是日志内容：最近河北省有暴雨！",
//                "1",
//                "单位办公",
//                "2017-5-21"
//        ), this);

//        6.查询日志草稿/jc_oa/find_ispublished (POST))    FindIsPublishedEntity OK 2017-05-21
//        CloudPlatformUtil.getInstance().findIspublished(JCOAApi.getFindIspublished(), this);

        //7.更新日志草稿/jc_oa/up_mylog (POST))       UpdateMyLogEntity
        // TODO: 2017/5/21 0021  onNetError --- 服务器响应失败  ！？？？   weixiao
//        CloudPlatformUtil.getInstance().updateMyLog(JCOAApi.getUpdateMylogMap("120", "weixiao"
//                , "content", "1", "xxx"
//                , "1"), this);

        //8.删除日志草稿/jc_oa/del_log (POST))        DeleteMyLogEntity           OK 2017-05-21
//        CloudPlatformUtil.getInstance().deleteMyLog(JCOAApi.getDeleteMylogMap("1"), this);

        //9.查询当前登录用户今天是否已发布日志/jc_oa/web_checklog (POST))
        //                                          WebChecklogEntity           OK 2017-05-21
//        CloudPlatformUtil.getInstance().getWebChecklogToday(JCOAApi.getWebChecklogMap(), this);

        //10.查询日志/jc_oa/findlog.action (POST))   FindlogEntity
        /*
         *  log_actiontime 日期区间-起
         *  log_endtime    日期区间-止
         *                       注：三者传一个（分别为按职务查询，按用户查询和按部门查询），
         *                       如果参数为空，查询权限下所有的日志
         *  job            职务字符串
         *  username       查询的用户username
         *  department     部门的名字
         */
//        CloudPlatformUtil.getInstance().findLogs(JCOAApi.getFindLogMap("2017.4.1", "2017.5.21",
//                "", "weixiao", ""), this);

//--------------------------------------------工作动态查询--------------------------------------------------------//

        //11.查询权限下用户/jc_oa/findleve_user (POST))    FindleveUserEntity   OK  5.22
//        CloudPlatformUtil.getInstance().findleveUser(JCOAApi.getFindleveUserMap(),this);
        //12.查询工作状态/jc_oa/findstate (POST))          FindStateEntity
        // TODO: 2017/5/22 0022 onNetError ---  ====POST-NetworkResponse===没有权限进行此操作
        // 05-25 16:57:58.552 28959-28959/com.vitek.jcoa E/123: getErrorMsg: com.android.volley.VolleyError: com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 1
        //   05-25 16:57:58.552 28959-28959/com.vitek.jcoa E/123: 数据异常  weixiao
        /*
         *     actiontime 开始日期
         *     endtime    结束日期
         *                   注意：两者不能同时传值，必须有一个为空
         *     lusername  用户的username
         *     department 部门
         */
//        CloudPlatformUtil.getInstance().findState(
//                JCOAApi.getFindStateMap("2017-02-2", "2017-05-30", "admin", "")
//                , this);

        //13.查询某一部门的所有用户/jc_oa/find_department_user (POST))  FindDepartmentUserEntity   OK  5.22
//        CloudPlatformUtil.getInstance().findDepartmentUser(JCOAApi.getFindDepartmentUserMap("1"), this);

        //14.查询权限下用户的工作动态/jc_oa/Statistical_personal.action(POST))  StatisticalPersonalEntity  OK 5.22
//        CloudPlatformUtil.getInstance().findStatisticalPersonal(
//                JCOAApi.getStatisticalPersonalMap("2017.4.2", "2017.5.20", "admin,weixiao"), this);
        //15.负责人工作动态汇总/jc_oa/job_summary.action (POST))  JobSummaryEntity
        //参数     day	yyyy-mm-dd格式	汇总的日期
        //返回值   data	字符串	要打印的html
//        CloudPlatformUtil.getInstance().getJobSummary(JCOAApi.getJobSummaryMap(TimeUtil.getStringDateyyyy_MM_dd()), this);//"2017-05-11"
        // TODO: 2017/5/22 0022   ↓
/*
====POST-NetworkResponse===没有权限进行此操作
05-22 10:00:24.469 15976-15976/com.vitek.jcoa E/123: getErrorMsg: com.android.volley.VolleyError: com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 1
05-22 10:00:24.470 15976-15976/com.vitek.jcoa E/123: onNetError --- 数据异常
 */
//--------------------------------------------用户设置--------------------------------------------------------//

        //16.查询权限下用户信息/jc_oa/get_leve_user (POST))  GetleveUserEntity  OK  5.22
//        CloudPlatformUtil.getInstance().getLeveUser(JCOAApi.getLeveUserMap(), this);

        //17.更新用户信息/jc_oa/up_user (POST))       UpdateUserEntity         OK  5.22
//        CloudPlatformUtil.getInstance().updateUserInfo(JCOAApi.getUpUserMap("120","weixiao","password"
//                ,"wei" ,"8","行政主任",""),
//        this);
//--------------------------------------------用户设置--------------------------------------------------------//

        //18.修改部门信息/jc_oa/up_Department (POST))    UpDepartmentEntity   OK  5.22
//        CloudPlatformUtil.getInstance().upDepartment(JCOAApi.getUpDepartmentMap("8","行政主任","9"),this);
        //19.添加新部门/jc_oa/up_Department (POST))      UpDepartmentEntity
        //接口与 18 相同，参数不同
        // TODO: 2017/5/22 0022  {"ispass":false,"defaultMessage":"更新失败","models":null}
//        CloudPlatformUtil.getInstance().upDepartment(JCOAApi.getUpDepartmentMap(null, "总监", "8"), this);
//--------------------------------------------工作流--------------------------------------------------------//


    }


    @Override
    protected void onNetSuccess(Object response) {
        entity = response;
//        VLogUtil.v("onNetSuccess --- " + response.toString());

        Message msg = getHandler().obtainMessage();
        msg.obj = NET_RESPONSE;
        getHandler().sendMessage(msg);
    }

    @Override
    protected void onNetError(VolleyError error) {
        VLogUtil.e("onNetError --- " + VolleyErrorManager.getErrorMsg(error));
    }

    @Override
    protected void onMsgObtain(Message msg) {
        super.onMsgObtain(msg);
        if (msg.obj == NET_RESPONSE) {
            textView.setText("onNetSuccess --- " + entity.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            releaseInstance();
        }
    }
}
