package com.misdk.util;

import android.widget.EditText;

/**
 * 用于验证edittext是否为空
 * Created by javakam on 2016-07-07
 */
public class EditTextCheck {
    EditText et;
    String msg;

    public EditTextCheck(EditText et, String msg) {
        super();
        this.et = et;
        this.msg = msg;
    }
}
