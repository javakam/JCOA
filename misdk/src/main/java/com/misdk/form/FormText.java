package com.misdk.form;

/**
 * Created by gyzhong on 15/3/1.
 */
/*表单提交的参数*/
public class FormText {
    /*参数的名称*/
    private String mName ;
    /*参数的值*/
    private String mValue ;

    public FormText(String mName, String mValue) {
        this.mName = mName;
        this.mValue = mValue;
    }

    public String getName() {
        return mName;
    }

    public String getValue() {
        return mValue;
    }

}
