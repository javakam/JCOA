package com.vitek.jcoa.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.misdk.net.VolleyErrorManager;
import com.vitek.jcoa.Constant;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseAppCompatActivity;
import com.vitek.jcoa.net.JCOAApi;
import com.vitek.jcoa.net.LoginUtil;
import com.vitek.jcoa.net.entity.LoginEntity;
import com.vitek.jcoa.util.VLogUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends GBaseAppCompatActivity<JSONObject> {
    private LinearLayout linearLogin2, linearLogin3;
    private ImageView imgPhone, imgPwd;
    private EditText edtName, edtPwd;
    private TextView tvMsgLogin, tvForgetPwd;//短信验证码登录，忘记密码
    private Button btLogin;
    private String userName;
    private String userPassword;
    private LoginUtil loginUtil;

    @Override
    protected void initVariables() {
        setTranslucentStatus(R.color.titleColor);
        loginUtil = new LoginUtil(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        linearLogin2 = (LinearLayout) findViewById(R.id.linearLogin2);
        linearLogin3 = (LinearLayout) findViewById(R.id.linearLogin3);
        imgPhone = (ImageView) findViewById(R.id.imgPhone);
        imgPwd = (ImageView) findViewById(R.id.imgPwd);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPwd = (EditText) findViewById(R.id.edtPwd);
        //test   weixiao  password    admin   jcoaAdmin
        edtName.setText("weixiao");
        edtName.setSelection(edtName.getText().toString().length());
        edtPwd.setText("password");
        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (hasFocus) {
                        edtName.setTextColor(getResources().getColor(R.color.titleColor));
                        linearLogin2.setBackground(getResources().getDrawable(R.drawable.shape_login_edt_focus));
                        imgPhone.setImageResource(R.drawable.phone);
                    } else {
                        edtName.setTextColor(getResources().getColor(R.color.gray));
                        linearLogin2.setBackground(getResources().getDrawable(R.drawable.shape_login_edt_nofocus));
                        imgPhone.setImageResource(R.drawable.phone_gray);
                    }
                }
            }
        });
        edtPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (hasFocus) {
                        edtPwd.setTextColor(getResources().getColor(R.color.titleColor));
                        linearLogin3.setBackground(getResources().getDrawable(R.drawable.shape_login_edt_focus));
                        imgPwd.setImageResource(R.drawable.pwd);
                    } else {
                        edtPwd.setTextColor(getResources().getColor(R.color.gray));
                        linearLogin3.setBackground(getResources().getDrawable(R.drawable.shape_login_edt_nofocus));
                        imgPwd.setImageResource(R.drawable.pwd_gray);
                    }
                }
            }
        });
        tvMsgLogin = (TextView) findViewById(R.id.tvMsgLogin);
        tvForgetPwd = (TextView) findViewById(R.id.tvForgetPwd);
        tvMsgLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SmsLoginActivity.class));
            }
        });
        tvForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
            }
        });
        btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.requestFocusFromTouch();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog();
                showDialog();
                net();
            }
        });

//        PermissionUtil.requestWriteExternalStoragePermission(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    , Constant.PM_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }

    }

    @Override
    protected void getNetData() {
        // 用户名:weixiao 密码:password
        /*
            "id": 120,
            "username": "weixiao",
            "password": "password",
            "realname": "wei",
            "departmentid": "8",
            "job": "行政主任",
            "job_type": null,
            "phone": "18632257918",
            "rtime": 1493887395000,
            "department": "综合科",
            "powerid": "2",
         */
        // admin   jcoaAdmin

        userName = edtName.getText().toString().trim();
        userPassword = edtPwd.getText().toString().trim();

        loginUtil.login(JCOAApi.getLoginMap(userName, userPassword), this);
    }

    @Override
    protected void onNetError(VolleyError error) {
        dismissDialog();
//        showShortToast(VolleyErrorManager.getErrorMsg(error));
        VLogUtil.e(VolleyErrorManager.getErrorMsg(error));
    }

    @Override
    protected void onNetSuccess(JSONObject jsonObject) {
        //从服务器响应response中的jsonObject中取出cookie的值，存到本地sharePreference
        try {
            loginUtil.setLocalCookie(jsonObject.getString(LoginUtil.COOKIE));
            //登录成功
            LoginEntity entity = gson.fromJson(jsonObject.toString(), LoginEntity.class);

//            VLogUtil.i("登录成功   " + jsonObject.getString(LoginUtil.COOKIE) + "  ---  " + entity.getCookie());
//            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            dismissDialog();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } catch (JSONException e) {
            dismissDialog();
            e.printStackTrace();
        }
    }
}