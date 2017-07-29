package com.vitek.jcoa.ui.activity;

import android.view.View;
import android.widget.EditText;

import com.misdk.util.StringUtils;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseChangeActivity;

/**
 * 更改密码
 */
public class ChangePwdActivity extends GBaseChangeActivity {

    private EditText edtOriginalPwd, edtNewPwd;
    private String newPwd;
    private String newPwdAgain;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected String getTileStr() {
        return ACTION_NAME[0];
    }

    @Override
    protected void iniView() {
        edtOriginalPwd = (EditText) findViewById(R.id.edtOriginalPwd);
        edtNewPwd = (EditText) findViewById(R.id.edtNewPwd);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judgeStr()) {
                    getDialog();
                    showDialog();
                    net();
                }
            }
        });
    }

    @Override
    protected String getParams() {
        return newPwdAgain;
    }

    private boolean judgeStr() {
        newPwd = edtOriginalPwd.getText().toString().trim();
        newPwdAgain = edtOriginalPwd.getText().toString().trim();
        if (StringUtils.isBlank(newPwd)) {
            showShortToast("请输入新密码");
            return false;
        }
        if (StringUtils.isBlank(newPwd)) {
            showShortToast("请重新输入新密码");
            return false;
        }
        if (newPwd.length() < 6 || newPwdAgain.length() < 6) {
            showShortToast("密码长度不能小于六位");
            return false;
        }
        if (!newPwdAgain.equalsIgnoreCase(newPwd)) {
            showShortToast("两次输入的密码不一致");
            return false;
        }
        return true;
    }

}
