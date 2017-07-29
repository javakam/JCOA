package com.misdk.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.misdk.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证输入
 * Created by javakam on 2016-07-07
 */
public class EditTextCheckUtil {
    /**
     * 验证所有必填项
     *
     * @param context
     * @param textChecks
     * @return true验证通过 false验证不通过
     */
    public static boolean notEmpty(Context context, EditTextCheck... textChecks) {
        boolean notEmpty = true;
        StringBuffer sb = new StringBuffer();
        for (EditTextCheck editTextCheck : textChecks) {
            String trim = editTextCheck.et.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                notEmpty = false;
                sb.append(editTextCheck.msg).append("\r\n");
            }
        }
        if (!notEmpty) {
            sb.deleteCharAt(sb.length() - 1);
            Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
        }
        return notEmpty;
    }

    /**
     * 判断TextView EditText内容是否为空
     *
     * @param view
     * @return true  null
     */
    public static boolean isEmpty(final View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            if (TextUtils.isEmpty(tv.getText())) {
//                tv.setError(tv.getContext().getString(R.string.text_input_empty));
                return true;
            }
        } else if (view instanceof EditText) {
            EditText edt = (EditText) view;
            if (TextUtils.isEmpty(edt.getText())) {
//                edt.setError(edt.getContext().getString(R.string.text_input_empty));
                return true;
            }
        }
        return false;
    }

    /**
     * 密码的合法性校验
     *
     * @param etPassword
     * @return
     */
    public static boolean isPasswordValidate(TextView etPassword) {
        if (isEmpty(etPassword)) {
            return false;
        }
        //不支持中文
        if (hasChinese(etPassword.getText().toString())) {
            etPassword.setError(etPassword.getContext().getString(R.string.text_input_password_can_not_be_chinese));
            return false;
        }
        //密码长度小于6位或者大于16位
        if (etPassword.getText().length() < 6 || etPassword.getText().length() > 16) {
            etPassword.setError(etPassword.getContext().getString(R.string.text_input_password_length_invalidate));
            return false;
        }
        return true;
    }

    /**
     * 密码输入的合法验证
     *
     * @param password
     * @return true 合法
     */
    public static boolean isValidatePassword(String password) {
        if (TextUtils.isEmpty(password.trim())) return false;
        //不支持中文及中文字符
        if (hasChinese(password)) {
            return false;
        }
        //密码长度小于6位或者大于16位
        if (password.trim().length() < 6 || password.trim().length() > 20) {
            return false;
        }
        return true;
    }

    /**
     * 是否为中文字符
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否含有中文或中文字符
     *
     * @param strName
     * @return
     */
    public static boolean hasChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c) == true) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否含有中文
     * 此判断对中文字符判断无效
     *
     * @param str
     * @return
     */
    private static boolean hasChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * 判断验证码格式是否正确  eg 四位数字1234
     *
     * @param str mob传回来的短信验证码
     * @return
     */
    public static boolean checkCodeRight(String str) {
        boolean temp = false;
        if (StringUtils.isBlank(str) || str.length() != 4) {
            return false;
        }
//        Pattern p = Pattern.compile("0123456789");
//        Matcher m = p.matcher(str);
//        if (m.find()) {
//            Log.e("123", "Matcher" + str + m);
//            temp = true;
//        }
        return true;
    }

    /**
     * 验证手机号是否合法
     *
     * @param etPhone
     * @return
     */
    public static boolean isPhoneValid(TextView etPhone) {
        if (isEmpty(etPhone)) {
            return false;
        }
        boolean isValid = true;
        String phone = etPhone.getText().toString().trim();
        int length = phone.length();
        if (TextUtils.isEmpty(phone)) {
            isValid = false;
        } else {
            Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phone);
            if (length > 11 || !m.matches()) {
                isValid = false;
            }
        }
        if (!isValid) {
            //Toast.makeText(context, "请输入11位有效手机号", Toast.LENGTH_SHORT).show();
            etPhone.setError(etPhone.getContext().getString(R.string.text_input_phone_validate));
        }
        return isValid;
    }
}
