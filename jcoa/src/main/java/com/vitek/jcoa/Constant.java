package com.vitek.jcoa;

import android.os.Environment;

/**
 * Created by javakam on 2017/05/16.
 */
public class Constant {

    public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static final String AUDIO_PATH = SD_PATH + "/Vitek/jcoa/audio";

    public static final String APP_FOLDER = SD_PATH + "/Vitek/jcoa/";
    public static final String APP_IMG_PATH = SD_PATH + "/Vitek/jcoa/pics";

    //==============================SharedPreferences==============================//

    public static final String SPF_LOGIN = "logindata";
    public static final String SP_LOGIN_COOKIE = "cookie";

    public static final String SPF_USER_INFO = "userinfo";
    public static final String SP_USER_ID = "id";
    public static final String SP_USER_NAME = "username";
    public static final String SP_USER_PWD = "password";
    public static final String SP_USER_REALNAME = "realname";
    public static final String SP_USER_JOB = "job";
    public static final String SP_USER_JOBTYPE = "job_type";
    public static final String SP_USER_PHONE = "phone";
    public static final String SP_USER_DEPARTMENT = "department";
    public static final String SP_USER_DEPARTMENTS = "departments";
    public static final String SP_USER_DEPARTMENTID = "departmentid";
    public static final String SP_USER_POWERID = "powerid";

    public static final String SP_USER_TOKEN = "token";
    /**
     * MOB短息验证成功
     */
    public static final String SP_SMS_OK = "sms_ok";

    //==============================Permission Code==============================//

    /**
     * 文件权限
     */
    public static final int PM_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0x555;
    /**
     * 语音录制权限
     */
    public static final int PM_RECORD_AUDIO_REQUEST_CODE = 0x666;

    //=================================固定数据=================================//

    public static final int INT_RETURNLOG = 3;//日志退回ReturnLog（POST） ispublished   3  固定值
    /**
     * 公文编号  写死
     */
    public static final String GW_NUMBER = "BDJSJC-JL-A-5";
    /**
     * 部门
     */
    public static final String[] STR_DEPARTMENTS = {
            "大队长", "副队长", "稽查管理科", "稽查督查科", "建筑市场稽查科", "房地产市场稽查科", "行政主任"};
    /**
     * 职务
     */
    public static final String[] STR_JOBS = {"大队长", "副队长", "行政主任", "科员"};
    /**
     * 全部工作状态
     */
    public static final String[] STR_WORK_STATE = {"工作状态", "全部", "在岗", "不在岗"};
    /**
     * 在岗+不在岗
     */
    public static final String[] STR_POST_NOPOST = {"单位办公", "外出", "事假", "病假"
            , "工伤", "特殊休假", "外调"};//, "年假", "其他"
    /**
     * 在岗
     */
    public static final String[] STR_POST = {"单位办公", "外出"};
    /**
     * 不在岗
     */
    public static final String[] STR_NO_POST = {"事假", "病假", "工伤", "特殊休假", "外调"};
    /**
     * 公文来源
     */
    public static final String[] STR_GSOURCE = {"上级交办", "其他部门移交"};

    /**
     * 事件类别
     */
    public static final String[] STR_GHANGDLE_TYPE = {"预售违法类", "违法施工类", "其他"};

    /**
     * 公文的三种状态
     * <p>
     * “综合办公室” 表示 新建的公文即待办公文 ；“副大队长办公室” 表示 已提交的公文；“归档”表示，已归档的公文。
     */
    public static final String STR_GW_TYPE_ZH = "综合办公室";
    public static final String STR_GW_TYPE_FD = "副大队长办公室";
    public static final String STR_GW_TYPE_GD = "归档";
    public static final int GW_TYPE = 0;// 0  综合  1  副大队  2 归档
    /**
     * 意见类别
     */
    public static final String[] STR_OPINION = {"处理意见", "部门负责人意见", "领导批示"};

    //===================================================Intent Flags=======================================//

    //AlertDialog  0  添加意见  1  添加图片  2 归档  3 转交下一部门
    public static final int GW_SHOW_TYPE_0 = 0x800;
    public static final int GW_SHOW_TYPE_1 = 0x801;
    public static final int GW_SHOW_TYPE_2 = 0x802;
    public static final int GW_SHOW_TYPE_3 = 0x803;

    public static final int FLAG_DRFT = 0x771;//草稿箱
    public static final int FLAG_DRFT_RETURN = 0x772;//退件箱
    public static final int FLAG_WORK_RECORD = 0x773;//工作记录
    public static final int FLAG_GW_YICHULI = 0x881;//GAlreadyHandleFragment  已处理的公文
    public static final int FLAG_GW_YIGUIDANG = 0x882;//GAlreadyHandleFragment  已归档的公文
    //    public static final int FLAG_WEICHULI = 0x882;//GStartFlowActivity
    public static final int FLAG_GW_WEIGUIDANG = 0x883;//ACTIVITY_NAME_APPROVAL  →   GNoHandleFragment

    //this_d:正件,enclosure：附件,ecpy_d:复印件
    public static final String[] IMG_FILE_TYPE = {"this_d", "enclosure", "ecpy_d"};

    //==========================================================================================//


    //百度语音识别
    public static final String EXTRA_KEY = "key";
    public static final String EXTRA_SECRET = "secret";
    public static final String EXTRA_SAMPLE = "sample";
    public static final String EXTRA_SOUND_START = "sound_start";
    public static final String EXTRA_SOUND_END = "sound_end";
    public static final String EXTRA_SOUND_SUCCESS = "sound_success";
    public static final String EXTRA_SOUND_ERROR = "sound_error";
    public static final String EXTRA_SOUND_CANCEL = "sound_cancel";
    public static final String EXTRA_INFILE = "infile";
    public static final String EXTRA_OUTFILE = "outfile";

    public static final String EXTRA_LANGUAGE = "language";
    public static final String EXTRA_NLU = "nlu";
    public static final String EXTRA_VAD = "vad";
    public static final String EXTRA_PROP = "prop";

    public static final String EXTRA_OFFLINE_ASR_BASE_FILE_PATH = "asr-base-file-path";
    public static final String EXTRA_LICENSE_FILE_PATH = "license-file-path";
    public static final String EXTRA_OFFLINE_LM_RES_FILE_PATH = "lm-res-file-path";
    public static final String EXTRA_OFFLINE_SLOT_DATA = "slot-data";
    public static final String EXTRA_OFFLINE_SLOT_NAME = "name";
    public static final String EXTRA_OFFLINE_SLOT_SONG = "song";
    public static final String EXTRA_OFFLINE_SLOT_ARTIST = "artist";
    public static final String EXTRA_OFFLINE_SLOT_APP = "app";
    public static final String EXTRA_OFFLINE_SLOT_USERCOMMAND = "usercommand";

    public static final int SAMPLE_8K = 8000;
    public static final int SAMPLE_16K = 16000;

    public static final String VAD_SEARCH = "search";
    public static final String VAD_INPUT = "input";
}