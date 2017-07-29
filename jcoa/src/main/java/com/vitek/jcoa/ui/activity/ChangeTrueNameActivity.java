package com.vitek.jcoa.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.misdk.util.StringUtils;
import com.vitek.jcoa.R;
import com.vitek.jcoa.base.GBaseChangeActivity;

/**
 * 修改真实姓名
 */
public class ChangeTrueNameActivity extends GBaseChangeActivity {

    private EditText edtChangeTrueName;
    private Button btConfirm;
    private String newName;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_true_name;
    }

    @Override
    protected String getTileStr() {
        return ACTION_NAME[2];
    }

    @Override
    protected void iniView() {
        edtChangeTrueName = (EditText) findViewById(R.id.edtChangeTrueName);
        btConfirm = (Button) findViewById(R.id.btConfirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newName = edtChangeTrueName.getText().toString().trim();
                if (StringUtils.isBlank(newName)) return;
                getDialog().show();
                net();
            }
        });
    }

    @Override
    protected String getParams() {
        return newName;
    }
}
