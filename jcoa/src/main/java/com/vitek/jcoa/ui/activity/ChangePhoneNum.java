package com.vitek.jcoa.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.misdk.util.StringUtils;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseChangeActivity;

public class ChangePhoneNum extends GBaseChangeActivity {
    private EditText edtInputPhoneNum;
    private Button btConfirm;
    private String newPhone;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_phone_num;
    }


    @Override
    protected String getTileStr() {
        return ACTION_NAME[1];
    }

    @Override
    protected void iniView() {
        edtInputPhoneNum = (EditText) findViewById(R.id.edtInputPhoneNum);
        btConfirm = (Button) findViewById(R.id.btConfirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPhone = edtInputPhoneNum.getText().toString().trim();
                if (StringUtils.isBlank(newPhone)) return;
                getDialog().show();
                net();
            }
        });
    }

    @Override
    protected String getParams() {
        return newPhone;
    }
}
