package com.misdk.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证是否为手机号<p>
 * Created by javakam on 2016/7/5.
 */
public class VerificationUtil {

    /**
     * 是否为手机号
     *
     * @param number
     * @return true 合法手机号
     */
    public static boolean isMoblie(String number) {
        if (TextUtils.isEmpty(number.trim())) return false;
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(number);
        b = m.matches();
        return b;
    }
}
