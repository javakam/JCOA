package com.vitek.jcoa.net;

import com.misdk.util.StringUtils;
import com.vitek.jcoa.util.VLogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by javakam on 2017/5/16 0016.
 */

public class JCOAApi {
    // http://www.vitection.com/OA-office/index-oa.html  oa主页
    public static final String BASE_URL2 = "http://i160p36491.51mypc.cn:10692/jc_oa/";
    public static final String BASE_URL = "http://www.vitection.com/jc_oa/";

    //公文BaseUrl
    public static final String BASE_FLOW_URL = BASE_URL + "flow/";
    //返回的图片路径需要加上这个前缀
    public static final String BASE_CFILES = "http://www.vitection.com/jc_oa";

    //----------------===============POST URL===============----------------//
    /**
     * 登录
     */
    public static final String LOGIN_URL = BASE_URL + "u_login";
    /**
     * 获取密码找回短信验证码
     */
    public static final String GETVERIFICATIONCODE = BASE_URL + "GetVerificationCode";
    /**
     * 找回密码
     */
    public static final String PASSWORD_RETRIEVAL = BASE_URL + "PasswordRetrieval";
    /**
     * 部门查询
     */
    public static final String DEPARTMENT_URL = BASE_URL + "find_department";
    /**
     * 职务查询
     */
    public static final String JOB_URL = BASE_URL + "find_role";
    /**
     * 退出登录
     */
    public static final String LOGIN_OUT_URL = BASE_URL + "login_out";
    /**
     * 查询用户信息
     */
    public static final String USER_INFO_URL = BASE_URL + "my_info";
    /**
     * 发布日志
     */
    public static final String PUBLISH_URL = BASE_URL + "addlog";
    /**
     * 查询日志草稿
     */
    public static final String FIND_ISPUBLISHED = BASE_URL + "find_ispublished";
    /**
     * 日志退回
     */
    public static final String RETURN_LOG = BASE_URL + "ReturnLog";
    /**
     * 查询退件箱
     */
    public static final String FIND_RETURNLOG = BASE_URL + "findReturnLog";
    /**
     * 更新日志草稿
     */
    public static final String UPDATE_MYLOG = BASE_URL + "up_mylog";
    /**
     * 删除日志草稿
     */
    public static final String DELETE_MYLOG = BASE_URL + "del_log";
    /**
     * 查询当前登录用户今天是否已发布日志
     */
    public static final String WEB_CHECKLOG = BASE_URL + "web_checklog";
    /**
     * 查询日志
     */
    public static final String FIND_LOG = BASE_URL + "findlog.action";
    /**
     * 查询权限下用户
     */
    public static final String FINDLEVE_USER = BASE_URL + "findleve_user";
    /**
     * 查询工作状态
     */
    public static final String FIND_STATE = BASE_URL + "findstate";
    /**
     * 查询某一部门的所有用户
     */
    public static final String FIND_DEPARTMENT_USER = BASE_URL + "find_department_user";
    /**
     * 查询权限下用户的工作动态
     */
    public static final String STATISTICAL_PERSON = BASE_URL + "Statistical_personal.action";
    /**
     * 负责人工作动态汇总
     */
    public static final String JOB_SUMMARY = BASE_URL + "job_summary.action";
    /**
     * 查询权限下用户信息
     */
    public static final String GETLEVE_USER = BASE_URL + "get_leve_user";
    /**
     * 更新用户信息
     */
    public static final String UPDATE_USER_INFO = BASE_URL + "up_user";
    /**
     * 修改部门信息 & 添加新部门
     */
    public static final String UP_DEPARTMENT = BASE_URL + "up_Department";
    /**
     * 添加公文图片
     */
    public static final String ADD_CFILES = BASE_FLOW_URL + "add_cfiles";
    /**
     * 查询公文处理意见
     */
    public static final String FIND_OPINION = BASE_FLOW_URL + "find_opinion";

    //----------------===============GET URL===============----------------//
    /**
     * 公文列表
     * <p>
     * "mytask": {
     * "id": "107607",
     * "name": "综合办公室",!!!!!!注意注意，牛逼的地方来了。。。 公文的后三个页面：同一接口，用文字做判断加载，屌炸天！！！
     * =============  其中 ：“综合办公室” 表示 新建的公文即代办公文；
     * “副大队长办公室” 表示 已提交的公文；“归档”表示，已归档的公文。
     * "processInstanceId": "107603",
     * "assignee": "weixiao",
     * "processDefinitionId": "myLeaveProcess:1:4",
     * "executionId": "107603"
     * }
     */
    public static final String SHOW_TASK = BASE_FLOW_URL + "showTask";
    /**
     * 新建公文
     */
    public static final String START_FLOW = BASE_FLOW_URL + "startFlow";
    /**
     * 提交公文  ：  归档和转交下一部门的接口
     */
    public static final String GW_COMPLETE = BASE_FLOW_URL + "complete";

    /**
     * 查看公文可流转的下一部门   ! 无参数 !   同下面url联动的
     */
    public static final String WORKFLOW_DEPARTMENT = BASE_FLOW_URL + "Workflow_Department";

    /**
     * 获得某个部门可以接受公文的人员          同上面url联动的
     */
    public static final String FIND_WORKFLOW_USER_D = BASE_FLOW_URL + "find_workflow_user_d";
    /**
     * 获取回退及指派的提交值以及是否可以归档   无参数
     */
    public static final String GET_BRANCH = BASE_FLOW_URL + "get_taskbranch";
    /**
     * 获取批文附件/jc_oa/flow/find_cfiles（GET）
     * <p>
     * 图片 ：http://www.vitection.com/jc_oa/    cfiles/5bb6719f-d5ff-4779-a582-9db4960ba3d5.png
     */
    public static final String FIND_CFILES = BASE_FLOW_URL + "find_cfiles";

    //============================GET=================================//

    /**
     * 新建公文
     * http://www.vitection.com/jc_oa/flow/startFlow
     * ?number=num1
     * &d_number=gnum
     * &s_number=xu
     * &source=%E4%B8%8A%E7%BA%A7%E4%BA%A4%E5%8A%9E
     * &d_source=%E6%9D%A5%E4%B8%89%E5%9B%9B%E5%8D%81
     * &handle_type=%E5%85%B6%E4%BB%96
     * &this_d=1
     * &enclosure=2
     * &ecpy_d=3
     * &source_time=2017-05-26
     * &title=title
     * &remarks=%E8%82%A1%E4%BB%BD%E5%85%AC%E5%8F%B8
     * &create_people=weixiao
     * &the_file=1
     *
     * @param number        编号
     * @param d_number      文号
     * @param s_number      序号
     * @param source        公文来源（上级交办、其他部门移交）
     * @param d_source      来文机关
     * @param handle_type   公文主题类别（预售违法类、违法施工类）
     * @param this_d        正件（份数）
     * @param enclosure     附件（份数）
     * @param ecpy_d        复印件（份数）
     * @param source_time   收到时间
     * @param title         题目
     * @param remarks       备注
     * @param create_people 创建人
     * @param the_file      1，不归档，0，归档（创建时传1）
     * @return
     */
    public static String getStartFlow(String number, String d_number
            , String s_number, String source, String d_source, String handle_type, String this_d
            , String enclosure, String ecpy_d, String source_time, String title, String remarks
            , String create_people, String the_file) {
        return START_FLOW + "?" + "number" + "=" + number
                + "&" + "d_number" + "=" + d_number
                + "&" + "s_number" + "=" + s_number
                + "&" + "source" + "=" + source
                + "&" + "d_source" + "=" + d_source
                + "&" + "handle_type" + "=" + handle_type
                + "&" + "this_d" + "=" + this_d
                + "&" + "enclosure" + "=" + enclosure
                + "&" + "ecpy_d" + "=" + ecpy_d
                + "&" + "source_time" + "=" + source_time
                + "&" + "title" + "=" + title
                + "&" + "remarks" + "=" + remarks
                + "&" + "create_people" + "=" + create_people
                + "&" + "the_file" + "=" + the_file;
    }

    /**
     * 查看公文
     *
     * @param assignee username
     * @return
     */
    public static String getShowTask(String assignee) {
        // eg  http://www.vitection.com/jc_oa/flow/showTask?assignee=weixiao
        return SHOW_TASK + "?" + "assignee" + "=" + assignee;//
    }

    /**
     * 获得某个部门可以接受公文的人员
     *
     * @param departmentid
     * @return
     */
    public static String getFindWorkflowUserD(String departmentid) {
        return FIND_WORKFLOW_USER_D + "?" + "departmentid" + "=" + departmentid;
    }

    /**
     * 获取批文附件
     */
    public static String getFindCfiles(String c_id) {
        return FIND_CFILES + "?" + "c_id" + "=" + c_id;
    }

    /**
     * 提交公文
     * <p>
     * 归档和转交下一部门的接口
     * 参数名称	参数类型	参数说明
     * number		taskDetail.number
     * d_number		taskDetail.d_number
     * source		公文来源（上级交办、其他部门移交）taskDetail.source
     * title		题目taskDetail.title
     * this_d		正件（份数）                  ------------- 动态传参
     * enclosure		附件（份数）              ------------- 动态传参
     * ecpy_d		复印件（份数）                ------------- 动态传参
     * remarks		备注
     * create_people		创建人（创建人的username）
     * the_file		1，不归档，0，归档（创建时传1）      ------------- 动态传参
     * c_id		当前公文的c_id
     * assignees		taskDetail.assignees
     * taskid		taskDetail.mytask.id
     * usernames		公文接受人的usernames（如果回退填taskDetail.assignees）如果归档此字段无效，可以传user.username   --- 动态传参
     * did_or_end		提交值（在get_taskbranch接口中有返回）（归档传0）    --- 动态传参
     * instid		taskDetail.instid
     * d_usernames		taskDetail.d_usernames
     *
     * @return
     */
    public static Map<String, String> getGwCompleteMap(String number, String d_number, String source, String title
            , String this_d, String enclosure, String ecpy_d, String remarks
            , String create_people, String the_file, String c_id, String assignees, String taskid
            , String usernames, String did_or_end, String instid, String d_usernames) {
        /*
        http://www.vitection.com/jc_oa/flow/complete
        ?number=12
        &d_number=12
        &source=%E4%B8%8A%E7%BA%A7%E4%BA%A4%E5%8A%9E
        &title=%E4%B8%8A%E7%BA%A7%E4%BA%A4%E5%8A%9E
        &c_id=105
        &enclosure=1
        &ecpy_d=1
        &remarks=%E7%99%BE%E5%8F%98%E5%AE%9D%E8%B4%9D%E4%B8%8D
        &assignees=weixiao
        &taskid=117654
        &this_d=1
        &usernames=v_%E6%B5%8B%E8%AF%951
        &did_or_end=5
        &instid=117650
        &d_usernames=weixiao%2C
         */
        VLogUtil.i("请求连接喽   ---   " + GW_COMPLETE + "?" + "number" + "=" + number
                + "&" + "d_number" + "=" + d_number
                + "&" + "source" + "=" + source
                + "&" + "title" + "=" + title
                + "&" + "this_d" + "=" + this_d
                + "&" + "enclosure" + "=" + enclosure
                + "&" + "ecpy_d" + "=" + ecpy_d
                + "&" + "remarks" + "=" + remarks
                + "&" + "create_people" + "=" + create_people
                + "&" + "the_file" + "=" + the_file
                + "&" + "c_id" + "=" + c_id
                + "&" + "assignees" + "=" + assignees
                + "&" + "taskid" + "=" + taskid
                + "&" + "usernames" + "=" + usernames
                + "&" + "did_or_end" + "=" + did_or_end
                + "&" + "instid" + "=" + instid
                + "&" + "d_usernames" + "=" + d_usernames + ";jsessionid=" + LoginUtil.getLocalCookie());
        HashMap<String, String> map = new HashMap<>();
        map.put("number", number);
        map.put("d_number", d_number);
        map.put("source", source);
        map.put("title", title);
        map.put("this_d", this_d);
        map.put("enclosure", enclosure);
        map.put("ecpy_d", ecpy_d);
        map.put("remarks", remarks);
        map.put("create_people", create_people);
        map.put("the_file", the_file);
        map.put("c_id", c_id);
        map.put("assignees", assignees);
        map.put("taskid", taskid);
        map.put("usernames", usernames);
        map.put("did_or_end", did_or_end);
        map.put("instid", instid);
        map.put("d_usernames", d_usernames);
        return map;
    }
    //============================POST=================================//

    /**
     * 登录
     *
     * @param user   用户名
     * @param passwd 密码
     * @return
     */
    public static Map<String, String> getLoginMap(String user, String passwd) {
        //生成MD5
        // passwd  = UserUtil.toLowerCaseMD5(passwd);
        //转成成UTF-8
//        try {
//            user = URLEncoder.encode(user, "UTF-8");
//            passwd = URLEncoder.encode(passwd, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        HashMap<String, String> mMap = new HashMap<String, String>();
        mMap.put("username", user);
        mMap.put("password", passwd);
        return mMap;
    }

    /**
     * 注册
     *
     * @param user   手机号---登录用
     * @param passwd 密码
     * @return
     */
    public static Map<String, String> getRegistMap(String user, String passwd) {
        Map<String, String> map = new HashMap<>();
        map.put("username", user);
        map.put("password", passwd);
        return map;
    }

    /**
     * 获取密码找回短信验证码
     *
     * @param phone 手机号
     * @return
     */
    public static Map<String, String> getGetVerificationCodeMap(String phone) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        return map;
    }

    /**
     * 找回密码 PasswordRetrieval (POST)
     *
     * @param phone       手机号
     * @param Code        短信验证码
     * @param MsgId       短信验证码ID
     * @param NewPassword 新密码
     * @return
     */
    public static Map<String, String> getPasswordRetrievalMap(String phone, String Code
            , String MsgId, String NewPassword) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("Code", Code);
        map.put("MsgId", MsgId);
        map.put("NewPassword", NewPassword);
        return map;
    }

    /**
     * 部门查询
     * 职位的powerid	可为空，也可不传空（不带参返所有部门）
     *
     * @param powerid
     * @return
     */
    public static Map<String, String> getDepartmentsMap(String powerid) {
        Map<String, String> map = new HashMap<>();
        map.put("powerid", powerid);
        return map;
    }

    /**
     * 职务查询
     * <p>
     * 参数名称  无参数
     *
     * @return
     */
    public static Map<String, String> getJobsMap() {
        return null;
    }

    /**
     * 退出登录！
     *
     * @return
     */
    public static Map<String, String> getLoginOutMap() {
        return null;
    }

    /**
     * 查询用户信息
     *
     * @return
     */
    public static Map<String, String> getUserInfoMap() {
        return null;
    }

    /**
     * 发布日志
     *
     * @param username
     * @param content
     * @param ispublished 1,0（1：发布，2：保存为草稿）
     * @param workstatus  工作状态 不为空,填二级选项的字符串
     * @param date        日期
     * @return
     */
    public static Map<String, String>
    getPublishLogMap(String username,
                     String content,
                     String ispublished,
                     String workstatus,
                     String date) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("content", content);
        map.put("ispublished", ispublished);
        map.put("workstatus", workstatus);
        map.put("date", date);
        return map;
    }

    /**
     * 查询日志草稿
     *
     * @return null
     */
    public static Map<String, String> getFindIspublished() {
        return null;
    }

    /**
     * 日志退回 ReturnLogEntity（POST）    ReturnLogEntity
     *
     * @param id          200
     * @param date        1493913600000
     * @param username    weixiao
     * @param content     今天，去办了一件公务。
     * @param ispublished 3  固定值 ！！！！！！
     * @param workstatus  单位办公
     * @return
     */
    public static Map<String, String> getReturnLogMap(String id, String date, String username
            , String content, String ispublished, String workstatus) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("date", date);
        map.put("username", username);
        map.put("content", content);
        map.put("ispublished", ispublished);
        map.put("workstatus", workstatus);
        return map;
    }

    /**
     * 查询退件箱
     *
     * @return null
     */
    public static Map<String, String> getFindReturnLogMap() {
        return null;
    }

    /**
     * 更新日志草稿
     *
     * @param id          日志草稿id
     * @param username
     * @param content
     * @param workstatus
     * @param date
     * @param ispublished 是否发布（1:发布，2：继续保存）  0  继续保存  ??
     * @return
     */
    public static Map<String, String> getUpdateMylogMap(String id, String username
            , String content, String workstatus, String date, String ispublished) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("username", username);
        map.put("content", content);
        map.put("workstatus", workstatus);
        map.put("date", date);
        map.put("ispublished", ispublished);
        return map;
    }

    /**
     * 删除日志草稿
     *
     * @param id 日志草稿id
     * @return
     */
    public static Map<String, String> getDeleteMylogMap(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        return map;
    }

    /**
     * 查询当前登录用户今天是否已发布日志
     *
     * @return null
     */
    public static Map<String, String> getWebChecklogMap() {
        return null;
    }

    /**
     * 查询日志
     *
     * @param log_actiontime 日期区间-起
     * @param log_endtime    日期区间-止
     *                       注：三者传一个（分别为按职务查询，按用户查询和按部门查询），
     *                       如果参数为空，查询权限下所有的日志
     *                       job            职务字符串
     *                       username       查询的用户username
     *                       department     部门的名称
     * @return
     */
    public static Map<String, String> getFindLogMap(String log_actiontime, String log_endtime
            , String... str) {
        HashMap<String, String> map = new HashMap<>();
        map.put("log_actiontime", log_actiontime);
        map.put("log_endtime", log_endtime);
        if (!StringUtils.isBlank(str[0])) {
            map.put("job", str[0]);
            return map;
        } else if (!StringUtils.isBlank(str[1])) {//查询制定用户
            map.put("username", str[1]);
            return map;
        } else if (!StringUtils.isBlank(str[2])) {//查询部门
            map.put("department", str[2]);
        }
        return map;//全部
    }

    /**
     * 查询权限下用户
     */
    public static Map<String, String> getFindleveUserMap() {
        return null;
    }

    /**
     * 查询工作状态
     *
     * @param actiontime 开始日期
     * @param endtime    结束日期
     *                   注意：两者不能同时传值，必须有一个为空
     * @param lusername  用户的username
     * @param department 部门
     * @return
     */
    public static Map<String, String> getFindStateMap(String actiontime, String endtime
            , String lusername, String department) {
        Map<String, String> map = new HashMap<>();
        map.put("actiontime", actiontime);
        map.put("endtime", endtime);
        //注意：两者不能同时传值，必须有一个为空
//        map.put("lusername", lusername);
        map.put("department", department);
        return map;
    }

    /**
     * 查询某一部门的所有用户
     *
     * @param departmentid 部门id ---  在用户信息里面
     * @return
     */
    public static Map<String, String> getFindDepartmentUserMap(String departmentid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("departmentid", departmentid);
        return map;
    }

    /**
     * 查询权限下用户的工作动态
     *
     * @param actiontime 日期区间-起
     * @param endtime    日期区间-止
     * @param username   数组（,号拼接）	用户的username数组
     * @return
     */
    public static Map<String, String> getStatisticalPersonalMap(String actiontime, String endtime
            , String username) {
        Map<String, String> map = new HashMap<>();
        map.put("actiontime", actiontime);
        map.put("endtime", endtime);
        map.put("username", username);
        return map;
    }

    /**
     * 负责人工作动态汇总
     *
     * @param day yyyy-mm-dd格式	汇总的日期
     * @return
     */
    public static Map<String, String> getJobSummaryMap(String day) {
        HashMap<String, String> map = new HashMap<>();
        map.put("day", day);
        return map;
    }

    /**
     * 查询权限下用户信息
     *
     * @return null
     */
    public static Map<String, String> getLeveUserMap() {
        return null;
    }

    /**
     * 更新用户信息
     * <p>
     * 除了更新的参数，其他都传原数据
     *
     * @param id
     * @param username
     * @param password
     * @param realname
     * @param departmentid
     * @param job
     * @param job_type
     * @param phone
     * @return
     */
    public static Map<String, String> getUpUserMap(String id, String username, String password,
                                                   String realname, String departmentid,
                                                   String job, String job_type, String phone) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("username", username);
        map.put("password", password);
        map.put("realname", realname);
        map.put("departmentid", departmentid);
        map.put("job", job);
        map.put("job_type", job_type);
        map.put("phone", phone);
        return map;
    }

    /**
     * 修改部门信息 & 添加新部门
     * 1.修改
     *
     * @param departmentid 部门id(要修改的部门id)
     * @param department   修改后的部门名称
     * @param powerid      修改后的部门权限值（与该部门的直接领导权限值相同）
     *                     2.添加
     *                     departmentid     ""
     *                     department		新部门名称
     *                     powerid		    新部门权限值（与该部门的直接领导权限值相同）
     * @return
     */
    public static Map<String, String> getUpDepartmentMap(String departmentid
            , String department, String powerid) {
        HashMap<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(departmentid)) {//添加
            map.put("department", department);
            map.put("powerid", powerid);
        } else {
            map.put("departmentid", departmentid);
            map.put("department", department);
            map.put("powerid", powerid);
        }
        return map;
    }

    /*
 添加公文图片/jc_oa/flow/add_cfiles
 参数名称	参数类型	参数说明
c_id
file_type		this_d:正件,enclosure：附件,ecpy_d:复印件
file		文件

  */
    public static Map<String, String> getAddCFilesMap(String c_id, String file_type) {

        HashMap<String, String> map = new HashMap<>();
        map.put("c_id", c_id);
        map.put("file_type", file_type);
//        map.put("file", file);
        return map;

    }
}
