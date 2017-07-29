package com.vitek.jcoa.ui.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.baidu.speech.VoiceRecognitionService;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.misdk.net.ResponseListener;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.SharePreUtil;
import com.misdk.util.StringUtils;
import com.misdk.util.TimeUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.JCOAApplication;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseAppCompatActivity;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.entity.FindIsPublishedEntity;
import com.vitek.jcoa.net.entity.PublishLogEntity;
import com.vitek.jcoa.net.entity.UpdateMyLogEntity;
import com.vitek.jcoa.ui.view.MyCalendarView;
import com.vitek.jcoa.util.VLogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 我的日志
 * <p>
 * Created by javakam on 2017/05/18 .
 */
public class MyDairyActivity extends GBaseAppCompatActivity<PublishLogEntity> implements RecognitionListener {
    public static final String ACT_MYDAIRY = "MyDairyActivity";
    //Baidu Voice
    private static final int EVENT_ERROR = 11;
    private SpeechRecognizer speechRecognizer;
    private long speechEndTime = -1;
    private View speechTips;
    private View speechWave;
    //Title
    private FrameLayout titleBack;
    private TextView tvTitle, tvTitleNowday;
    private RelativeLayout relaStartTime, relaEndTime;//开始时间&结束时间
    private TextView tvChooseDate, tvStartTime;//把“开始时间”改成“选择日期”  , 日期
    private MyCalendarView myCalendarView;//日历控件
    private MaterialSpinner spinnerWorkState, spinnerDWBG;//工作状态&具体条目

    private Button btSaveLog, btCommitLog;
    private String content;//日志内容
    private TextView tvLog;//临时显示语音内容
    private EditText edtContent;//日志文本
    private ImageView imgBtVoice;//语音按钮
    private int ispublished = 1;// （1：发布，2：保存为草稿）
    private SharePreUtil sharePreUtil;
    private String stateType;//状态
    private String commitDate = TimeUtil.getStringDateyyyy_MM_dd();//用于提交的日期格式,默认今日

    private FindIsPublishedEntity.ModelsBean findIspubBean;
    private String titleStr;
    private int flag;
    private String[] strsNull = new String[]{""};

    @Override
    protected void initVariables() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().remove(Constant.EXTRA_INFILE).commit(); // infile参数用于控制识别一个PCM音频流（或文件），每次进入程序都将该值清楚，以避免体验时没有使用录音的问题
        //动态设置权限
//        PermissionUtil.requestRecordAudioPermission(getActivity());
        sharePreUtil = new SharePreUtil(this, Constant.SPF_USER_INFO);
        flag = getIntent().getFlags();
        if (flag == Constant.FLAG_DRFT) {//草稿箱
            titleStr = "编辑";
            findIspubBean = (FindIsPublishedEntity.ModelsBean) getIntent().getSerializableExtra(ACT_MYDAIRY);
        } else if (flag == Constant.FLAG_DRFT_RETURN) {//退件箱
            titleStr = "编辑";
            findIspubBean = (FindIsPublishedEntity.ModelsBean) getIntent().getSerializableExtra(ACT_MYDAIRY);

        } else {
            titleStr = "我的日志";
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_publish;
    }

    @Override
    protected void initPtr() {
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
        tvTitle.setText(titleStr);
        tvTitleNowday = (TextView) findViewById(R.id.tvTitleNowday);
        tvTitleNowday.setText(TimeUtil.getTitleDate());
        relaStartTime = (RelativeLayout) findViewById(R.id.relaStartTime);
        relaEndTime = (RelativeLayout) findViewById(R.id.relaEndTime);
        relaEndTime.setVisibility(View.GONE);
        relaStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendarView.showCalendarViewAsDropDown(v);
            }
        });
        tvChooseDate = (TextView) findViewById(R.id.tvChooseDate);
        tvChooseDate.setText("选择日期");
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);

        myCalendarView = new MyCalendarView(this);
        myCalendarView.initCalendarView(2017, 0, 1);
        myCalendarView.materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (myCalendarView.mPopupWindow != null && myCalendarView.mPopupWindow.isShowing()) {
                    myCalendarView.mPopupWindow.dismiss();
                    myCalendarView.mPopupWindow = null;
                }
                commitDate = MyCalendarView.formatDateToyyyy_MM_dd(date.getDate());
                tvStartTime.setText(commitDate);
            }
        });
        spinnerWorkState = (MaterialSpinner) findViewById(R.id.spinnerWorkState);
        spinnerWorkState.setItems(Constant.STR_WORK_STATE);
        spinnerDWBG = (MaterialSpinner) findViewById(R.id.spinnerDWBG);
        spinnerDWBG.setVisibility(View.INVISIBLE);
//        spinnerDWBG.setItems(Constant.STR_POST);//默认值
        spinnerWorkState.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                spinnerDWBG.setVisibility(View.VISIBLE);
                if (JCOAApplication.getInstance().isInputMethodOpenned(MyDairyActivity.this)) {
                    JCOAApplication.getInstance().hideInputMethod(MyDairyActivity.this);
                }
                String str = (String) item;
                String[] state = Constant.STR_WORK_STATE;
                if (str.trim().equals(state[1])) {
                    spinnerDWBG.setItems(strsNull);
                    spinnerDWBG.setItems(Constant.STR_POST_NOPOST);
                } else if (str.trim().equals(state[2])) {
                    spinnerDWBG.setItems(strsNull);
                    spinnerDWBG.setItems(Constant.STR_POST);
                } else if (str.trim().equals(state[3])) {
                    spinnerDWBG.setItems(strsNull);
                    spinnerDWBG.setItems(Constant.STR_NO_POST);
                } else {
                    spinnerDWBG.setItems(strsNull);//工作状态  state[0]
                    spinnerDWBG.setVisibility(View.INVISIBLE);
                }
            }
        });
        spinnerDWBG.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (JCOAApplication.getInstance().isInputMethodOpenned(MyDairyActivity.this)) {
                    JCOAApplication.getInstance().hideInputMethod(MyDairyActivity.this);
                }
            }
        });
        tvLog = (TextView) findViewById(R.id.tvLog);

        edtContent = (EditText) findViewById(R.id.edtContent);
        //// TODO: 2017/5/23 0023   删了试试 。。。
        tvTitle.requestFocusFromTouch();

        imgBtVoice = (ImageView) findViewById(R.id.imgVoice);
        initVoice();

        btSaveLog = (Button) findViewById(R.id.btSaveLog);
        btCommitLog = (Button) findViewById(R.id.btCommitLog);
        btSaveLog.setOnClickListener(onClickListener);
        btCommitLog.setOnClickListener(onClickListener);

        if (flag == Constant.FLAG_DRFT) {//草稿箱
            tvStartTime.setText(TimeUtil.longToDate8(findIspubBean.getDate()));
            String cs = findIspubBean.getContent();
            edtContent.setText(cs);
            edtContent.setSelection(cs.length());

        } else if (flag == Constant.FLAG_DRFT_RETURN) {//退件箱
            tvStartTime.setText(TimeUtil.longToDate8(findIspubBean.getDate()));
        } else {//正常发布
            tvStartTime.setText(TimeUtil.getLogDefaultDate(1));
        }
    }

    /**
     * 保存、提交
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            stateType = spinnerDWBG.getText().toString().trim();
            content = edtContent.getText().toString();
            if (StringUtils.isBlank(commitDate)) {
                showShortToast("请选择日期");
                return;
            }
            if (StringUtils.isBlank(stateType)) {
                showShortToast("请选择工作状态");
                return;
            }
            if (StringUtils.isBlank(content)) {
                showShortToast("内容不能为空");
                return;
            }

            switch (v.getId()) {
                case R.id.btSaveLog://草稿箱
                    ispublished = 0;
                    if (flag == Constant.FLAG_DRFT) {
                        publishDraft();
                    } else if (flag == Constant.FLAG_DRFT_RETURN) {

                    } else {
                        net();
                    }
                    break;
                case R.id.btCommitLog:
                    ispublished = 1;
                    if (flag == Constant.FLAG_DRFT) {
                        publishDraft();
                    } else if (flag == Constant.FLAG_DRFT_RETURN) {

                    } else {
                        net();
                    }
                    break;
            }
        }
    };

    /**
     * 发布草稿
     */
    private void publishDraft() {
        //7.更新日志草稿/jc_oa/up_mylog (POST))       UpdateMyLogEntity
        CloudPlatformUtil.getInstance().updateMyLog(
                JCOAApi.getUpdateMylogMap(
                        findIspubBean.getId() + ""
                        , findIspubBean.getUsername()
                        , content
                        , stateType
                        , commitDate
                        , ispublished + "")//是否发布（1:发布，0：继续保存）
                , new ResponseListener<UpdateMyLogEntity>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showShortToast(VolleyErrorManager.getErrorMsg(error));
                    }

                    @Override
                    public void onResponse(UpdateMyLogEntity response) {
                        if (response.ispass()) {
                            edtContent.setText("");
                        }
                        VLogUtil.d("发布草稿日志成功");
                        showShortToast(response.getDefaultMessage());
                        JCOAApplication.getInstance().notifyDataChange(JCOAApplication.UPDATE_DRAFT, null, null);
                    }
                });
    }

    @Override
    protected void getNetData() {
        //5.发布日志/jc_oa/addlog (POST))                PublishLogEntity
        // {"ispass":true,"defaultMessage":"添加成功","models":null,"logExtends":null}

        // "username": "weixiao",  "password": "password",
//        VLogUtil.v(spinnerDWBG.getItems().get(spinnerDWBG.getSelectedIndex()).toString() + " --==-- " +
//                commitDate);
        CloudPlatformUtil.getInstance().publishLog(JCOAApi.getPublishLogMap(
                sharePreUtil.getValue(Constant.SP_USER_NAME),//username
                content,//content
                ispublished + "",            //ispublished   （1：发布，0：保存为草稿）
                //workstatus  工作状态 不为空,填二级选项的字符串
                spinnerDWBG.getItems().get(spinnerDWBG.getSelectedIndex()).toString(),
                commitDate // date     2017-05-22    日期 2017-05-22 ERROR  2017.05.30
        ), this);
    }

    @Override
    protected void onNetError(VolleyError error) {
        VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
    }

    @Override
    protected void onNetSuccess(PublishLogEntity entity) {
        VLogUtil.d("正常发布日志成功");
        if (entity.ispass()) {
            edtContent.setText("");
        }
        showShortToast(entity.getDefaultMessage());
    }

    private void initVoice() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this, new ComponentName(this, VoiceRecognitionService.class));
        speechRecognizer.setRecognitionListener(this);

        speechTips = View.inflate(this, R.layout.bd_asr_popup_speech, null);
        speechWave = speechTips.findViewById(R.id.wave);
        speechTips.setVisibility(View.GONE);
        addContentView(speechTips, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imgBtVoice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{
                                    Manifest.permission.RECORD_AUDIO
                            }, Constant.PM_RECORD_AUDIO_REQUEST_CODE); // requestPermissions是Activity的方法
                        }
                        speechTips.setVisibility(View.VISIBLE);
                        speechRecognizer.cancel();
                        Intent intent = new Intent();
                        bindParams(intent);
                        intent.putExtra("vad", "touch");
//                        edtContent.setText("");//之前内容置空
//                        txtLog.setText("");
                        speechRecognizer.startListening(intent);
                        return true;
                    case MotionEvent.ACTION_UP:
                        speechRecognizer.stopListening();
                        speechTips.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });
    }

    public void bindParams(Intent intent) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getBoolean("tips_sound", true)) {
            intent.putExtra(Constant.EXTRA_SOUND_START, R.raw.bdspeech_recognition_start);
            intent.putExtra(Constant.EXTRA_SOUND_END, R.raw.bdspeech_speech_end);
            intent.putExtra(Constant.EXTRA_SOUND_SUCCESS, R.raw.bdspeech_recognition_success);
            intent.putExtra(Constant.EXTRA_SOUND_ERROR, R.raw.bdspeech_recognition_error);
            intent.putExtra(Constant.EXTRA_SOUND_CANCEL, R.raw.bdspeech_recognition_cancel);
        }
        if (sp.contains(Constant.EXTRA_INFILE)) {
            String tmp = sp.getString(Constant.EXTRA_INFILE, "").replaceAll(",.*", "").trim();
            intent.putExtra(Constant.EXTRA_INFILE, tmp);
        }
        if (sp.getBoolean(Constant.EXTRA_OUTFILE, false)) {
            intent.putExtra(Constant.EXTRA_OUTFILE, "sdcard/outfile.pcm");
        }
        if (sp.contains(Constant.EXTRA_SAMPLE)) {
            String tmp = sp.getString(Constant.EXTRA_SAMPLE, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(Constant.EXTRA_SAMPLE, Integer.parseInt(tmp));
            }
        }
        if (sp.contains(Constant.EXTRA_LANGUAGE)) {
            String tmp = sp.getString(Constant.EXTRA_LANGUAGE, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(Constant.EXTRA_LANGUAGE, tmp);
            }
        }
        if (sp.contains(Constant.EXTRA_NLU)) {
            String tmp = sp.getString(Constant.EXTRA_NLU, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(Constant.EXTRA_NLU, tmp);
            }
        }

        if (sp.contains(Constant.EXTRA_VAD)) {
            String tmp = sp.getString(Constant.EXTRA_VAD, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(Constant.EXTRA_VAD, tmp);
            }
        }
        String prop = null;
        if (sp.contains(Constant.EXTRA_PROP)) {
            String tmp = sp.getString(Constant.EXTRA_PROP, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(Constant.EXTRA_PROP, Integer.parseInt(tmp));
                prop = tmp;
            }
        }
        // offline asr
        {
            intent.putExtra(Constant.EXTRA_OFFLINE_ASR_BASE_FILE_PATH, "/sdcard/easr/s_1");
            intent.putExtra(Constant.EXTRA_LICENSE_FILE_PATH, "/sdcard/easr/license-tmp-20150530.txt");
            if (null != prop) {
                int propInt = Integer.parseInt(prop);
                if (propInt == 10060) {
                    intent.putExtra(Constant.EXTRA_OFFLINE_LM_RES_FILE_PATH, "/sdcard/easr/s_2_Navi");
                } else if (propInt == 20000) {
                    intent.putExtra(Constant.EXTRA_OFFLINE_LM_RES_FILE_PATH, "/sdcard/easr/s_2_InputMethod");
                }
            }
            intent.putExtra(Constant.EXTRA_OFFLINE_SLOT_DATA, buildTestSlotData());
        }
    }

    private String buildTestSlotData() {
        JSONObject slotData = new JSONObject();
        JSONArray name = new JSONArray().put("李涌泉").put("郭下纶");
        JSONArray song = new JSONArray().put("七里香").put("发如雪");
        JSONArray artist = new JSONArray().put("周杰伦").put("李世龙");
        JSONArray app = new JSONArray().put("手机百度").put("百度地图");
        JSONArray usercommand = new JSONArray().put("关灯").put("开门");
        return slotData.toString();
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {
        final int VTAG = 0xFF00AA01;
        Integer rawHeight = (Integer) speechWave.getTag(VTAG);
        if (rawHeight == null) {
            rawHeight = speechWave.getLayoutParams().height;
            speechWave.setTag(VTAG, rawHeight);
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) speechWave.getLayoutParams();
        params.height = (int) (rawHeight * rmsdB * 0.01);
        params.height = Math.max(params.height, speechWave.getMeasuredWidth());
        speechWave.setLayoutParams(params);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {
        StringBuilder sb = new StringBuilder();
        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                sb.append("音频问题");
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                sb.append("没有语音输入");
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                sb.append("其它客户端错误");
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                sb.append("权限不足");
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                sb.append("网络问题");
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                sb.append("没有匹配的识别结果");
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                sb.append("引擎忙");
                break;
            case SpeechRecognizer.ERROR_SERVER:
                sb.append("服务端错误");
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                sb.append("连接超时");
                break;
        }
        sb.append(":" + error);
        print("识别失败：" + sb.toString());
    }

    @Override
    public void onResults(Bundle results) {
        long end2finish = System.currentTimeMillis() - speechEndTime;
        ArrayList<String> nbest = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        print("识别成功：" + Arrays.toString(nbest.toArray(new String[nbest.size()])));
        String json_res = results.getString("origin_result");
        try {
            print("origin_result=\n" + new JSONObject(json_res).toString(4));
        } catch (Exception e) {
            print("origin_result=[warning: bad json]\n" + json_res);
        }
//        imgBtVoice.setText("开始");
        String strEnd2Finish = "";
        if (end2finish < 60 * 1000) {
            strEnd2Finish = "(waited " + end2finish + "ms)";
        }

        int index = edtContent.getSelectionStart();
        Editable editable = edtContent.getText();
        editable.insert(index, nbest.get(0) + strEnd2Finish);
        tvLog.setText("");
        tvLog.setVisibility(View.GONE);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        ArrayList<String> nbest = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (nbest.size() > 0) {
            print("~临时识别结果：" + Arrays.toString(nbest.toArray(new String[0])));
            tvLog.setVisibility(View.VISIBLE);
            tvLog.setText("正在说的话：" + nbest.get(0));
        }
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        switch (eventType) {
            case EVENT_ERROR:
                String reason = params.get("reason") + "";
                print("EVENT_ERROR, " + reason);
                break;
        }
    }

    private void print(String msg) {
//        txtLog.append(msg + "\n");
//        ScrollView sv = (ScrollView) txtLog.getParent();
//        sv.smoothScrollTo(0, 1000000);
//        Log.d(TAG, "----" + msg);
    }
}
