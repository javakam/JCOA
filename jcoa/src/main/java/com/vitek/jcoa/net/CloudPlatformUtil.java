package com.vitek.jcoa.net;

import com.android.volley.Request;
import com.misdk.net.CookiePostRequest;
import com.misdk.net.GetObjectRequest;
import com.misdk.net.PostObjectRequest;
import com.misdk.net.ResponseListener;
import com.misdk.net.VolleyUtil;
import com.vitek.jcoa.net.entity.CompleteEntity;
import com.vitek.jcoa.net.entity.DeleteMyLogEntity;
import com.vitek.jcoa.net.entity.DepartmentEntity;
import com.vitek.jcoa.net.entity.FindCFilesEntity;
import com.vitek.jcoa.net.entity.FindDepartmentUserEntity;
import com.vitek.jcoa.net.entity.FindIsPublishedEntity;
import com.vitek.jcoa.net.entity.FindReturnLogEntity;
import com.vitek.jcoa.net.entity.FindStateEntity;
import com.vitek.jcoa.net.entity.FindWorkflowUserdEntity;
import com.vitek.jcoa.net.entity.FindleveUserEntity;
import com.vitek.jcoa.net.entity.FindlogEntity;
import com.vitek.jcoa.net.entity.GetBranchEntity;
import com.vitek.jcoa.net.entity.GetVerificationCodeEntity;
import com.vitek.jcoa.net.entity.GetleveUserEntity;
import com.vitek.jcoa.net.entity.JobEntity;
import com.vitek.jcoa.net.entity.LoginOutEntity;
import com.vitek.jcoa.net.entity.PasswordRetrievalEntity;
import com.vitek.jcoa.net.entity.PublishLogEntity;
import com.vitek.jcoa.net.entity.ReturnLogEntity;
import com.vitek.jcoa.net.entity.ShowTaskEntity;
import com.vitek.jcoa.net.entity.StartFlowEntity;
import com.vitek.jcoa.net.entity.StatisticalPersonalEntity;
import com.vitek.jcoa.net.entity.UpDepartmentEntity;
import com.vitek.jcoa.net.entity.UpdateMyLogEntity;
import com.vitek.jcoa.net.entity.UpdateUserEntity;
import com.vitek.jcoa.net.entity.UserInfoEntity;
import com.vitek.jcoa.net.entity.WebChecklogEntity;
import com.vitek.jcoa.net.entity.WorkFlowDepartmentEntity;
import com.vitek.jcoa.util.VLogUtil;

import org.json.JSONObject;

import java.util.Map;


/**
 * Created by kam on 2017/5/12.
 */

public class CloudPlatformUtil {
    private static final String JSESSIONID = ";jsessionid=";

    private CloudPlatformUtil() {
    }

    private static final CloudPlatformUtil ourInstance = new CloudPlatformUtil();

    public static CloudPlatformUtil getInstance() {
        return ourInstance;
    }

    //============================GET=================================//


    /**
     * 新建公文
     *
     * @param url
     * @param responseListener
     */
    public void getStartFlow(String url, ResponseListener responseListener) {
        VolleyUtil.getRequestQueue().add(
                new GetObjectRequest(url, StartFlowEntity.class, responseListener));
    }

    /**
     * 展示公文
     *
     * @param url
     * @param responseListener
     */
    public void getShowTask(String url, ResponseListener responseListener) {
        VolleyUtil.getRequestQueue().add(
                new GetObjectRequest(url, ShowTaskEntity.class, responseListener));
    }

    /**
     * 查看公文可流转的下一部门  ---  新建的公文（待办公文） 提交
     *
     * @param url
     * @param responseListener
     */
    public void getWorkFlowDepartment(String url, ResponseListener responseListener) {
        VolleyUtil.getRequestQueue().add(new GetObjectRequest(url
                , WorkFlowDepartmentEntity.class, responseListener));
    }

    /**
     * 获得某个部门可以接受公文的人员
     *
     * @param url
     * @param responseListener
     */
    public void getFindWorkflowUserD(String url, ResponseListener responseListener) {
        VolleyUtil.getRequestQueue().add(new GetObjectRequest(url, FindWorkflowUserdEntity.class, responseListener));
    }

    /**
     * 获取回退及指派的提交值以及是否可以归档/jc_oa/flow/get_taskbranch（GET）
     */
    public void getBranch(String url, ResponseListener responseListener) {
        VolleyUtil.getRequestQueue().add(new GetObjectRequest(url, GetBranchEntity.class, responseListener));
    }

    /**
     * 获取批文附件/jc_oa/flow/find_cfiles（GET）
     */
    public void findCFiles(String url, ResponseListener responseListener) {
        VolleyUtil.getRequestQueue().add(new GetObjectRequest(url, FindCFilesEntity.class, responseListener));
    }


    //============================POST=================================//

    /**
     * 登录
     *
     * @param params
     * @param responseListener
     */
    public void loginMethod(Map<String, String> params, ResponseListener responseListener, String cookie) {
        CookiePostRequest request = new CookiePostRequest(JCOAApi.LOGIN_URL, responseListener, params);
        request.addSessionCookie(LoginUtil.COOKIE, cookie);//向服务器发起post请求时加上cookie字段
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 获取密码找回短信验证码  GetVerificationCode  （POST）
     *
     * @param params
     * @param responseListener
     */
    public void getGetVerificationCode(Map<String, String> params, ResponseListener responseListener) {
        Request request = new PostObjectRequest(JCOAApi.GETVERIFICATIONCODE,
                params, GetVerificationCodeEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 找回密码 PasswordRetrieval (POST)
     *
     * @param params
     * @param responseListener
     */
    public void getPasswordRetrieval(Map<String, String> params, ResponseListener responseListener) {
        Request request = new PostObjectRequest(JCOAApi.PASSWORD_RETRIEVAL,
                params, PasswordRetrievalEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 部门查询
     *
     * @param params
     * @param responseListener
     */
    public void getDepartments(Map<String, String> params, ResponseListener responseListener) {
        Request request = new PostObjectRequest(JCOAApi.DEPARTMENT_URL,
                params,
                DepartmentEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 职务查询
     *
     * @param params
     * @param responseListener
     */
    public void getJobs(Map<String, String> params, ResponseListener responseListener) {
        Request request = new PostObjectRequest(JCOAApi.JOB_URL
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params,
                JobEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 退出登录
     *
     * @param params
     * @param responseListener
     */
    public void loginOut(Map<String, String> params, ResponseListener responseListener) {
        Request request = new PostObjectRequest(JCOAApi.LOGIN_OUT_URL
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params,
                LoginOutEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 查询用户信息
     *
     * @param params
     * @param responseListener
     */
    public void getUserInfo(Map<String, String> params, ResponseListener responseListener) {
        PostObjectRequest request = new PostObjectRequest(JCOAApi.USER_INFO_URL
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params,
                UserInfoEntity.class, responseListener);//+ ";jsessionid=" + cookie
//request.addSessionCookie(LoginUtil.getLocalCookie());
        VLogUtil.d(JCOAApi.USER_INFO_URL + JSESSIONID + LoginUtil.getLocalCookie());

//        request.addSessionCookie(LoginUtil.COOKIE, ";jsessionid="+cookie);
//        try {
//            request.getHeaders().put(LoginUtil.COOKIE+";jsessionid=",  cookie);
//        } catch (AuthFailureError authFailureError) {
//            authFailureError.printStackTrace();
//        }

        //下面这些传值方式都无效   ：
        //  key  :  Cookie         value      CF89186039035972CEB678A9107FE964
        http:
//www.vitection.com/jc_oa/my_info/jsessionid=6582B76345277EF1418CDFB8F0898D38
        //  key  ： Cookie         value  ：  jsessionid=CF89186039035972CEB678A9107FE964
        //  key  ： Cookie         value  ：  jsession=CF89186039035972CEB678A9107FE964
        //  key  ： jsessionid     value  ：  CF89186039035972CEB678A9107FE964

        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 发布日志
     *
     * @param params
     * @param responseListener
     */
    public void publishLog(Map<String, String> params, ResponseListener responseListener) {
        Request request = new PostObjectRequest(JCOAApi.PUBLISH_URL
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params,
                PublishLogEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 查询草稿
     *
     * @param params
     * @param responseListener
     */
    public void findIspublished(Map<String, String> params, ResponseListener responseListener) {
        Request request = new PostObjectRequest(JCOAApi.FIND_ISPUBLISHED
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params,
                FindIsPublishedEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 日志退回 ReturnLogEntity（POST）    ReturnLogEntity
     *
     * @param params
     * @param responseListener
     */
    public void returnLog(Map<String, String> params, ResponseListener responseListener) {
        PostObjectRequest request = new PostObjectRequest(JCOAApi.RETURN_LOG
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, ReturnLogEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 查询退件箱
     */
    public void findReturnLog(Map<String, String> params, ResponseListener responseListener) {
        Request request = new PostObjectRequest(JCOAApi.FIND_RETURNLOG
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, FindReturnLogEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 更新日志草稿
     *
     * @param params
     * @param responseListener
     */
    public void updateMyLog(Map<String, String> params, ResponseListener responseListener) {
        Request request = new PostObjectRequest(JCOAApi.UPDATE_MYLOG
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, UpdateMyLogEntity.class, responseListener
        );
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 删除日志草稿
     *
     * @param params
     * @param responseListener
     */
    public void deleteMyLog(Map<String, String> params, ResponseListener responseListener) {
        Request request = new PostObjectRequest(JCOAApi.DELETE_MYLOG
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, DeleteMyLogEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 查询当前登录用户今天是否已发布日志
     *
     * @param params
     * @param responseListener
     */
    public void getWebChecklogToday(Map<String, String> params, ResponseListener responseListener) {
        Request request = new PostObjectRequest(JCOAApi.WEB_CHECKLOG
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, WebChecklogEntity.class, responseListener
        );
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 查询日志
     *
     * @param params
     * @param responseListener
     */
    public void findLogs(Map<String, String> params, ResponseListener responseListener) {
//        Request request=new CookiePostRequest(JCOAApi.FIND_LOG
//        +JSESSIONID+LoginUtil.getLocalCookie()
//        ,responseListener,params);
        Request request = new PostObjectRequest(JCOAApi.FIND_LOG
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, FindlogEntity.class, responseListener
        );
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 查询权限下用户
     *
     * @param params
     * @param responseListener
     */
    public void findleveUser(Map<String, String> params, ResponseListener responseListener) {
        Request request = new PostObjectRequest(JCOAApi.FINDLEVE_USER
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, FindleveUserEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 查询工作状态
     *
     * @param params
     * @param responseListener
     */
    public void findState(Map<String, String> params, ResponseListener responseListener) {
        Request request = new PostObjectRequest(JCOAApi.FIND_STATE
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, FindStateEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 查询某一部门的所有用户
     *
     * @param params
     * @param responseListener
     */
    public void findDepartmentUser(Map<String, String> params, ResponseListener responseListener) {
        PostObjectRequest request = new PostObjectRequest(JCOAApi.FIND_DEPARTMENT_USER
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, FindDepartmentUserEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 查询权限下用户的工作动态
     *
     * @param params
     * @param responseListener
     */
    public void findStatisticalPersonal(Map<String, String> params, ResponseListener responseListener) {
        PostObjectRequest request = new PostObjectRequest(JCOAApi.STATISTICAL_PERSON
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, StatisticalPersonalEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 负责人工作动态汇总   --- 只有大队长才可以操作
     *
     * @param params
     * @param responseListener
     */
    public PostObjectRequest getJobSummary(Map<String, String> params, ResponseListener responseListener) {
        PostObjectRequest request = new PostObjectRequest(JCOAApi.JOB_SUMMARY
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, JSONObject.class, responseListener);//JobSummaryEntity
        VolleyUtil.getRequestQueue().add(request);
        return request;
    }

    /**
     * 查询权限下用户信息
     *
     * @param params
     * @param responseListener
     */
    public void getLeveUser(Map<String, String> params, ResponseListener responseListener) {
        PostObjectRequest request = new PostObjectRequest(JCOAApi.GETLEVE_USER
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, GetleveUserEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 更新用户信息
     *
     * @param params
     * @param responseListener
     */
    public void updateUserInfo(Map<String, String> params, ResponseListener responseListener) {
        PostObjectRequest request = new PostObjectRequest(JCOAApi.UPDATE_USER_INFO
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, UpdateUserEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 修改部门信息
     *
     * @param params
     * @param responseListener
     */
    public void upDepartment(Map<String, String> params, ResponseListener responseListener) {
        PostObjectRequest request = new PostObjectRequest(JCOAApi.UP_DEPARTMENT
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, UpDepartmentEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }

    /**
     * 提交公文
     * <p>
     * 归档和转交下一部门的接口
     */
    public void complete(Map<String, String> params, ResponseListener responseListener) {
        PostObjectRequest request = new PostObjectRequest(JCOAApi.GW_COMPLETE
                + JSESSIONID + LoginUtil.getLocalCookie(),
                params, CompleteEntity.class, responseListener);
        VolleyUtil.getRequestQueue().add(request);
    }
    //==============================================================================//


}
