package com.vitek.jcoa.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.net.ResponseListener;
import com.misdk.net.VolleyErrorManager;
import com.misdk.util.EditTextCheckUtil;
import com.misdk.util.StringUtils;
import com.misdk.util.VerificationUtil;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseAppCompatActivity;
import com.vitek.jcoa.net.CloudPlatformUtil;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.entity.GetVerificationCodeEntity;
import com.vitek.jcoa.net.entity.PasswordRetrievalEntity;
import com.vitek.jcoa.util.VLogUtil;

import java.util.Timer;
import java.util.TimerTask;

public class ForgetPwdActivity extends GBaseAppCompatActivity<PasswordRetrievalEntity> {
    private FrameLayout titleBack;
    private TextView tvTitle;
    private EditText edtPhone, edtYzm, edtNewPwd, edtNewPwdAgain;
    private Button btSendYZM, btCommit;
    private String phone, yzm, newPwd, newPwdAgain;

    private static final int MSG_TAG = 0x102;
    private static final int FIXED_TIME = 60;//固定倒计时60s
    private Timer timer;
    private TimerTask timerTask;
    private int fixedTime = FIXED_TIME;
    private Message msg;

    @Override
    protected void initVariables() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_forget_pwd;
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
        tvTitle.setText("忘记密码");
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtYzm = (EditText) findViewById(R.id.edtYzm);
        edtNewPwd = (EditText) findViewById(R.id.edtNewPwd);
        edtNewPwdAgain = (EditText) findViewById(R.id.edtNewPwdAgain);
        btSendYZM = (Button) findViewById(R.id.btSendYZM);
        btCommit = (Button) findViewById(R.id.btCommit);

        btSendYZM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = edtPhone.getText().toString().trim();
                if (!VerificationUtil.isMoblie(phone)) {
                    showShortToast("请输入手机号");
                    return;
                }

                if (fixedTime == FIXED_TIME && timerTask == null) {
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            msg = getHandler().obtainMessage();
                            msg.what = MSG_TAG;
                            getHandler().sendMessage(msg);
                            fixedTime--;
                        }
                    };
                    timer = new Timer();
                    timer.schedule(timerTask, 1000, 1000);

                    netVerifyCode();
                }
            }
        });
        btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judgeViews()) {
                    net();
                }
            }
        });
    }

    private void netVerifyCode() {
        //获取密码找回短信验证码  GetVerificationCode  （POST）
        CloudPlatformUtil.getInstance().getGetVerificationCode(
                JCOAApi.getGetVerificationCodeMap(phone), new ResponseListener<GetVerificationCodeEntity>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VLogUtil.e("获取密码找回短信验证码 失败 --- " + VolleyErrorManager.getErrorMsg(error));
                    }

                    @Override
                    public void onResponse(GetVerificationCodeEntity response) {
                        if (response == null) return;
                    }
                });//18920566703
    }

    private boolean judgeViews() {
        phone = edtPhone.getText().toString().trim();
        if (!VerificationUtil.isMoblie(phone)) {
            showShortToast("请输入手机号");
            return false;
        }
        yzm = edtYzm.getText().toString().trim();
        if (StringUtils.isBlank(yzm)) {
            showShortToast("请输入验证码");
            return false;
        }
        newPwd = edtNewPwd.getText().toString().trim();
        newPwdAgain = edtNewPwdAgain.getText().toString().trim();
        if (!EditTextCheckUtil.isValidatePassword(newPwd)
                || !EditTextCheckUtil.isValidatePassword(newPwdAgain)) {
            showShortToast("请输入正确格式的密码");
            return false;
        }
        if (!newPwd.equals(newPwdAgain)) {
            showShortToast("两次输入的密码不一致");
            return false;
        }
        return true;
    }

    @Override
    protected void getNetData() {
        //找回密码 PasswordRetrieval (POST)     PasswordRetrievalEntity
        CloudPlatformUtil.getInstance().getPasswordRetrieval(
                JCOAApi.getPasswordRetrievalMap(phone, yzm, "MsgId", newPwd), this);
    }

    @Override
    protected void onNetError(VolleyError error) {
        VLogUtil.e("找回密码 失败 --- " + VolleyErrorManager.getErrorMsg(error));
    }

    @Override
    protected void onNetSuccess(PasswordRetrievalEntity response) {
        if (response == null) return;
    }

    @Override
    protected void onMsgObtain(Message msg) {
        super.onMsgObtain(msg);
        if (msg.what == MSG_TAG) {
            if (fixedTime >= 1) {
                btSendYZM.setBackgroundResource(R.drawable.yangzheng_gray);
                btSendYZM.setText(fixedTime + "s");
            } else {
                fixedTime = FIXED_TIME;
                btSendYZM.setBackgroundResource(R.drawable.yangzheng);
                btSendYZM.setText("发送验证码");
                removeTimer();
            }
        }
    }

    private void removeTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    @Override
    protected void onDestroy() {
        removeTimer();
        getHandler().removeMessages(MSG_TAG);
        super.onDestroy();
    }
}
