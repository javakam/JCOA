package com.vitek.jcoa.ui.fragment;


import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
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
import com.vitek.jcoa.base.GBaseFragment;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.entity.PublishLogEntity;
import com.vitek.jcoa.ui.view.MyCalendarView;
import com.vitek.jcoa.util.VLogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * 发布日志
 * <p>
 * A simple {@link Fragment} subclass.
 */
public class PublishFragment extends GBaseFragment<PublishLogEntity> implements RecognitionListener {
    private static final int EVENT_ERROR = 11;
    //Baidu Voice
    public static final String ARG_PUBLISHFRAGMENT = "PublishFragment";
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
    private String commitDate = TimeUtil.getStringDateyyyy_MM_dd();//用于提交的日期格式,默认今日

    private MaterialSpinner spinnerWorkState, spinnerDWBG;//工作状态&具体条目
    private Button btSaveLog, btCommitLog;
    private String content;//日志内容
    private TextView tvLog;//临时显示语音内容
    private EditText edtContent;//日志文本
    private ImageView imgBtVoice;//语音按钮
    private int ispublished = 1;// （1：发布，2：保存为草稿）
    private SharePreUtil sharePreUtil;

    private String[] strsNull = new String[]{""};

    @Override
    protected void initData(Bundle savedInstanceState) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sp.edit().remove(Constant.EXTRA_INFILE).commit(); // infile参数用于控制识别一个PCM音频流（或文件），每次进入程序都将该值清楚，以避免体验时没有使用录音的问题
        //动态设置权限
//        PermissionUtil.requestRecordAudioPermission(getActivity());
        sharePreUtil = new SharePreUtil(mContext, Constant.SPF_USER_INFO);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_publish;
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
        tvTitle.setText("发布日志");
        tvTitleNowday = (TextView) view.findViewById(R.id.tvTitleNowday);
        tvTitleNowday.setText(TimeUtil.getTitleDate());
        relaStartTime = (RelativeLayout) view.findViewById(R.id.relaStartTime);
        relaEndTime = (RelativeLayout) view.findViewById(R.id.relaEndTime);
        relaEndTime.setVisibility(View.GONE);
        relaStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendarView.showCalendarViewAsDropDown(v);
            }
        });
        tvChooseDate = (TextView) view.findViewById(R.id.tvChooseDate);
        tvChooseDate.setText("选择日期");
        tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);
        tvStartTime.setText(TimeUtil.getLogDefaultDate(1));

        myCalendarView = new MyCalendarView(mContext);
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

        spinnerWorkState = (MaterialSpinner) view.findViewById(R.id.spinnerWorkState);
        spinnerWorkState.setItems(Constant.STR_WORK_STATE);
        spinnerDWBG = (MaterialSpinner) view.findViewById(R.id.spinnerDWBG);
        spinnerDWBG.setVisibility(View.INVISIBLE);
        initMaterialSpinner();
        tvLog = (TextView) view.findViewById(R.id.tvLog);

        edtContent = (EditText) view.findViewById(R.id.edtContent);
        //// TODO: 2017/5/23 0023   删了试试 。。。
//        tvTitle.requestFocusFromTouch();

        imgBtVoice = (ImageView) view.findViewById(R.id.imgVoice);
        initVoice();

        btSaveLog = (Button) view.findViewById(R.id.btSaveLog);
        btCommitLog = (Button) view.findViewById(R.id.btCommitLog);
        btSaveLog.setOnClickListener(onClickListener);
        btCommitLog.setOnClickListener(onClickListener);
    }

    private void initMaterialSpinner() {
//        spinnerDWBG.setItems(Constant.STR_POST);//默认值
        spinnerWorkState.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                spinnerDWBG.setVisibility(View.VISIBLE);
                if (JCOAApplication.getInstance().isInputMethodOpenned(getActivity())) {
                    JCOAApplication.getInstance().hideInputMethod(getActivity());
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
                    spinnerDWBG.setItems(strsNull); //工作状态  state[0]
                    spinnerDWBG.setVisibility(View.INVISIBLE);
                }
            }
        });
        spinnerDWBG.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (JCOAApplication.getInstance().isInputMethodOpenned(getActivity())) {
                    JCOAApplication.getInstance().hideInputMethod(getActivity());
                }
            }
        });
    }

    @Override
    public void onResume() {
        JCOAApplication.getInstance().showInputMethod(getActivity());
        super.onResume();
    }

    /**
     * 保存、提交
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String stateType = spinnerDWBG.getText().toString().trim();
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
                    net();
                    break;
                case R.id.btCommitLog:
                    ispublished = 1;
                    net();
                    break;
            }
        }
    };


    @Override
    protected void getNetData() {
        //5.发布日志/jc_oa/addlog (POST))                PublishLogEntity
        // {"ispass":true,"defaultMessage":"添加成功","models":null,"logExtends":null}
        // "username": "weixiao",  "password": "password",
        CloudPlatformUtil.getInstance().publishLog(JCOAApi.getPublishLogMap(
                sharePreUtil.getValue(Constant.SP_USER_NAME),//username
                content,//content
                ispublished + "",            //ispublished   （1：发布，0：保存为草稿）
                //workstatus  工作状态 不为空,填二级选项的字符串
                spinnerDWBG.getItems().get(spinnerDWBG.getSelectedIndex()).toString(),
                // date   日期  2017.5.22 or  2017.05.22 错误格式：2017-05-22
                commitDate
        ), this);
    }

    @Override
    protected void onNetError(VolleyError error) {
        VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
    }

    @Override
    protected void onNetSuccess(PublishLogEntity entity) {
        if (entity.ispass()) {
            edtContent.setText("");
        }
        showShortToast(entity.getDefaultMessage());
    }

    private void initVoice() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext, new ComponentName(mContext, VoiceRecognitionService.class));
        speechRecognizer.setRecognitionListener(this);

        speechTips = View.inflate(mContext, R.layout.bd_asr_popup_speech, null);
        speechWave = speechTips.findViewById(R.id.wave);
        speechTips.setVisibility(View.GONE);
        getActivity().addContentView(speechTips, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imgBtVoice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        requestPermissions(new String[]{
                                Manifest.permission.RECORD_AUDIO
                        }, Constant.PM_RECORD_AUDIO_REQUEST_CODE); // requestPermissions是Activity的方法
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
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
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
