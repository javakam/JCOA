package com.vitek.jcoa.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseAppCompatActivity;
import com.vitek.jcoa.net.entity.LoginBySmsEntity;

/**
 * 短信验证码登录
 * <p>
 * Creater by javakam on 2017-05-24 19:06:39 .
 */
public class SmsLoginActivity extends GBaseAppCompatActivity<LoginBySmsEntity> {

    private FrameLayout titleBack;
    private TextView tvTitle;
    private ImageView imgPhone;
    private EditText edtPhone, edtYzm;
    private Button btSendYZM, btLogin;


    @Override
    protected void initVariables() {
        setTranslucentStatus(R.color.titleColor);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sms_login;
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
        tvTitle.setText("短信验证码登录");
        imgPhone = (ImageView) findViewById(R.id.imgPhone);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtYzm = (EditText) findViewById(R.id.edtYzm);
        edtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imgPhone.setImageResource(R.drawable.phone);
                } else {
                    imgPhone.setImageResource(R.drawable.phone_gray);
                }
            }
        });
        btSendYZM = (Button) findViewById(R.id.btSendYZM);
        btSendYZM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SmsLoginActivity.this, "Send Verification Code ", Toast.LENGTH_SHORT).show();
            }
        });
        btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SmsLoginActivity.this, "Login", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void getNetData() {

    }

    @Override
    protected void onNetError(VolleyError error) {

    }

    @Override
    protected void onNetSuccess(LoginBySmsEntity response) {

    }


}
