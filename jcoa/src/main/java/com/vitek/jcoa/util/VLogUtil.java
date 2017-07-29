package com.vitek.jcoa.util;

import android.util.Log;

/**
 * Log工具类
 */
public class VLogUtil {

    private static final String TAG = "123";


    public static void v(String str) {

        Log.v(TAG, str);
    }

    public static void d(String str) {

        Log.d(TAG, str);
    }

    public static void e(String str) {

        Log.e(TAG, str);
    }

    public static void i(String str) {

        Log.i(TAG, str);
    }

    public static void w(String str) {

        Log.w(TAG, str);
    }

}
